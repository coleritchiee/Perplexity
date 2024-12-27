package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DuskbladeOfDraktharrEffect extends MobEffect {
    public DuskbladeOfDraktharrEffect() {
        super(MobEffectCategory.NEUTRAL, 0x6b0000);
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(source.hasEffect(Registration.DUSKBLADE_EFFECT)) {
                float damage = event.getOriginalAmount();
                event.setAmount((float) (damage * (1 + 0.225*(1 - (event.getEntity().getHealth() / event.getEntity().getMaxHealth())))));
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(event.getEntity() instanceof Player && source.hasEffect(Registration.DUSKBLADE_EFFECT)){
                source.setData(Registration.DUSKBLADE_IMMUNITY, 1);
                source.setData(Registration.DUSKBLADE_COOLDOWN, 30);
            }
        }
    }

    @SubscribeEvent
    public static void immuneCheck(LivingIncomingDamageEvent event) {
        if(event.getEntity() instanceof Player target){
            if(target.hasEffect(Registration.DUSKBLADE_EFFECT)&&target.getData(Registration.DUSKBLADE_IMMUNITY)==1) {
                event.setCanceled(true);
            }
        }
    }


}
