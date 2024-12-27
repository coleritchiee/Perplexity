package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class NavoriFlickerBladeEffect extends MobEffect {
    public static List<DeferredHolder<AttachmentType<?>, AttachmentType<Integer>>> cooldowns = new ArrayList<>();

    public NavoriFlickerBladeEffect() {
        super(MobEffectCategory.NEUTRAL, 0x814cfe);
        cooldowns.add(Registration.DIVINE_SUNDERER_COOLDOWN);
        cooldowns.add(Registration.WARMOGS_COMBAT_COOLDOWN);
        cooldowns.add(Registration.TRIFORCE_COOLDOWN);
        cooldowns.add(Registration.GUNBLADE_COOLDOWN);
        cooldowns.add(Registration.ROCKETBELT_COOLDOWN);
        cooldowns.add(Registration.YOUMUUS_COOLDOWN);
        cooldowns.add(Registration.PROWLERS_COOLDOWN);
        cooldowns.add(Registration.SPECTRAL_COOLDOWN);
        cooldowns.add(Registration.GOREDRINKER_COOLDOWN);
        cooldowns.add(Registration.STRIDEBREAKER_COOLDOWN);
        cooldowns.add(Registration.OMEN_COOLDOWN);
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(source.hasEffect(Registration.NAVORI_EFFECT)&&!source.equals(event.getEntity())) {
                cooldowns.forEach(deferredHolder -> {
                    source.setData(deferredHolder, (int)(source.getData(deferredHolder)*0.85));
                });
            }
        }
    }
}
