package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.item.DeathsDanceItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DeathsDanceEffect{
    @SubscribeEvent
    public static void onHurt(LivingIncomingDamageEvent event) {
        if(event.getEntity() instanceof net.minecraft.world.entity.player.Player player && CuriosUtil.findFirstEquipped(player, DeathsDanceItem.class).isPresent()){
            LivingEntity entity = event.getEntity();
            var stack = CuriosUtil.findFirstEquipped(player, DeathsDanceItem.class).get();
            if(ItemData.getStacks(stack) < 2){
                float damage = event.getAmount();
                ItemData.setStacks(stack, ItemData.getStacks(stack)+1);
                ItemData.setStoredDamage(stack, ItemData.getStoredDamage(stack) + (int)(damage*0.3));
                event.setAmount((float)(0.7*damage));
            }
            else if(ItemData.getStacks(stack) == 2){
                float damage = event.getOriginalAmount();
                int stored = ItemData.getStoredDamage(stack);
                ItemData.setStacks(stack, 0);
                ItemData.setStoredDamage(stack, 0);
                event.setAmount((float) (damage + stored * 0.5));
            }
        }
    }
}
