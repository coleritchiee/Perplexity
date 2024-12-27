package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;

import java.util.Map;

public class VoidStaffItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> voidModifiers = Map.of(
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.void.ap"), 9.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.MAGIC_PEN, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.void.mp"), 40, AttributeModifier.Operation.ADD_VALUE)
    );

    public VoidStaffItem() {
            super(new Item.Properties().stacksTo(1), voidModifiers, null, null);
        }
}
