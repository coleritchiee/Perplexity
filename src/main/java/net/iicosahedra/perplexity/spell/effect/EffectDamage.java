package net.iicosahedra.perplexity.spell.effect;

import net.iicosahedra.perplexity.spell.Affinities;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.SpellResolver;
import net.iicosahedra.perplexity.spell.component.AbstractEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class EffectDamage extends AbstractEffect {
    public static final EffectDamage INSTANCE = new EffectDamage();

    public EffectDamage() {
        super("effect.damagetest", "damage", Affinities.TEST);
    }

    @Override
    public void onCastOnEntity(EntityHitResult entityHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellResolver resolver) {
        Entity target = entityHitResult.getEntity();
        target.kill();
    }
}
