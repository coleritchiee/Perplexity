package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.network.packet.ProcessFlightMovementPacket;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class TwilightsEdgeAbility extends ActiveAbility {
    public TwilightsEdgeAbility() {
        super(1);
    }

    @Override
    public void cast(Player player) {
        if (player.getData(Registration.TWILIGHTS_COOLDOWN) == 0) {
            Vec3 vec3 = player.getLookAngle().scale(1.5);
            if(player instanceof ServerPlayer p) {
                PacketDistributor.sendToPlayer(p, new ProcessFlightMovementPacket(vec3));
            }
        }
        else{
            player.displayClientMessage(Component.literal("This ability cannot be used in combat."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
        }
    }
}
