package net.iicosahedra.perplexity.setup;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.client.render.RenderSpellCastEntity;
import net.iicosahedra.perplexity.client.render.RenderSpellProj;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(Registration.SPELL_PROJ.get(), renderer -> new RenderSpellProj(renderer, null));
        event.registerEntityRenderer(Registration.SPELL_CAST_ENTITY.get(), renderer -> new RenderSpellCastEntity(renderer, null));
    }
}
