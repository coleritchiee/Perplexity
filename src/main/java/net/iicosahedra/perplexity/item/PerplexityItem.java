package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class PerplexityItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> perplexityModifiers = Map.of(
            Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.perplexity.ms"), 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.perplexity.ability_power"), 9, AttributeModifier.Operation.ADD_VALUE),
            Registration.ARMOR_PEN, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.perplexity.ap"), 22, AttributeModifier.Operation.ADD_VALUE),
            Registration.MAGIC_PEN, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.perplexity.mp"), 30, AttributeModifier.Operation.ADD_VALUE)
    );

    public PerplexityItem() {
        super(new Properties().stacksTo(1), perplexityModifiers, null);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.perplexity"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
