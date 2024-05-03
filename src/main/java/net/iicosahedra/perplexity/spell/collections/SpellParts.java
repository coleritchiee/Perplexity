package net.iicosahedra.perplexity.spell.collections;

import net.iicosahedra.perplexity.spell.component.AbstractSpellPart;
import net.iicosahedra.perplexity.spell.effect.EffectBreak;
import net.iicosahedra.perplexity.spell.effect.EffectDamage;
import net.iicosahedra.perplexity.spell.shapes.ShapeProj;
import net.iicosahedra.perplexity.spell.shapes.ShapeSelf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class SpellParts {
    public static final Map<ResourceLocation, AbstractSpellPart> SPELL_PARTS = new HashMap<>();

    public static void register(){
        SPELL_PARTS.put(ShapeSelf.INSTANCE.getRegistryName(), ShapeSelf.INSTANCE);
        SPELL_PARTS.put(EffectDamage.INSTANCE.getRegistryName(), EffectDamage.INSTANCE);
        SPELL_PARTS.put(ShapeSelf.INSTANCE.getRegistryName(), ShapeSelf.INSTANCE);
        SPELL_PARTS.put(EffectBreak.INSTANCE.getRegistryName(), EffectBreak.INSTANCE);
        SPELL_PARTS.put(ShapeProj.INSTANCE.getRegistryName(), ShapeProj.INSTANCE);
    }
}
