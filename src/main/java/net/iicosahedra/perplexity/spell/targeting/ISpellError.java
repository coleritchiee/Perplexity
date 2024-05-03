package net.iicosahedra.perplexity.spell.targeting;

import net.iicosahedra.perplexity.spell.component.AbstractSpellPart;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

public interface ISpellError {
    int getPosition();

    @Nullable AbstractSpellPart getSpellPart();

    MutableComponent makeTextComponent();
    MutableComponent makeTextComponentAdding();
}
