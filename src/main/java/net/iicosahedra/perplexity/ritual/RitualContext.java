package net.iicosahedra.perplexity.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class RitualContext {
    public Player caster;
    public BlockPos activationPoint;

    public RitualContext(Player caster, BlockPos activationPoint){
        this.caster = caster;
        this.activationPoint = activationPoint;
    }

}
