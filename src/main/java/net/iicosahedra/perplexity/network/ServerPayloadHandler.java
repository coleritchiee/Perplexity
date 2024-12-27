package net.iicosahedra.perplexity.network;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.network.packet.AbilityUsePacket;
import net.iicosahedra.perplexity.network.packet.ProcessDeltaMovementPacket;
import net.iicosahedra.perplexity.network.packet.ProcessFlightMovementPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler{
    public static void handleAbility(final AbilityUsePacket abilityUsePacket, final IPayloadContext context){
        ActiveAbility.useAbility(context.player(), abilityUsePacket.slot());
    }

    public static void processDeltaMovement(final ProcessDeltaMovementPacket packet, final IPayloadContext context){

    }
    public static void processFlightMovement(final ProcessFlightMovementPacket packet, final IPayloadContext context){
    }
}
