package net.iicosahedra.perplexity.ritual.rituals;

import net.iicosahedra.perplexity.ritual.AbstractRitual;
import net.iicosahedra.perplexity.ritual.RitualContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;


public class TestRitual extends AbstractRitual {

    public static final BlockPos[] shape = new BlockPos[]{
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(0, 0, 1),
            new BlockPos(0, 0, -1)
    };
    public static final TestRitual INSTANCE = new TestRitual("test", shape);

    public TestRitual(String name, BlockPos[] shape) {
        super(name, shape);
    }

    @Override
    public void activate(Level world, RitualContext context) {
        for(int i = 0; i < 15; i++){
            context.caster.setDeltaMovement(0, 1000, 0);
        }
    }
}
