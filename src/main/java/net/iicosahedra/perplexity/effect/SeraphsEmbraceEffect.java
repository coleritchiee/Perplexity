package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.item.SeraphsEmbraceItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SeraphsEmbraceEffect{
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getEntity() instanceof Player source) {
            if (CuriosUtil.findFirstEquipped(source, SeraphsEmbraceItem.class).isPresent()) {
                var stack = CuriosUtil.findFirstEquipped(source, SeraphsEmbraceItem.class).get();
                if (ItemData.getCooldown(stack) == 0) {
                    if(source.getHealth() - event.getAmount() <= source.getMaxHealth()*0.3) {
                        float shieldAmount = 20f + source.getData(Registration.MANA) * 0.02f;
                        source.getAttribute(Attributes.MAX_ABSORPTION).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.seraphs.am"), shieldAmount, AttributeModifier.Operation.ADD_VALUE));
                        source.setAbsorptionAmount(source.getAbsorptionAmount() + shieldAmount);
                        ItemData.setCooldown(stack, 90*20);
                        TickScheduler.schedule(60, () -> {
                            source.setAbsorptionAmount(source.getAbsorptionAmount() - shieldAmount);
                            if (Objects.requireNonNull(source.getAttribute(Attributes.MAX_ABSORPTION)).hasModifier(ResourceLoc.create("attribute.perplexity.seraphs.am"))) {
                                source.getAttribute(Attributes.MAX_ABSORPTION).removeModifier(ResourceLoc.create("attribute.perplexity.seraphs.am"));
                            }
                        });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(CuriosUtil.findFirstEquipped(source, SeraphsEmbraceItem.class).isPresent()){
                int amount = source.getData(Registration.MANA) + (int)(event.getEntity().getMaxHealth()*(1/Math.max(Math.log10(source.getData(Registration.MANA)),1)));
                if (amount < 1000) {
                    source.setData(Registration.MANA, amount);
                }
                else{
                    source.setData(Registration.MANA, 1000);
                }
            }
        }
    }
}
