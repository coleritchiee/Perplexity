package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.item.NavoriFlickerbladeItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class NavoriFlickerBladeEffect{
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(CuriosUtil.findFirstEquipped(source, NavoriFlickerbladeItem.class).isPresent() && !source.equals(event.getEntity())) {
                CuriosApi.getCuriosInventory(source).ifPresent(handler -> {
                    handler.getCurios().values().forEach(curio -> {
                        for (int i = 0; i < curio.getStacks().getSlots(); i++) {
                            var stack = curio.getStacks().getStackInSlot(i);
                            if (!stack.isEmpty()) {
                                Integer cd = stack.get(Registration.COMPONENT_COOLDOWN_TICKS.value());
                                if (cd != null && cd > 0) {
                                    int reduced = (int)(cd * 0.85);
                                    stack.set(Registration.COMPONENT_COOLDOWN_TICKS.value(), reduced);
                                }
                            }
                        }
                    });
                });
            }
        }
    }
}
