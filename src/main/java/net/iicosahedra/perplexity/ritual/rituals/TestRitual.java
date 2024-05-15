package net.iicosahedra.perplexity.ritual.rituals;

import net.iicosahedra.perplexity.setup.Registration;
import oshi.util.tuples.Pair;
import net.iicosahedra.perplexity.ritual.AbstractRitual;
import net.iicosahedra.perplexity.ritual.RitualContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class TestRitual extends AbstractRitual {

    public static final TestRitual INSTANCE = new TestRitual("test");
    public static final List<Pair<BlockPos, Block>> shape = new ArrayList<>();
    public TestRitual(String name) {
        super(name);
        shape.add(new Pair<>(new BlockPos(1, 0, 0), Registration.EXAMPLE_BLOCK.get()));
        shape.add(new Pair<>(new BlockPos(0, 0, 0), Registration.EXAMPLE_BLOCK.get()));
        shape.add(new Pair<>(new BlockPos(-1, 0, 0), Registration.EXAMPLE_BLOCK.get()));
        shape.add(new Pair<>(new BlockPos(0, 0, 1), Registration.EXAMPLE_BLOCK.get()));
        shape.add(new Pair<>(new BlockPos(0, 0, -1), Registration.EXAMPLE_BLOCK.get()));
        setShape(shape);
    }

    @Override
    public void activate(Level world, RitualContext context) {
        context.caster.kill();
    }
}
