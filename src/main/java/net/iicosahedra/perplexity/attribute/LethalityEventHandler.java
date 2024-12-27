package net.iicosahedra.perplexity.attribute;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LethalityEventHandler {
    @SubscribeEvent
    public static void flatArmorPen(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            double amount = source.getAttribute(Registration.LETHALITY).getValue();
            event.getContainer().addModifier(
                    DamageContainer.Reduction.ARMOR,
                    (container, baseReduction) -> (float) (baseReduction-(amount/2))
            );
        }
    }

    @SubscribeEvent
    public static void percentArmorPen(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            double amount = source.getAttribute(Registration.ARMOR_PEN).getValue();
            event.getContainer().addModifier(
                    DamageContainer.Reduction.ARMOR,
                    (container, baseReduction) -> (float) (baseReduction*((100-amount/2)/100))
            );
        }
    }
}
