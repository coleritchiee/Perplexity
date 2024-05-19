package net.iicosahedra.perplexity.common.block;

import net.iicosahedra.perplexity.ritual.RitualContext;
import net.iicosahedra.perplexity.ritual.rituals.SpellRitual;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SpellStructureHead extends Block implements EntityBlock {
    public SpellStructureHead() {
        super(BlockBehaviour.Properties.of());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, BlockHitResult p_60508_) {
        SpellRitual.INSTANCE.activate(p_60504_, new RitualContext(p_60506_, p_60505_));
        return super.useWithoutItem(p_60503_, p_60504_, p_60505_, p_60506_, p_60508_);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
        //Registration.SPELL_STRUCTURE_HEAD_ENTITY.get().create(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return EntityBlock.super.getTicker(pLevel, pState, pBlockEntityType);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel pLevel, T pBlockEntity) {
        return EntityBlock.super.getListener(pLevel, pBlockEntity);
    }
}
