package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class GrievousWoundsEffect extends MobEffect {
    public GrievousWoundsEffect() {
        super(MobEffectCategory.NEUTRAL, 0x18ff03);
    }

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event){
        if(event.getEntity().hasEffect(Registration.GRIEVOUS_WOUNDS_EFFECT)){
            event.setAmount((float) (event.getAmount()*0.4));
        }
    }

}
