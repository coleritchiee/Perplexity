package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EssenceReaverEffect extends MobEffect {
    public EssenceReaverEffect() {
        super(MobEffectCategory.NEUTRAL, 0x006fe0);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(source.hasEffect(Registration.ESSENCE_REAVER_EFFECT)){
                source.setData(Registration.MANA, source.getData(Registration.MANA) + (int)(event.getEntity().getMaxHealth()*(1/Math.max(Math.log10(source.getData(Registration.MANA)),1))));
            }
        }
    }
}
