package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.item.TwilightsEdgeItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TwilightsEdgeEffect {
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player player) {
            if(CuriosUtil.findFirstEquipped(player, TwilightsEdgeItem.class).isPresent()){
                var stack = CuriosUtil.findFirstEquipped(player, TwilightsEdgeItem.class).get();
                ItemData.setCooldown(stack, 100);
            }
        }
    }
}
