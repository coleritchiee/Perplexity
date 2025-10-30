package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.item.KrakenSlayerItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class KrakenSlayerEffect{
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(CuriosUtil.findFirstEquipped(source, KrakenSlayerItem.class).isPresent()) {
                var stack = CuriosUtil.findFirstEquipped(source, KrakenSlayerItem.class).get();
                if (ItemData.getStacks(stack) < 2) {
                    ItemData.setStacks(stack, ItemData.getStacks(stack) + 1);
                } else if (ItemData.getStacks(stack) == 2) {
                    float damage = event.getOriginalAmount();
                    ItemData.setStacks(stack, 0);
                    event.setAmount((float) (damage + 4.0 * (1 + (1 - (event.getEntity().getHealth() / event.getEntity().getMaxHealth())))));
                }
            }
        }
    }
}
