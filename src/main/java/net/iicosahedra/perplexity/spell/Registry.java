package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.spell.components.ISpellComponent;
import net.iicosahedra.perplexity.spell.effects.EffectBreak;
import net.iicosahedra.perplexity.spell.shapes.ShapeTouch;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class Registry {
    public static final Map<ResourceLocation, ISpellComponent> SPELL_PARTS = new HashMap<>();

    public static void register(){
        SPELL_PARTS.put(ShapeTouch.INSTANCE.getRegistryName(), ShapeTouch.INSTANCE);
        SPELL_PARTS.put(EffectBreak.INSTANCE.getRegistryName(), EffectBreak.INSTANCE);
    }
}
