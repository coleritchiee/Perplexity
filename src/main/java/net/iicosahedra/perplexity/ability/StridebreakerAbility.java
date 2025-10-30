package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.client.particle.SpinningEffect;
import net.iicosahedra.perplexity.network.packet.ProcessDeltaMovementPacket;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.item.StridebreakerItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.util.HitScanResult;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

import java.util.List;
import java.util.Objects;

public class StridebreakerAbility extends ActiveAbility{
    public StridebreakerAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        var stackOpt = CuriosUtil.findFirstEquipped(player, StridebreakerItem.class);
        if(stackOpt.isPresent() && ItemData.getCooldown(stackOpt.get()) == 0) {
            Vec3 vec3 = player.getLookAngle();
            if(player instanceof ServerPlayer p) {
                PacketDistributor.sendToPlayer(p, new ProcessDeltaMovementPacket(vec3.scale(1.2)));
            }
            List<LivingEntity> list = HitScanResult.getEntitiesWithinSphere(player, 4.5);
            list.forEach(target -> {
                target.hurt(new DamageSource(target.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                .getOrThrow(DamageTypes.PLAYER_ATTACK), target, player),
                        (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.75)
                );
                target.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.stridebreaker.ms"), -0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                TickScheduler.schedule(40, () -> {
                    if (Objects.requireNonNull(target.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLoc.create("attribute.perplexity.stridebreaker.ms"))) {
                        target.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ResourceLoc.create("attribute.perplexity.stridebreaker.ms"));
                    }
                });
            });
            SpinningEffect.createSpinningParticles(player, 4.5, new Vector3f(0.27f,0.73f,1f), 2f);
            ItemData.setCooldown(stackOpt.get(), (int)(20*20* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Stridebreaker is on cooldown for "+(ItemData.getCooldown(stackOpt.orElse(ItemStack.EMPTY))/20)+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            net.iicosahedra.perplexity.util.CuriosUtil.findFirstEquipped(player, net.iicosahedra.perplexity.item.LichBaneItem.class)
                    .ifPresent(s -> net.iicosahedra.perplexity.util.ItemData.setFlag(s, 0));
        }
    }
}
