package net.iicosahedra.perplexity.spell.components;

import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractModifier implements ISpellComponent{
    private int manaCost;

    private int tier;

    private final ResourceLocation registryName;
    private String name;
    public AbstractModifier(ResourceLocation registryName, String name) {
        this.registryName = registryName;
        this.name = name;
    }
    public AbstractModifier(String tag, String name, int tier, int manaCost) {
        this(ResourceLoc.create(tag), name);
        this.tier = tier;
        this.manaCost = manaCost;
    }
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getManaCost() {
        return manaCost;
    }
}
