package net.iicosahedra.perplexity.spell.component;

import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AbstractSpellPart implements Comparable<AbstractSpellPart> {

    public int manaCost;

    private final ResourceLocation registryName;
    private String name;

    public AbstractSpellPart(ResourceLocation registryName, String name) {
        this.registryName = registryName;
        this.name = name;
    }
    public AbstractSpellPart(String tag, String name) {
        this(ResourceLoc.create(tag), name);
    }

    public int getManaCost() {
        return manaCost;
    }

    @Override
    public int compareTo(@NotNull AbstractSpellPart o) {
        return 0;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public String getName() {
        return name;
    }
}
