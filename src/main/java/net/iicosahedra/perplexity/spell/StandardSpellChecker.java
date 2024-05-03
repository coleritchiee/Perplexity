package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.spell.component.AbstractSpellPart;
import net.iicosahedra.perplexity.spell.targeting.ISpellChecker;
import net.iicosahedra.perplexity.spell.targeting.ISpellError;

import java.util.List;

public class StandardSpellChecker implements ISpellChecker {
    @Override
    public List<ISpellError> check(List<AbstractSpellPart> spellParts) {
        return null;
    }
}
