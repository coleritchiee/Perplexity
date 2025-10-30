package net.iicosahedra.perplexity.util;

import net.iicosahedra.perplexity.item.ItemAnvilItem;
import net.iicosahedra.perplexity.item.RelicItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public final class AnvilPool {
    private AnvilPool() {}

    public static List<ResourceLocation> getRelicItemIds() {
        List<ResourceLocation> list = new ArrayList<>();
        for (ResourceLocation id : BuiltInRegistries.ITEM.keySet()) {
            Item item = BuiltInRegistries.ITEM.get(id);
            if (item instanceof RelicItem && !(item instanceof ItemAnvilItem)) {
                list.add(id);
            }
        }
        return list;
    }
}


