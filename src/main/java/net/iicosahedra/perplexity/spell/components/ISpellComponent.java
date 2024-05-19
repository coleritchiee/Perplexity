package net.iicosahedra.perplexity.spell.components;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public interface ISpellComponent {
    public ResourceLocation getRegistryName();
    public Map<BlockPos, Block> getShape();
}
