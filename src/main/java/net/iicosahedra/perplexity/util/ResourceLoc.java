package net.iicosahedra.perplexity.util;

import net.iicosahedra.perplexity.Perplexity;
import net.minecraft.resources.ResourceLocation;

public class ResourceLoc {
    public static ResourceLocation create(String path){
        return ResourceLocation.fromNamespaceAndPath(Perplexity.MODID, path);
    }
}
