package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.item.RanduinsOmenItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RanduinsOmenEffect{
    @SubscribeEvent
    public static void critDamage(CriticalHitEvent event) {
        if(event.getTarget() instanceof Player target){
            if(target instanceof net.minecraft.world.entity.player.Player player && CuriosUtil.findFirstEquipped(player, RanduinsOmenItem.class).isPresent()) {
                event.setDamageMultiplier(event.getDamageMultiplier()-0.3f);
            }
        }
    }
}
