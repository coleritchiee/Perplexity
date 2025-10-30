package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.client.particle.LineEffect;
import net.iicosahedra.perplexity.item.DivineSundererItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DivineSundererEffect{
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(CuriosUtil.findFirstEquipped(source, DivineSundererItem.class).isPresent() && !source.equals(event.getEntity())) {
                var stack = CuriosUtil.findFirstEquipped(source, DivineSundererItem.class).get();
                if (ItemData.getCooldown(stack) == 0) {
                    float damage = event.getOriginalAmount()
                            + (float)(1.6* Objects.requireNonNull(source.getAttribute(Attributes.ATTACK_DAMAGE)).getValue())
                            + (float)(event.getEntity().getMaxHealth()*0.04);
                    event.setAmount(damage);
                    source.heal((float)(0.64* Objects.requireNonNull(source.getAttribute(Attributes.ATTACK_DAMAGE)).getValue()
                            +(float)(event.getEntity().getMaxHealth()*0.016)));
                    LineEffect.createLineParticles(source, event.getEntity(), 0x0, 1, 5);
                    ItemData.setCooldown(stack, (int)(200* CooldownCalc.cooldownReduction(source.getAttribute(Registration.ABILITY_HASTE).getValue())));
                }
            }
        }
    }
}
