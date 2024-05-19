package net.iicosahedra.perplexity.spell.shapes;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.targeting.CastResult;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ShapeSelf extends AbstractShape {
    public ShapeSelf() {
        super(ResourceLoc.create("shape.self"), "self", 0, 0);
    }

    @Override
    public CastResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellCasting casting) {
        casting.processEffects(new EntityHitResult(playerEntity));
        return CastResult.SUCCESS;
    }

    @Override
    public Map<BlockPos, Block> getShape() {
        return Map.of();
    }
}
