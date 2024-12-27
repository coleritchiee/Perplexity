package net.iicosahedra.perplexity.entity;

import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RocketbeltProjectileEntity extends Projectile {
    public int age;
    public boolean isNoGravity = true;

    public RocketbeltProjectileEntity(EntityType<? extends RocketbeltProjectileEntity> type, Level world){
        super(type, world);
    }

    public RocketbeltProjectileEntity(Level world, LivingEntity shooter){
        super(Registration.ROCKETBELT_PROJ.get(), world);
        setOwner(shooter);
        Vec3 lookDirection = shooter.getLookAngle();
        double offsetX = shooter.getX() + lookDirection.x;
        double offsetY = shooter.getEyeY() + lookDirection.y - 0.1F;
        double offsetZ = shooter.getZ() + lookDirection.z;
        setPos(offsetX, offsetY, offsetZ);
    }



    @Override
    public void tick() {
        super.tick();
        age++;

        if(age >= 15.0){
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
        if(!level().isClientSide && result instanceof EntityHitResult entityHitResult){
            if(entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                if (this.getOwner() instanceof Player p) {
                    if (livingEntity.equals(this.getOwner())) {
                        return;
                    }
                    livingEntity.hurt(new DamageSource(p.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                            .getOrThrow(DamageTypes.MAGIC), livingEntity, this.getOwner()), (float)(10 + 0.1 * p.getAttributeValue(Registration.ABILITY_POWER)));
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
        if(!level().isClientSide && result instanceof BlockHitResult) {
            this.remove(RemovalReason.DISCARDED);
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


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }
}
