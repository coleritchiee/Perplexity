package net.iicosahedra.perplexity.spell.components;

import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractModifier implements ISpellComponent{
    private int manaCost;

    private final ResourceLocation registryName;
    private String name;
    public AbstractModifier(ResourceLocation registryName, String name) {
        this.registryName = registryName;
        this.name = name;
    }
    public AbstractModifier(String tag, String name) {
        this(ResourceLoc.create(tag), name);
    }
    public ResourceLocation getRegistryName() {
        return registryName;
    }
}
