package net.iicosahedra.perplexity.util;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ItemCooldownTicker {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        CuriosApi.getCuriosInventory(event.getEntity()).ifPresent(handler -> {
            handler.getCurios().values().forEach(curio -> {
                for (int i = 0; i < curio.getStacks().getSlots(); i++) {
                    var stack = curio.getStacks().getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        Integer cd = stack.get(Registration.COMPONENT_COOLDOWN_TICKS.value());
                        if (cd != null && cd > 0) {
                            stack.set(Registration.COMPONENT_COOLDOWN_TICKS.value(), cd - 1);
                        }
                    }
                }
            });
        });
    }
}


