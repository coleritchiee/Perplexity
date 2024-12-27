package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.entity.RocketbeltProjectileEntity;
import net.iicosahedra.perplexity.network.packet.ProcessDeltaMovementPacket;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Objects;

public class YoumuusGhostbladeAbility extends ActiveAbility{
    public YoumuusGhostbladeAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        if (player.getData(Registration.YOUMUUS_COOLDOWN) == 0) {
            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.youmuus.ms"), 2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            TickScheduler.schedule(120, ()->{
                if(Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLoc.create("attribute.perplexity.youmuus.ms"))){
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ResourceLoc.create("attribute.perplexity.youmuus.ms"));
                }
            });
            player.setData(Registration.YOUMUUS_COOLDOWN.value(), (int)(900* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Youmuu's Ghostblade is on cooldown for "+player.getData(Registration.YOUMUUS_COOLDOWN)/20+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            player.setData(Registration.LICH_FLAG.value(), 0);
        }
    }
}
