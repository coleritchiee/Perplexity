package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.client.Minecraft;
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

public class DeathsDanceItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> deathsDanceModifiers = Map.of(
            Attributes.ARMOR, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.dd.armor"), 5, AttributeModifier.Operation.ADD_VALUE),
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.dd.ad"), 6, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.dd.ability_haste"), 15, AttributeModifier.Operation.ADD_VALUE)
            );

    public DeathsDanceItem() {
        super(new Item.Properties().stacksTo(1), deathsDanceModifiers, null);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.deathsdance"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
