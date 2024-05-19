package net.iicosahedra.perplexity.ritual;

import com.mojang.datafixers.util.Pair;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractRitual {
    String name;
    List<BlockPos> blocks = new ArrayList<>();

    public AbstractRitual(String name, BlockPos[] shape){
        this.name = name;
        this.blocks = Arrays.stream(shape).toList();
    }

    public AbstractRitual(String name){
        this.name = name;
    }

    public boolean matches(Level world, BlockPos activationPoint){
        for (BlockPos offset : blocks) {
            if (!world.getBlockState(activationPoint.offset(offset)).getBlock().equals(Registration.EXAMPLE_BLOCK.get())) {
                return false;
            }
        }
        return true;
    }

    public abstract void activate(Level world, RitualContext context);
}
