package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TwilightsEdgeEffect extends MobEffect {
    public TwilightsEdgeEffect() {
        super(MobEffectCategory.NEUTRAL, 0x0381ff);
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player player) {
            if(player.hasEffect(Registration.TWILIGHTS_EFFECT)){
                player.setData(Registration.TWILIGHTS_COOLDOWN, 100);
            }
        }
    }
}
