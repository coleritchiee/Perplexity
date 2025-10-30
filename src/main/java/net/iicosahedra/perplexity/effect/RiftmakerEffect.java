package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.item.RiftmakerItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RiftmakerEffect{
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(CuriosUtil.findFirstEquipped(source, RiftmakerItem.class).isPresent() && !source.equals(event.getEntity())) {
                var stack = CuriosUtil.findFirstEquipped(source, RiftmakerItem.class).get();
                if(ItemData.getStacks(stack)<8){
                    ItemData.setStacks(stack, ItemData.getStacks(stack)+1);
                    if(source.getAttribute(Registration.OMNIVAMP).hasModifier(ResourceLoc.create("attribute.perplexity.rift.omni"))){
                        source.getAttribute(Registration.OMNIVAMP).removeModifier(ResourceLoc.create("attribute.perplexity.rift.omni"));
                    }
                }
                if(ItemData.getStacks(stack)==8){
                    ItemData.setCooldown(stack, 100);
                    source.getAttribute(Registration.OMNIVAMP).addOrUpdateTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.rift.omni"), 10, AttributeModifier.Operation.ADD_VALUE));
                }
                else{
                    ItemData.setCooldown(stack, 60);
                }
                event.setAmount((float) (event.getAmount()*(1+(ItemData.getStacks(stack)*0.01))));
            }
        }
    }
}
