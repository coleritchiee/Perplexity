package net.iicosahedra.perplexity.spell.shapes;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.entity.EntityProj;
import net.iicosahedra.perplexity.spell.targeting.CastResult;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ShapeProj extends AbstractShape {
    public ShapeProj() {
        super(ResourceLoc.create("shape.proj"),"proj",0,0);
    }
    public final Map<BlockPos, Block> shape = new HashMap<>() {{
        put(new BlockPos(1, 0, 0), Blocks.STONE);
        put(new BlockPos(0, 0, 0), Blocks.STONE);
        put(new BlockPos(-1, 0, 0), Blocks.STONE);
        put(new BlockPos(0, 0, 1), Blocks.STONE);
        put(new BlockPos(0, 0, -1), Blocks.STONE);
        put(new BlockPos(1, 0, -1), Blocks.STONE);
        put(new BlockPos(-1, 0, -1), Blocks.STONE);
        put(new BlockPos(1, 0, 1), Blocks.STONE);
        put(new BlockPos(-1, 0, 1), Blocks.STONE);
    }};
    @Override
    public CastResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellCasting casting) {
        shootProj(world, playerEntity, casting);
        return CastResult.SUCCESS;
    }

    public void shootProj(Level world, LivingEntity shooter, SpellCasting casting){
        EntityProj proj = new EntityProj(world, casting);
        proj.shoot(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 1.5f, 0.8f);
        world.addFreshEntity(proj);
    }

    @Override
    public Map<BlockPos, Block> getShape() {
        return shape;
    }
}
