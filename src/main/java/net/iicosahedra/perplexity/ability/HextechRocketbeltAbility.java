package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.entity.RocketbeltProjectileEntity;
import net.iicosahedra.perplexity.network.packet.ProcessDeltaMovementPacket;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

public class HextechRocketbeltAbility extends ActiveAbility{

    public HextechRocketbeltAbility(){
        super(50);
    }

    @Override
    public void cast(Player player) {
        if (player.getData(Registration.ROCKETBELT_COOLDOWN) == 0) {
            Vec3 vec3 = player.getLookAngle();
            if(player instanceof ServerPlayer p) {
                PacketDistributor.sendToPlayer(p, new ProcessDeltaMovementPacket(vec3));
            }
            int numberOfProjectiles = 7;
            float spreadAngle = 60.0F;
            float angleIncrement = spreadAngle / (numberOfProjectiles - 1);
            float startAngle = -spreadAngle / 2.0F;

            for (int i = 0; i < numberOfProjectiles; i++) {
                float yawOffset = startAngle + (i * angleIncrement);
                RocketbeltProjectileEntity proj = new RocketbeltProjectileEntity(player.level(), player);
                proj.shoot(player, player.getXRot(), player.getYRot() + yawOffset, 0.0F, 1.0F, 0.8F);
                player.level().addFreshEntity(proj);
            }
            player.setData(Registration.ROCKETBELT_COOLDOWN.value(), (int)(800* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Hextech Rocketbelt is on cooldown for "+player.getData(Registration.ROCKETBELT_COOLDOWN)/20+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            player.setData(Registration.LICH_FLAG.value(), 0);
        }
    }
}
