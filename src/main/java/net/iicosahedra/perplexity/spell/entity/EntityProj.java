package net.iicosahedra.perplexity.spell.entity;

import net.iicosahedra.perplexity.spell.SpellCasting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.iicosahedra.perplexity.setup.Registration;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class EntityProj extends Projectile {
    public int age;
    public SpellCasting casting;
    public boolean isNoGravity = true;
    public boolean canTraversePortals = true;
    public int prismRedirect;

    public Set<BlockPos> hitList = new HashSet<>();

    public EntityProj(EntityType<? extends EntityProj> type, Level world){
        super(type, world);
    }

    public EntityProj(EntityType<? extends EntityProj> type, Level world, double x, double y, double z){
        super(type, world);
        setPos(x,y,z);
    }
    public EntityProj(EntityType<? extends EntityProj> type, Level world, LivingEntity shooter){
        super(type, world);
        setOwner(shooter);
        setPos(shooter.getX(), shooter.getEyeY() - (double) 0.1F, shooter.getZ());
    }
    public EntityProj(EntityType<? extends EntityProj> type, Level world, SpellCasting casting){
        this(type, world, casting.context.getCaster());
        this.casting = casting;
    }
    public EntityProj(Level world, SpellCasting casting){
        this(Registration.SPELL_PROJ.get(), world, casting);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326003_) {

    }

    @Override
    public void tick() {
        super.tick();
        age++;

        if((!level().isClientSide && this.age > 1200) || (!level().isClientSide && casting == null)){
            this.remove(RemovalReason.DISCARDED);
            return;
        }

        this.xOld = this.getX();
        this.yOld = this.getY();
        this.zOld = this.getZ();

        Vec3 thisPosition = this.position();
        Vec3 nextPosition = getNextHitPosition();
        traceAnyHit(getHitResult(), thisPosition, nextPosition);

        tickNextPosition();
    }

    @Override
    protected void onHit(HitResult result) {
        result = transformHitResult(result);
        if(!level().isClientSide && result instanceof EntityHitResult entityHitResult){
            if(this.casting != null){
                this.casting.processEffects(result);
                this.remove(RemovalReason.DISCARDED);
            }
        }
        if(!level().isClientSide && result instanceof BlockHitResult blockHitResult && !this.isRemoved()){
            if(this.casting != null){
                this.hitList.add(blockHitResult.getBlockPos());
                this.casting.processEffects(result);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
        float f = -Mth.sin(rotationYawIn * ((float) Math.PI / 180F)) * Mth.cos(rotationPitchIn * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitchIn + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(rotationYawIn * ((float) Math.PI / 180F)) * Mth.cos(rotationPitchIn * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, inaccuracy);
    }

    public HitResult getHitResult() {
        Vec3 thisPosition = this.position();
        Vec3 nextPosition = getNextHitPosition();
        return this.level().clip(new ClipContext(thisPosition, nextPosition,ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this));
    }

    public Vec3 getNextHitPosition() {
        return this.position().add(this.getDeltaMovement());
    }

    public void traceAnyHit(@Nullable HitResult raytraceresult, Vec3 thisPosition, Vec3 nextPosition) {
        if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS) {
            nextPosition = raytraceresult.getLocation();
        }
        EntityHitResult entityraytraceresult = this.findHitEntity(thisPosition, nextPosition);
        if (entityraytraceresult != null) {
            raytraceresult = entityraytraceresult;
        }

        if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS) {
            this.onHit(raytraceresult);
            this.hasImpulse = true;
        }
    }

    public @Nullable HitResult transformHitResult(@Nullable HitResult hitResult) {
        if(hitResult instanceof BlockHitResult hitResult1){
            return new BlockHitResult(hitResult1.getLocation(), hitResult1.getDirection(), hitResult1.getBlockPos(), false);
        }
        return hitResult;
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    public void tickNextPosition() {
        Vec3 vec3d = this.getDeltaMovement();
        double x = this.getX() + vec3d.x;
        double y = this.getY() + vec3d.y;
        double z = this.getZ() + vec3d.z;
        if (!this.isNoGravity()) {
            Vec3 vec3d1 = this.getDeltaMovement();
            this.setDeltaMovement(vec3d1.x, vec3d1.y, vec3d1.z);
        }
        this.setPos(x, y, z);
    }

    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        Entity entity = this.level().getEntity(pPacket.getData());
        if (entity != null) {
            this.setOwner(entity);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        isNoGravity = tag.getBoolean("gravity");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("gravity", isNoGravity);
    }

}
