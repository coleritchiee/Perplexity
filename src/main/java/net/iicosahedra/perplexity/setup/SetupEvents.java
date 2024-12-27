package net.iicosahedra.perplexity.setup;

import net.iicosahedra.perplexity.Perplexity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SetupEvents {
    @SubscribeEvent
    static void registerAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, Registration.ABILITY_HASTE);
        event.add(EntityType.PLAYER, Registration.ABILITY_POWER);
        event.add(EntityType.PLAYER, Registration.CRIT_CHANCE);
        event.add(EntityType.PLAYER, Registration.CRIT_DAMAGE);
        event.add(EntityType.PLAYER, Registration.LETHALITY);
        event.add(EntityType.PLAYER, Registration.OMNIVAMP);
        event.add(EntityType.PLAYER, Registration.MAGIC_PEN);
        event.add(EntityType.PLAYER, Registration.MAGIC_RESIST);
        event.add(EntityType.PLAYER, Registration.ARMOR_PEN);
    }

    @SubscribeEvent
    static void registerRegistries(NewRegistryEvent event){
        event.register(Registration.ABILITY_REGISTRY);
    }
}
