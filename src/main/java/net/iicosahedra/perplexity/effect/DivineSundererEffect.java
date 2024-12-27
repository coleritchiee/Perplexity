package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.client.particle.LineEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DivineSundererEffect extends MobEffect {
    public DivineSundererEffect() {
        super(MobEffectCategory.NEUTRAL, 0x2ac6f8);
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(source.hasEffect(Registration.DIVINE_SUNDERER_EFFECT)&&!source.equals(event.getEntity())) {
                if (source.getData(Registration.DIVINE_SUNDERER_COOLDOWN.value()) == 0) {
                    float damage = event.getOriginalAmount()
                            + (float)(1.6* Objects.requireNonNull(source.getAttribute(Attributes.ATTACK_DAMAGE)).getValue())
                            + (float)(event.getEntity().getMaxHealth()*0.04);
                    event.setAmount(damage);
                    source.heal((float)(0.64* Objects.requireNonNull(source.getAttribute(Attributes.ATTACK_DAMAGE)).getValue()
                            +(float)(event.getEntity().getMaxHealth()*0.016)));
                    LineEffect.createLineParticles(source, event.getEntity(), Registration.DIVINE_SUNDERER_EFFECT.value().getColor(), 1, 5);
                    source.setData(Registration.DIVINE_SUNDERER_COOLDOWN.value(), (int)(200* CooldownCalc.cooldownReduction(source.getAttribute(Registration.ABILITY_HASTE).getValue())));
                }
            }
        }
    }
}
