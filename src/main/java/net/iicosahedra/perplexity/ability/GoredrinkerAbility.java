package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.client.particle.ShockwaveEffect;
import net.iicosahedra.perplexity.client.particle.SpinningEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.item.GoredrinkerItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.util.HitScanResult;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

import java.util.List;

public class GoredrinkerAbility extends ActiveAbility{
    public GoredrinkerAbility() {
        super(80);
    }

    @Override
    public void cast(Player player) {
        var stackOpt = CuriosUtil.findFirstEquipped(player, GoredrinkerItem.class);
        if(stackOpt.isPresent() && ItemData.getCooldown(stackOpt.get()) == 0) {
            List<LivingEntity> list = HitScanResult.getEntitiesWithinSphere(player, 4.5);
            list.forEach(target -> {
                target.hurt(new DamageSource(target.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                .getOrThrow(DamageTypes.PLAYER_ATTACK), target, player),
                        (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.60)
                );
                player.heal((float)(0.2*(player.getAttributeValue(Attributes.ATTACK_DAMAGE) + 0.08*(player.getMaxHealth()-player.getHealth()))));
            });
            SpinningEffect.createSpinningParticles(player, 4.5, new Vector3f(1f,0.27f,0.21f), 2f);
            ItemData.setCooldown(stackOpt.get(), (int)(15*20* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Goredrinker is on cooldown for "+(ItemData.getCooldown(stackOpt.orElse(ItemStack.EMPTY))/20)+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            net.iicosahedra.perplexity.util.CuriosUtil.findFirstEquipped(player, net.iicosahedra.perplexity.item.LichBaneItem.class)
                    .ifPresent(s -> net.iicosahedra.perplexity.util.ItemData.setFlag(s, 0));
        }
    }
}
