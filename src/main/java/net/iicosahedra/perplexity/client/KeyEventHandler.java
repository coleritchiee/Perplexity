package net.iicosahedra.perplexity.client;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.network.packet.AbilityUsePacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(value = Dist.CLIENT, modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class KeyEventHandler {
    @SubscribeEvent
    public static void onKeyInput(ClientTickEvent.Post event) {
        while(KeyBinding.ABILITY_1.consumeClick()){
            PacketDistributor.sendToServer(new AbilityUsePacket(1));
        }
        while(KeyBinding.ABILITY_2.consumeClick()){
            PacketDistributor.sendToServer(new AbilityUsePacket(2));
        }
        while(KeyBinding.ABILITY_3.consumeClick()){
            PacketDistributor.sendToServer(new AbilityUsePacket(3));
        }
        while(KeyBinding.ABILITY_4.consumeClick()){
            PacketDistributor.sendToServer(new AbilityUsePacket(4));
        }
    }
}
