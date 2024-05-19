package net.iicosahedra.perplexity.spell.effects;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.Affinities;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Map;

public class EffectDamage extends AbstractEffect {
    public EffectDamage() {
        super(ResourceLoc.create("effect.damage"), "damage",0 ,0, Affinities.TYPELESS);
    }

    @Override
    public void onCastOnEntity(EntityHitResult entityHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellCasting casting) {
        Entity target = entityHitResult.getEntity();
        target.kill();
    }

    @Override
    public Map<BlockPos, Block> getShape() {
        return Map.of();
    }
}