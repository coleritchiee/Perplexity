package net.iicosahedra.perplexity.ritual;

import net.minecraft.resources.ResourceLocation;

public abstract class AbstractRitual {
    ResourceLocation name;

    public AbstractRitual(ResourceLocation name){
        this.name = name;
    }
}
