package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.ItemData;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Map;

public class StridebreakerItem extends RelicItem {
    private static final Map<Holder<Attribute>, AttributeModifier> strideModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.stridebreaker.hp"), 3, AttributeModifier.Operation.ADD_VALUE),
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.stridebreaker.ad"), 5, AttributeModifier.Operation.ADD_VALUE),
            Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.stridebreaker.as"), 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.stridebreaker.ability_haste"), 10, AttributeModifier.Operation.ADD_VALUE)
    );
    public StridebreakerItem() {
        super(new Item.Properties().stacksTo(1), strideModifiers, Registration.STRIDEBREAKER_ABILITY);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (ItemData.getCooldown(stack) > 0) {
                ItemData.setCooldown(stack, ItemData.getCooldown(stack) - 1);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.stridebreaker"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
