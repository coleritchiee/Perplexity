package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CheckManaAbility extends ActiveAbility{
    public CheckManaAbility() {
        super(0);
    }

    @Override
    public void cast(Player player) {
        player.sendSystemMessage(Component.literal("Mana: " + player.getData(Registration.MANA)));
        net.iicosahedra.perplexity.util.CuriosUtil.findFirstEquipped(player, net.iicosahedra.perplexity.item.LichBaneItem.class)
                .ifPresent(s -> net.iicosahedra.perplexity.util.ItemData.setFlag(s, 0));
    }
}
