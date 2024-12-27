package net.iicosahedra.perplexity.attribute;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CritEventHandler {
    @SubscribeEvent
    public static void critChance(CriticalHitEvent event) {
        if(event.getEntity() instanceof Player){
            Player source = (Player)event.getEntity();
            double critChance = Objects.requireNonNull(source.getAttribute(Registration.CRIT_CHANCE)).getValue();
            if(critChance > 0){
                if(Math.random()*100 <= critChance){
                    event.setCriticalHit(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void critDamage(CriticalHitEvent event) {
        if(event.getEntity() instanceof Player){
            Player source = (Player)event.getEntity();
            double critDamage = Objects.requireNonNull(source.getAttribute(Registration.CRIT_DAMAGE)).getValue();
            if(critDamage > 0){
                event.setDamageMultiplier(event.getDamageMultiplier()+(float)(critDamage/100.0));
            }
        }
    }
}
