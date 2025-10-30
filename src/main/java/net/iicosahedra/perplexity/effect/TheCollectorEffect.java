package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.item.TheCollectorItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TheCollectorEffect {
    @SubscribeEvent
    public static void onAttack(LivingDamageEvent.Pre event) {
        if(event.getSource().getEntity() instanceof Player){
            Player source = (Player)event.getSource().getEntity();
            if(CuriosUtil.findFirstEquipped(source, TheCollectorItem.class).isPresent() && !source.equals(event.getEntity())) {
                if(event.getEntity().getHealth()-event.getNewDamage() <= 0.05f*event.getEntity().getMaxHealth()){
                    event.setNewDamage(event.getEntity().getMaxHealth()*10f);
                }
            }
        }
    }
}
