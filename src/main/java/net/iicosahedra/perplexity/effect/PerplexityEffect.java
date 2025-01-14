package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PerplexityEffect extends MobEffect {
    public PerplexityEffect() {
        super(MobEffectCategory.NEUTRAL, 0x8d03ff);
    }

    @SubscribeEvent
    public static void gs(LivingDamageEvent.Pre event) {
        if(event.getSource().getEntity() instanceof Player player){
            if(player.hasEffect(Registration.PERPLEXITY_EFFECT)){
                event.setNewDamage((float) (event.getNewDamage()*(1+(0.22*event.getEntity().getMaxHealth()/player.getHealth()))));
            }
        }
    }
}
