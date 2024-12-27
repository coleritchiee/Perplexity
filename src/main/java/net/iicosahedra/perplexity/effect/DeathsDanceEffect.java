package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DeathsDanceEffect extends MobEffect {
    public DeathsDanceEffect() {
        super(MobEffectCategory.NEUTRAL, 0xf55353);
    }

    @SubscribeEvent
    public static void onHurt(LivingIncomingDamageEvent event) {
        if(event.getEntity().hasEffect(Registration.DEATHS_DANCE_EFFECT)){
            LivingEntity entity = event.getEntity();
            if(entity.getData(Registration.DEATHS_DANCE_STACKS.value())<2){
                float damage = event.getAmount();
                entity.setData(Registration.DEATHS_DANCE_STACKS.value(), entity.getData(Registration.DEATHS_DANCE_STACKS.value())+1);
                entity.setData(Registration.DEATHS_DANCE_STORED_DAMAGE.value(), entity.getData(Registration.DEATHS_DANCE_STORED_DAMAGE.value())+(int)(damage*0.3));
                event.setAmount((float)(0.7*damage));
            }
            else if(entity.getData(Registration.DEATHS_DANCE_STACKS.value())==2){
                float damage = event.getOriginalAmount();
                entity.setData(Registration.DEATHS_DANCE_STACKS.value(), 0);
                entity.setData(Registration.DEATHS_DANCE_STORED_DAMAGE.value(), 0);
                event.setAmount((float) (damage + entity.getData(Registration.DEATHS_DANCE_STORED_DAMAGE.value()) * 0.5));
            }
        }
    }
}
