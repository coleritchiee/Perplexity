package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.client.particle.ShockwaveEffect;
import net.iicosahedra.perplexity.client.particle.SpinningEffect;
import net.iicosahedra.perplexity.network.packet.ProcessDeltaMovementPacket;
import net.iicosahedra.perplexity.setup.Registration;
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

import java.util.List;
import java.util.Objects;

public class RanduinsOmenAbility extends ActiveAbility{
    public RanduinsOmenAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        if(player.getData(Registration.OMEN_COOLDOWN) == 0) {
            List<LivingEntity> list = HitScanResult.getEntitiesWithinSphere(player, 5);
            list.forEach(target -> {
                target.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.omen.ms"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                TickScheduler.schedule(40, () -> {
                    if (Objects.requireNonNull(target.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLoc.create("attribute.perplexity.omen.ms"))) {
                        target.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ResourceLoc.create("attribute.perplexity.omen.ms"));
                    }
                });
            });
            ShockwaveEffect.createShockwaveParticles(player, 5, new Vector3f(1f,0.73f,0.17f), 2f);
            player.setData(Registration.OMEN_COOLDOWN.value(), (int)(90*20* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Randuin's Omen is on cooldown for "+player.getData(Registration.OMEN_COOLDOWN)/20+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            player.setData(Registration.LICH_FLAG.value(), 0);
        }
    }
}
