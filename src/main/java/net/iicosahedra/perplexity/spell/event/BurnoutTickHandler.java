package net.iicosahedra.perplexity.spell.event;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.spell.BurnoutSavedData;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class BurnoutTickHandler {
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event){
        BurnoutSavedData burnoutData = BurnoutSavedData.get(ServerLifecycleHooks.getCurrentServer().overworld());
        burnoutData.decrementBurnoutTime();
    }
}
