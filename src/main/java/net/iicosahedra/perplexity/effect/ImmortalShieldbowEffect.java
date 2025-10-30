package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.item.ImmortalShieldbowItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ImmortalShieldbowEffect{
    @SubscribeEvent
    public static void onHurt(LivingIncomingDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(CuriosUtil.findFirstEquipped((net.minecraft.world.entity.player.Player)event.getEntity(), ImmortalShieldbowItem.class).isPresent()){
            var stack = CuriosUtil.findFirstEquipped((net.minecraft.world.entity.player.Player)event.getEntity(), ImmortalShieldbowItem.class).get();
            if (ItemData.getCooldown(stack) == 0) {
                LivingEntity entity = event.getEntity();
                if (entity instanceof Player player) {
                    if (player.getHealth() - event.getAmount() < 0.3 * player.getMaxHealth()) {
                        float shieldAmount = 40f - event.getAmount();
                        player.getAttribute(Attributes.MAX_ABSORPTION).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.lifeline.am"), shieldAmount, AttributeModifier.Operation.ADD_VALUE));
                        player.setAbsorptionAmount(player.getAbsorptionAmount() + shieldAmount);
                        TickScheduler.schedule(100, () -> {
                            player.setAbsorptionAmount(player.getAbsorptionAmount() - shieldAmount);
                            if (Objects.requireNonNull(player.getAttribute(Attributes.MAX_ABSORPTION)).hasModifier(ResourceLoc.create("attribute.perplexity.lifeline.am"))) {
                                player.getAttribute(Attributes.MAX_ABSORPTION).removeModifier(ResourceLoc.create("attribute.perplexity.lifeline.am"));
                            }
                        });
                        event.setCanceled(true);
                        ItemData.setCooldown(stack, (int)(1200f*CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
                    }
                }
            }
        }
    }
}
