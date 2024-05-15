package net.iicosahedra.perplexity.common.block;

import net.iicosahedra.perplexity.ritual.RitualContext;
import net.iicosahedra.perplexity.ritual.RitualHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TestBlock extends Block {
    public TestBlock() {
        super(BlockBehaviour.Properties.of());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, BlockHitResult p_60508_) {
        RitualHelper.startRitual(new RitualContext(p_60506_,p_60505_), p_60506_);
        return super.useWithoutItem(p_60503_, p_60504_, p_60505_, p_60506_, p_60508_);
    }
}
