package net.iicosahedra.perplexity.spell.effects;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.Affinities;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class EffectFling extends AbstractEffect {
    public EffectFling() {
        super(ResourceLoc.create("effect.fling"), "fling", 0, 0, Affinities.AIR);

    }
    @Override
    public void onCastOnEntity(EntityHitResult entityHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellCasting casting) {
        Vec3 look = caster.getLookAngle();
        Entity target = entityHitResult.getEntity();
        if(target instanceof LivingEntity){
            target.setDeltaMovement(1.5*look.x(), 1.5*look.y(), 1.5*look.z());
        }
    }

    @Override
    public Map<BlockPos, Block> getShape() {
        return Map.of();
    }
}
