package net.iicosahedra.perplexity.spell.component;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.SpellResolver;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class AbstractEffect extends AbstractSpellPart{
    public AbstractEffect(ResourceLocation registryName, String name, String affinity) {
        super(registryName, name);
        this.affinity = affinity;
    }

    public String affinity;

    public AbstractEffect(String tag, String name, String affinity) {
        super(tag, name);
        this.affinity = affinity;
    }



    public void onCast(HitResult hitResult, Level world, @NotNull LivingEntity caster, SpellContext spellContext, SpellResolver resolver) {
        if(hitResult instanceof BlockHitResult blockHitResult){
            onCastOnBlock(blockHitResult, world, caster, spellContext, resolver);
        }
        else if(hitResult instanceof EntityHitResult entityHitResult){
            onCastOnEntity(entityHitResult, world, caster, spellContext, resolver);
        }
    }

    public void onCastOnEntity(EntityHitResult entityHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellResolver resolver) {
    }

    public void onCastOnBlock(BlockHitResult blockHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellResolver resolver) {
    }

    public Player getPlayer(LivingEntity entity, ServerLevel world) {
        return entity instanceof Player player ? player : null;
    }

    public Vec3 getEntityHitPos(HitResult result) {
        return result instanceof EntityHitResult entityHitResult ? entityHitResult.getEntity().position() : result.getLocation();
    }

    public String getAffinity(){
        return affinity;
    }

}
