package net.iicosahedra.perplexity.attribute;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class MagicResistEventHandler {
    @SubscribeEvent
    public static void mr(LivingDamageEvent.Pre event) {
        if(event.getEntity() instanceof Player player){
            if(event.getSource().getEntity() instanceof Player source) {
                if (event.getSource().type().equals(player.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                        .getOrThrow(DamageTypes.MAGIC).value())) {
                    event.setNewDamage((float) (event.getNewDamage() * (10 / (10 + (player.getAttributeValue(Registration.MAGIC_RESIST) * (source.getAttributeValue(Registration.MAGIC_PEN)/100))))));
                }
            }
        }
    }
}
