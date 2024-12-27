package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.client.particle.ShockwaveEffect;
import net.iicosahedra.perplexity.client.particle.SpinningEffect;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.HitScanResult;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;

import java.util.List;

public class GoredrinkerAbility extends ActiveAbility{
    public GoredrinkerAbility() {
        super(80);
    }

    @Override
    public void cast(Player player) {
        if(player.getData(Registration.GOREDRINKER_COOLDOWN) == 0) {
            List<LivingEntity> list = HitScanResult.getEntitiesWithinSphere(player, 4.5);
            list.forEach(target -> {
                target.hurt(new DamageSource(target.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                .getOrThrow(DamageTypes.PLAYER_ATTACK), target, player),
                        (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.60)
                );
                player.heal((float)(0.2*(player.getAttributeValue(Attributes.ATTACK_DAMAGE) + 0.08*(player.getMaxHealth()-player.getHealth()))));
            });
            SpinningEffect.createSpinningParticles(player, 4.5, new Vector3f(1f,0.27f,0.21f), 2f);
            player.setData(Registration.GOREDRINKER_COOLDOWN.value(), (int)(15*20* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Goredrinker is on cooldown for "+player.getData(Registration.GOREDRINKER_COOLDOWN)/20+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            player.setData(Registration.LICH_FLAG.value(), 0);
        }
    }
}
