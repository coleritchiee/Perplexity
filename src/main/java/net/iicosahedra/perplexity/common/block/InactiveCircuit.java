package net.iicosahedra.perplexity.common.block;

import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class InactiveCircuit extends Block {
    public InactiveCircuit() {
        super(BlockBehaviour.Properties.of());
    }

    public Block getActiveCircuit() {
        return Registration.ACTIVE_CIRCUIT.get();
    }
}
