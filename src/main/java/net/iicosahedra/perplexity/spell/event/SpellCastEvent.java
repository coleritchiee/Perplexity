package net.iicosahedra.perplexity.spell.event;

import net.iicosahedra.perplexity.spell.SpellContext;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class SpellCastEvent extends Event implements ICancellableEvent {
    private SpellContext context;

    public SpellCastEvent(SpellContext context) {
        this.context = context;
    }

    public SpellContext getContext() {
        return context;
    }
}
