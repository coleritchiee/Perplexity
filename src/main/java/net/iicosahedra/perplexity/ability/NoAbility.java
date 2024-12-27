package net.iicosahedra.perplexity.ability;

import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.entity.player.Player;

public class NoAbility extends ActiveAbility{
    public NoAbility() {
        super(0);
    }

    @Override
    public void cast(Player player) {
        player.setData(Registration.LICH_FLAG.value(), 0);
    }
}
