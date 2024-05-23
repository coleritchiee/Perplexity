package net.iicosahedra.perplexity.spell.event;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.spell.BurnoutSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.UUID;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SpellEventHandler {

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent event) {
        if(event.getContext().getCaster() instanceof Player player && !player.level().isClientSide()){
            UUID playerId = player.getUUID();
            ServerLevel serverLevel = (ServerLevel) ServerLifecycleHooks.getCurrentServer().overworld();
            BurnoutSavedData burnoutData = BurnoutSavedData.get(serverLevel);

            if (burnoutData.isInBurnout(playerId)) {
                player.displayClientMessage(Component.translatable("Your mana refuses to respond to your will"), true);
                event.setCanceled(true);
            } else {
                burnoutData.addMana(playerId, event.getContext().getSpell().getManaCost());
            }
        }
    }
}
