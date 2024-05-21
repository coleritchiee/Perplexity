package net.iicosahedra.perplexity.spell.event;

import net.iicosahedra.perplexity.Perplexity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SpellEventHandler {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent event) {
        event.setCanceled(true);
    }
}
