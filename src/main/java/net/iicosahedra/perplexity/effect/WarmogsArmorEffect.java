package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class WarmogsArmorEffect extends MobEffect {
    public  WarmogsArmorEffect() {
        super(MobEffectCategory.NEUTRAL, 0x009317);
    }

    @SubscribeEvent
    public static void onHurt(LivingIncomingDamageEvent event) {
        if(event.getEntity().hasEffect(Registration.WARMOGS_EFFECT)){
            LivingEntity entity = event.getEntity();
            entity.setData(Registration.WARMOGS_COMBAT_COOLDOWN.value(), (int)(120* CooldownCalc.cooldownReduction(entity.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
    }
}
