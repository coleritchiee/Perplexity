package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.item.WarmogsArmorItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class WarmogsArmorEffect{
    @SubscribeEvent
    public static void onHurt(LivingIncomingDamageEvent event) {
        if(event.getEntity() instanceof net.minecraft.world.entity.player.Player player
                && CuriosUtil.findFirstEquipped(player, WarmogsArmorItem.class).isPresent()){
            var stack = CuriosUtil.findFirstEquipped(player, WarmogsArmorItem.class).get();
            LivingEntity entity = event.getEntity();
            ItemData.setCooldown(stack, (int)(120* CooldownCalc.cooldownReduction(entity.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
    }
}
