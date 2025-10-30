package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.item.DuskbladeOfDraktharrItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DuskbladeOfDraktharrEffect {
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(CuriosUtil.findFirstEquipped(source, DuskbladeOfDraktharrItem.class).isPresent()) {
                float damage = event.getOriginalAmount();
                event.setAmount((float) (damage * (1 + 0.225*(1 - (event.getEntity().getHealth() / event.getEntity().getMaxHealth())))));
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(event.getEntity() instanceof Player && CuriosUtil.findFirstEquipped(source, DuskbladeOfDraktharrItem.class).isPresent()){
                var stack = CuriosUtil.findFirstEquipped(source, DuskbladeOfDraktharrItem.class).get();
                ItemData.setFlag(stack, 1);
                ItemData.setCooldown(stack, 30);
            }
        }
    }

    @SubscribeEvent
    public static void immuneCheck(LivingIncomingDamageEvent event) {
        if(event.getEntity() instanceof Player target){
            if(target instanceof Player player && CuriosUtil.findFirstEquipped(player, DuskbladeOfDraktharrItem.class).isPresent()) {
                var stack = CuriosUtil.findFirstEquipped(player, DuskbladeOfDraktharrItem.class).get();
                if (ItemData.getFlag(stack) == 1) {
                    event.setCanceled(true);
                }
            }
        }
    }


}
