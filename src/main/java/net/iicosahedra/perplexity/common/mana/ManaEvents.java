package net.iicosahedra.perplexity.common.mana;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ManaEvents {

    @SubscribeEvent
    public static void onPlayerLoad(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        player.getData(Registration.MANA);
        player.getData(Registration.ABILITY_POWER);
        player.getData(Registration.MAGIC_RESIST);
        player.getData(Registration.ABILITY_HASTE);
        player.getData(Registration.CRIT_CHANCE);
        player.getData(Registration.LIFE_STEAL);
        player.getData(Registration.SPELL_CRIT);
        player.getData(Registration.TIER);
        if(player.getData(Registration.AFFINITY)==-1){
            //TODO make random
            player.setData(Registration.AFFINITY, 0);
        }
    }
}
