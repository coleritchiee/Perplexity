package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.client.particle.LineEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EnergizeEffect extends MobEffect {
    public EnergizeEffect() {
        super(MobEffectCategory.NEUTRAL, 0xffb400);
    }

    @SubscribeEvent
    public static void onMovement(PlayerTickEvent.Post event) {
        if(event.getEntity().isSprinting() && event.getEntity().getData(Registration.ENERGIZE_STACKS)<120 &&
                event.getEntity().hasEffect(Registration.ENERGIZE)){
            event.getEntity().setData(Registration.ENERGIZE_STACKS, event.getEntity().getData(Registration.ENERGIZE_STACKS)+1);
        }
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            boolean reset = false;
            Player source = (Player)event.getSource().getEntity();
            if(source.hasEffect(Registration.STORMRAZOR_EFFECT)&&!source.equals(event.getEntity())) {
                if (source.getData(Registration.ENERGIZE_STACKS) == 120) {
                    reset = true;
                    source.setData(Registration.ENERGIZE_STACKS, 0);
                    event.getEntity().hurt(new DamageSource(event.getEntity().level()
                            .registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MAGIC), event.getEntity(), source),20);
                    source.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.energize.ms"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    TickScheduler.schedule(30, ()->{
                        if(Objects.requireNonNull(source.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLoc.create("attribute.perplexity.energize.ms"))){
                            source.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ResourceLoc.create("attribute.perplexity.energize.ms"));
                        }
                    });
                }
                LineEffect.createLineParticles(source, event.getEntity(), Registration.ENERGIZE.value().getColor(), 1f, 5);
            }
            if(source.hasEffect(Registration.RAPIDFIRECANNON_EFFECT)&&!source.equals(event.getEntity())) {
                if (source.getData(Registration.ENERGIZE_STACKS) == 120 || reset) {
                    source.setData(Registration.ENERGIZE_STACKS, 0);
                    event.getEntity().hurt(new DamageSource(event.getEntity().level()
                            .registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MAGIC), event.getEntity(), source),4);
                    if(Objects.requireNonNull(source.getAttribute(Attributes.ENTITY_INTERACTION_RANGE)).hasModifier(ResourceLoc.create("attribute.perplexity.energize.eir"))){
                        source.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).removeModifier(ResourceLoc.create("attribute.perplexity.energize.eir"));
                    }
                    if(!reset){
                        LineEffect.createLineParticles(source, event.getEntity(), Registration.ENERGIZE.value().getColor(), 1f, 5);
                    }
                }
            }
        }
    }
}
