package net.iicosahedra.perplexity.spell.effects;

import net.iicosahedra.perplexity.spell.Affinities;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class EffectDamage extends AbstractEffect {
    public static final EffectDamage INSTANCE = new EffectDamage();

    public EffectDamage() {
        super("effect.damagetest", "damage",0 ,0, Affinities.TYPELESS);
    }

    @Override
    public void onCastOnEntity(EntityHitResult entityHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellCasting casting) {
        Entity target = entityHitResult.getEntity();
        target.kill();
    }
}