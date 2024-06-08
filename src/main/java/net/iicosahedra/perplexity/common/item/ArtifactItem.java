package net.iicosahedra.perplexity.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ArtifactItem extends Item implements ICurioItem {
    public ArtifactItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }
}
