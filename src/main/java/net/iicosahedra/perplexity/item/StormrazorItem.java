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

public class StormrazorItem extends EnergizeItem{
    private static final Map<Holder<Attribute>, AttributeModifier> stormrazorModifiers = Map.of(
            Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.sr.as"), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.sr.ad"), 4.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.CRIT_CHANCE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.sr.crit_chance"), 25, AttributeModifier.Operation.ADD_VALUE)
    );

    public StormrazorItem() {
        super(new Item.Properties().stacksTo(1), stormrazorModifiers, Registration.STORMRAZOR_EFFECT, null);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.stormrazor"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
