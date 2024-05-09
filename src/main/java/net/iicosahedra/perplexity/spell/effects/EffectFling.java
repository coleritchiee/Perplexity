package net.iicosahedra.perplexity.spell.effects;

import net.iicosahedra.perplexity.spell.Affinities;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EffectFling extends AbstractEffect {

    public static final EffectFling INSTANCE = new EffectFling();
    public EffectFling() {
        super("effect.fling", "fling", 0, 0, Affinities.AIR);

    }
    @Override
    public void onCastOnEntity(EntityHitResult entityHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellCasting casting) {
        Vec3 look = caster.getLookAngle();
        Entity target = entityHitResult.getEntity();
        if(target instanceof LivingEntity){
            target.setDeltaMovement(1.5*look.x(), 1.5*look.y(), 1.5*look.z());
        }
    }
}