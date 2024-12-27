package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RiftmakerEffect extends MobEffect {
    public RiftmakerEffect() {
        super(MobEffectCategory.NEUTRAL, 0xb103fc);
    }

    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(source.hasEffect(Registration.RIFT_EFFECT)&&!source.equals(event.getEntity())) {
                if(source.getData(Registration.RIFT_STACKS)<8){
                    source.setData(Registration.RIFT_STACKS, source.getData(Registration.RIFT_STACKS)+1);
                }
                if(source.getData(Registration.RIFT_STACKS)==8){
                    source.setData(Registration.RIFT_COOLDOWN, 100);
                    source.getAttribute(Registration.OMNIVAMP).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.rift.omni"), 10, AttributeModifier.Operation.ADD_VALUE));
                }
                else{
                    source.setData(Registration.RIFT_COOLDOWN, 60);
                }
                event.setAmount((float) (event.getAmount()*(1+(source.getData(Registration.RIFT_STACKS)*0.01))));
            }
        }
    }
}
