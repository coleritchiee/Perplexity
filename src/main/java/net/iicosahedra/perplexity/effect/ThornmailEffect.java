package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ThornmailEffect extends MobEffect {
    public ThornmailEffect() {
        super(MobEffectCategory.NEUTRAL, 0x18ff03);
    }

    @SubscribeEvent
    public static void onHurt(LivingIncomingDamageEvent event) {
        if(event.getEntity() instanceof Player player){
            if(player.hasEffect(Registration.THORNMAIL_EFFECT)&&event.getSource().getEntity() instanceof LivingEntity source){
                source.addEffect(new MobEffectInstance(Registration.GRIEVOUS_WOUNDS_EFFECT, 3*20, 0, false, false));
            }
        }
    }
}