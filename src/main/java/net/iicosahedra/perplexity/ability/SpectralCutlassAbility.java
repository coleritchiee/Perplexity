package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.item.SpectralCutlassItem;
import net.iicosahedra.perplexity.util.CuriosUtil;
import net.iicosahedra.perplexity.util.ItemData;
import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SpectralCutlassAbility extends ActiveAbility{
    public SpectralCutlassAbility() {
        super(50);
    }

    @Override
    public void cast(Player player) {
        var stackOpt = CuriosUtil.findFirstEquipped(player, SpectralCutlassItem.class);
        if (stackOpt.isPresent() && ItemData.getCooldown(stackOpt.get()) == 0) {
            player.displayClientMessage(Component.literal("Soul Anchor set"), true);
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            TickScheduler.schedule(200, ()->{
                player.teleportTo(x,y,z);
            });
            ItemData.setCooldown(stackOpt.get(), (int)(15*20* CooldownCalc.cooldownReduction(player.getAttribute(Registration.ABILITY_HASTE).getValue())));
        }
        else{
            player.displayClientMessage(Component.literal("Spectral Cutlass is on cooldown for "+(ItemData.getCooldown(stackOpt.orElse(ItemStack.EMPTY))/20)+" more seconds."), true);
            player.setData(Registration.MANA, player.getData(Registration.MANA)+manaCost);
            net.iicosahedra.perplexity.util.CuriosUtil.findFirstEquipped(player, net.iicosahedra.perplexity.item.LichBaneItem.class)
                    .ifPresent(s -> net.iicosahedra.perplexity.util.ItemData.setFlag(s, 0));
        }
    }
}
