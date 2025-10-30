package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.item.EssenceReaverItem;
import net.iicosahedra.perplexity.item.SeraphsEmbraceItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EssenceReaverEffect{
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(CuriosUtil.findFirstEquipped(source, SeraphsEmbraceItem.class).isPresent()){
                int amount = source.getData(Registration.MANA) + (int)(event.getEntity().getMaxHealth()*(1/Math.max(Math.log10(source.getData(Registration.MANA)),1)));
                if (amount < 1000) {
                    source.setData(Registration.MANA, amount);
                }
                else{
                    source.setData(Registration.MANA, 1000);
                }
            }
        }
    }
}
