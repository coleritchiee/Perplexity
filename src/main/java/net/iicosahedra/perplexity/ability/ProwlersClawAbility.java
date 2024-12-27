package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.HitScanResult;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ProwlersClawAbility extends ActiveAbility{
    public ProwlersClawAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        if(player.getData(Registration.PROWLERS_COOLDOWN) == 0) {
            EntityHitResult result = HitScanResult.getPlayerPOVHitResult(player, 32);
            if (result != null && result.getEntity() instanceof LivingEntity target) {
                Vec3 targetPosition = target.position();
                Vec3 targetLookDirection = target.getLookAngle().normalize();
                double dashDistance = 2.0;
                Vec3 behindTargetPosition = targetPosition.subtract(targetLookDirection.scale(dashDistance));
                player.teleportTo(behindTargetPosition.x, behindTargetPosition.y, behindTargetPosition.z);
                target.hurt(new DamageSource(target.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                        .getOrThrow(DamageTypes.PLAYER_ATTACK), target, player),
                        (float)(10+player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.3)
                        );
                player.setData(Registration.PROWLERS_COOLDOWN.value(), (int)(1800* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
            }
            else{
                player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            }
        }
        else{
            player.displayClientMessage(Component.literal("Prowler's Claw is on cooldown for "+player.getData(Registration.PROWLERS_COOLDOWN)/20+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            player.setData(Registration.LICH_FLAG.value(), 0);
        }
    }
}
