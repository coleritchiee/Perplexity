package net.iicosahedra.perplexity.spell.targeting;

import net.iicosahedra.perplexity.spell.component.AbstractSpellPart;

import java.util.List;

public interface ISpellChecker {
    List<ISpellError> check(List<AbstractSpellPart> spellParts);
}
