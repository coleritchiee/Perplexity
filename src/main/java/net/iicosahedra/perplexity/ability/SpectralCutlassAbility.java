package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SpectralCutlassAbility extends ActiveAbility{
    public SpectralCutlassAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        if (player.getData(Registration.SPECTRAL_COOLDOWN) == 0) {
            player.displayClientMessage(Component.literal("Soul Anchor set"), true);
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            TickScheduler.schedule(200, ()->{
                player.teleportTo(x,y,z);
            });
            player.setData(Registration.SPECTRAL_COOLDOWN.value(), (int)(15*20* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Spectral Cutlass is on cooldown for "+player.getData(Registration.SPECTRAL_COOLDOWN)/20+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            player.setData(Registration.LICH_FLAG.value(), 0);
        }
    }
}
