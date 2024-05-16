package net.iicosahedra.perplexity.common.block;

import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SpellStructureHeadEntity extends BlockEntity {
    public SpellStructureHeadEntity(BlockPos pPos, BlockState pBlockState) {
        super(Registration.SPELL_STRUCTURE_HEAD_ENTITY.get(),pPos, pBlockState);
    }
}
