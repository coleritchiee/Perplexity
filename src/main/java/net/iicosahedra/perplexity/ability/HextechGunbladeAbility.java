package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.client.particle.LineEffect;
import net.iicosahedra.perplexity.item.HextechGunbladeItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.HitScanResult;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;

import java.util.Objects;

public class HextechGunbladeAbility extends ActiveAbility {
    public HextechGunbladeAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        var stackOpt = CuriosUtil.findFirstEquipped(player, HextechGunbladeItem.class);
        if (stackOpt.isPresent() && ItemData.getCooldown(stackOpt.get()) == 0) {
            EntityHitResult result = HitScanResult.getPlayerPOVHitResult(player, player.entityInteractionRange() + 6);
            if (result != null && result.getEntity() instanceof LivingEntity entity) {
                entity.hurt(new DamageSource(entity.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                .getOrThrow(DamageTypes.MAGIC), entity, player),
                        (float) (15 + player.getAttributeValue(Registration.ABILITY_POWER) * 0.3 + player.getAttributeValue(Attributes.ATTACK_DAMAGE)));
                entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.gunblade.ms"), -0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                TickScheduler.schedule(40, () -> {
                    if (Objects.requireNonNull(entity.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLoc.create("attribute.perplexity.gunblade.ms"))) {
                        entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ResourceLoc.create("attribute.perplexity.gunblade.ms"));
                        }
                });
                LineEffect.createLineParticles(player, entity, new Vector3f(0.1411764f, 1f, 0.51372549f), 1f, 5);
                ItemData.setCooldown(stackOpt.get(), (int)(1200* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
            }
            else{
                player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            }
        }
        else{
            player.displayClientMessage(Component.literal("Hextech Gunblade is on cooldown for "+(ItemData.getCooldown(stackOpt.orElse(ItemStack.EMPTY))/20)+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            net.iicosahedra.perplexity.util.CuriosUtil.findFirstEquipped(player, net.iicosahedra.perplexity.item.LichBaneItem.class)
                    .ifPresent(s -> net.iicosahedra.perplexity.util.ItemData.setFlag(s, 0));
        }
    }
}
