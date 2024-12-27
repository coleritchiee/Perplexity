package net.iicosahedra.perplexity.network;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.network.packet.AbilityUsePacket;
import net.iicosahedra.perplexity.network.packet.ProcessDeltaMovementPacket;
import net.iicosahedra.perplexity.network.packet.ProcessFlightMovementPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterPackets {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                AbilityUsePacket.TYPE,
                AbilityUsePacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::handleAbility,
                        ServerPayloadHandler::handleAbility
                )
        );
        registrar.playBidirectional(
                ProcessDeltaMovementPacket.TYPE,
                ProcessDeltaMovementPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::processDeltaMovement,
                        ServerPayloadHandler::processDeltaMovement
                )
        );
        registrar.playBidirectional(
                ProcessFlightMovementPacket.TYPE,
                ProcessFlightMovementPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::processFlightMovement,
                        ServerPayloadHandler::processFlightMovement
                )
        );
    }
}
