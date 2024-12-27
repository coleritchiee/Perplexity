package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.client.particle.LineEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TrinityForceEffect extends MobEffect {
    public TrinityForceEffect() {
        super(MobEffectCategory.NEUTRAL, 0xfff300);
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(source.hasEffect(Registration.TRINITY_EFFECT)&&!source.equals(event.getEntity())) {
                if (source.getData(Registration.TRIFORCE_COOLDOWN.value()) == 0) {
                    float damage = event.getOriginalAmount()
                            + (float)(2* Objects.requireNonNull(source.getAttribute(Attributes.ATTACK_DAMAGE)).getValue());
                    event.setAmount(damage);
                    LineEffect.createLineParticles(source, event.getEntity(), Registration.TRINITY_EFFECT.value().getColor(), 1, 5);
                    source.setData(Registration.TRIFORCE_COOLDOWN.value(), (int)(200* CooldownCalc.cooldownReduction(source.getAttribute(Registration.ABILITY_HASTE).getValue())));
                }
            }
        }
    }
}
