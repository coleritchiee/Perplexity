package net.iicosahedra.perplexity.spell.shapes;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.targeting.CastResult;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.Map;

public class ShapeTouch extends AbstractShape {

    public ShapeTouch() {
        super(ResourceLoc.create("shape.touch"), "touch", 0, 0);
    }

    @Override
    public CastResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellCasting casting) {
        if(playerEntity instanceof Player) {
            HitResult result = playerEntity.pick(((Player) playerEntity).getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE), 1.0F, false);
            casting.processEffects(result);
            return CastResult.SUCCESS;
        }
        return CastResult.FAIL;
    }

    @Override
    public Map<BlockPos, Block> getShape() {
        return Map.of();
    }
}
