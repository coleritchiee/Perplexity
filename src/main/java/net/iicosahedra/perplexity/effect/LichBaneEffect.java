package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.client.particle.LineEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LichBaneEffect extends MobEffect {
    public LichBaneEffect() {
        super(MobEffectCategory.NEUTRAL, 0xc014ff);
    }

    public static void processLichConditions(Player player){
        player.setData(Registration.LICH_FLAG.value(), 1);
        player.getAttribute(Attributes.ATTACK_SPEED).addOrUpdateTransientModifier(new AttributeModifier(ResourceLoc.create("attributes.lich.effect"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(source.hasEffect(Registration.LICH_EFFECT)&&!source.equals(event.getEntity())) {
                if (source.getData(Registration.LICH_FLAG.value()) == 1) {
                    source.setData(Registration.LICH_FLAG.value(), 0);
                    event.getEntity().hurt(new DamageSource(event.getEntity().level()
                            .registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MAGIC), event.getEntity(), source),
                            (float)(source.getAttributeValue(Registration.ABILITY_POWER)*0.4+source.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.75));

                    LineEffect.createLineParticles(source, event.getEntity(), Registration.LICH_EFFECT.value().getColor(), 1, 5);
                    source.getAttribute(Attributes.ATTACK_SPEED).removeModifier(ResourceLoc.create("attributes.lich.effect"));
                }
            }
        }
    }
}
