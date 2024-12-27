package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Map;

public class MorellonomiconItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> morelloModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.morello.hp"), 3.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.morello.ap"), 7.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.morello.ability_haste"), 15, AttributeModifier.Operation.ADD_VALUE)
    );


    public MorellonomiconItem() {
        super(new Item.Properties().stacksTo(1), morelloModifiers, Registration.MORELLO_EFFECT, null);
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.morello"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
