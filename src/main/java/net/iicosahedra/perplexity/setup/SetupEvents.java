package net.iicosahedra.perplexity.setup;

import net.iicosahedra.perplexity.Perplexity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SetupEvents {
    @SubscribeEvent
    static void registerRegistries(NewRegistryEvent event) {
        event.register(Registration.SPELL_REGISTRY);
    }
}
