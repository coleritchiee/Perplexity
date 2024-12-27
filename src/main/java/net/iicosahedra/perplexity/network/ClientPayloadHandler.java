package net.iicosahedra.perplexity.network;

import net.iicosahedra.perplexity.network.packet.AbilityUsePacket;
import net.iicosahedra.perplexity.network.packet.ProcessDeltaMovementPacket;
import net.iicosahedra.perplexity.network.packet.ProcessFlightMovementPacket;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    public static void handleAbility(final AbilityUsePacket abilityUsePacket, final IPayloadContext context){

    }

    public static void processDeltaMovement(final ProcessDeltaMovementPacket packet, final IPayloadContext context){
        context.player().addDeltaMovement(packet.v);
    }

    public static void processFlightMovement(final ProcessFlightMovementPacket packet, final IPayloadContext context){
        context.player().setDeltaMovement(packet.v);
    }
}
