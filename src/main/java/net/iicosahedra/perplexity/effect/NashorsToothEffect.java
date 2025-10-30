package net.iicosahedra.perplexity.effect;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.CooldownCalc;
import net.iicosahedra.perplexity.client.particle.LineEffect;
import net.iicosahedra.perplexity.item.NashorsToothItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class NashorsToothEffect{
    @SubscribeEvent
    public static void onAttack(LivingIncomingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player source){
            if(CuriosUtil.findFirstEquipped(source, NashorsToothItem.class).isPresent() && !source.equals(event.getEntity())) {
                event.getEntity().hurt(new DamageSource(event.getEntity().level()
                        .registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MAGIC), event.getEntity(), source),
                        (float) (1.5+source.getAttributeValue(Registration.ABILITY_POWER)*0.15));
            }
        }
    }
}
