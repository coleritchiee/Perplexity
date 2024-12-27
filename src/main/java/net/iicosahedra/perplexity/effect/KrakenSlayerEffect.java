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
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class KrakenSlayerEffect extends MobEffect {
    public KrakenSlayerEffect(){
        super(MobEffectCategory.NEUTRAL, 0xf1ad00);
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(source.hasEffect(Registration.KRAKEN_EFFECT)) {
                if (source.getData(Registration.KRAKEN_SLAYER_STACKS.value()) < 2) {
                    source.setData(Registration.KRAKEN_SLAYER_STACKS.value(), source.getData(Registration.KRAKEN_SLAYER_STACKS.value()) + 1);
                } else if (source.getData(Registration.KRAKEN_SLAYER_STACKS.value()) == 2) {
                    float damage = event.getOriginalAmount();
                    source.setData(Registration.KRAKEN_SLAYER_STACKS.value(), 0);
                    event.setAmount((float) (damage + 4.0 * (1 + (1 - (event.getEntity().getHealth() / event.getEntity().getMaxHealth())))));
                }
            }
        }
    }
}
