package net.iicosahedra.perplexity.setup;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.client.KeyBinding;
import net.iicosahedra.perplexity.client.render.RocketbeltProjectileRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.checkerframework.dataflow.qual.SideEffectFree;

@EventBusSubscriber(value = Dist.CLIENT, modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(Registration.ROCKETBELT_PROJ.get(), RocketbeltProjectileRenderer::new);
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.ABILITY_1);
        event.register(KeyBinding.ABILITY_2);
        event.register(KeyBinding.ABILITY_3);
        event.register(KeyBinding.ABILITY_4);
    }
}
