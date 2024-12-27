package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
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
public class SeraphsEmbraceEffect extends MobEffect {
    public SeraphsEmbraceEffect() {
        super(MobEffectCategory.NEUTRAL, 0x14dce3);
    }

    @SubscribeEvent
    public static void onTick(PlayerTickEvent.Post event) {
        //TODO FIX CONSTANT TICK BUG
        if (event.getEntity().hasEffect(Registration.SERAPHS_EFFECT)) {
            event.getEntity().getAttribute(Registration.ABILITY_POWER).addOrUpdateTransientModifier(
                    new AttributeModifier(ResourceLoc.create("attributes.perplexity.seraphs_conv"), 0.02 * event.getEntity().getData(Registration.MANA), AttributeModifier.Operation.ADD_VALUE));

        }
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player source) {
            if (source.hasEffect(Registration.SERAPHS_EFFECT)) {
                if (source.getData(Registration.SERAPHS_COOLDOWN) == 0) {
                    if(source.getHealth() - event.getAmount() <= source.getMaxHealth()*0.3) {
                        float shieldAmount = 20f + source.getData(Registration.MANA) * 0.02f;
                        source.getAttribute(Attributes.MAX_ABSORPTION).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.seraphs.am"), shieldAmount, AttributeModifier.Operation.ADD_VALUE));
                        source.setAbsorptionAmount(source.getAbsorptionAmount() + shieldAmount);
                        source.setData(Registration.SERAPHS_COOLDOWN.value(), 90*20);
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
            if(source.hasEffect(Registration.SERAPHS_EFFECT)){
                source.setData(Registration.MANA, source.getData(Registration.MANA) + (int)(event.getEntity().getMaxHealth()*(1/Math.max(Math.log10(source.getData(Registration.MANA)),1))));
            }
        }
    }
}
