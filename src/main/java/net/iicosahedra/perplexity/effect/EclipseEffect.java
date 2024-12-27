package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EclipseEffect extends MobEffect {
    public EclipseEffect() {
        super(MobEffectCategory.NEUTRAL, 0x8f3d00);
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player source) {
            if (source.hasEffect(Registration.ECLIPSE_EFFECT)) {
                if (source.getData(Registration.ECLIPSE_COOLDOWN) == 0) {
                    float damage = event.getAmount() + event.getEntity().getMaxHealth()*0.06f;
                    event.setAmount(damage);
                    float shieldAmount = 16f+(float)(0.4*source.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                    source.getAttribute(Attributes.MAX_ABSORPTION).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.eclipse.am"), shieldAmount, AttributeModifier.Operation.ADD_VALUE));
                    source.setAbsorptionAmount(source.getAbsorptionAmount() + shieldAmount);
                    source.setData(Registration.ECLIPSE_COOLDOWN.value(), 120);
                    TickScheduler.schedule(40, () ->{
                        source.setAbsorptionAmount(source.getAbsorptionAmount() - shieldAmount);
                        if(Objects.requireNonNull(source.getAttribute(Attributes.MAX_ABSORPTION)).hasModifier(ResourceLoc.create("attribute.perplexity.eclipse.am"))){
                            source.getAttribute(Attributes.MAX_ABSORPTION).removeModifier(ResourceLoc.create("attribute.perplexity.eclipse.am"));
                        }
                    });
                }
            }
        }
    }
}
