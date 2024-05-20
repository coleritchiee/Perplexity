package net.iicosahedra.perplexity.common.block;

import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ActiveCircuitEntity extends BlockEntity {
    private BlockPos headPos;

    public ActiveCircuitEntity(BlockPos pos, BlockState state) {
        super(Registration.ACTIVE_CIRCUIT_ENTITY.get(), pos, state);
    }

    public void setHeadPos(BlockPos headPos) {
        this.headPos = headPos;
    }

    public BlockPos getHeadPos() {
        return headPos;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider p_338445_) {
        super.loadAdditional(tag, p_338445_);
        if (tag.contains("HeadPosX") && tag.contains("HeadPosY") && tag.contains("HeadPosZ")) {
            int x = tag.getInt("HeadPosX");
            int y = tag.getInt("HeadPosY");
            int z = tag.getInt("HeadPosZ");
            this.headPos = new BlockPos(x, y, z);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider p_323635_) {
        super.saveAdditional(tag,p_323635_);
        if (this.headPos != null) {
            tag.putInt("HeadPosX", this.headPos.getX());
            tag.putInt("HeadPosY", this.headPos.getY());
            tag.putInt("HeadPosZ", this.headPos.getZ());
        }
    }
}
