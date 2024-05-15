package net.iicosahedra.perplexity.ritual;

import oshi.util.tuples.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractRitual {
    String name;
    List<Pair<BlockPos, Block>> blocks = new ArrayList<>();

    public AbstractRitual(String name){
        this.name = name;
    }

    public void setShape(List<Pair<BlockPos, Block>> shape){
        this.blocks = shape;
    }

    public boolean matches(Level world, BlockPos activationPoint){
        for (Pair<BlockPos, Block> offset : blocks) {
            if (!world.getBlockState(activationPoint.offset(offset.getA())).getBlock().equals(offset.getB())) {
                return false;
            }
        }
        return true;
    }

    public abstract void activate(Level world, RitualContext context);
}
