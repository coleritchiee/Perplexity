package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.item.ProwlersClawItem;
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
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ProwlersClawAbility extends ActiveAbility{
    public ProwlersClawAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        var stackOpt = CuriosUtil.findFirstEquipped(player, ProwlersClawItem.class);
        if(stackOpt.isPresent() && ItemData.getCooldown(stackOpt.get()) == 0) {
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
                ItemData.setCooldown(stackOpt.get(), (int)(1800* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
            }
            else{
                player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            }
        }
        else{
            player.displayClientMessage(Component.literal("Prowler's Claw is on cooldown for "+(ItemData.getCooldown(stackOpt.orElse(ItemStack.EMPTY))/20)+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            net.iicosahedra.perplexity.util.CuriosUtil.findFirstEquipped(player, net.iicosahedra.perplexity.item.LichBaneItem.class)
                    .ifPresent(s -> net.iicosahedra.perplexity.util.ItemData.setFlag(s, 0));
        }
    }
}
