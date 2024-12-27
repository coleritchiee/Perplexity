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
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Map;

public class GargoyleStoneplateItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> gargoyleModifiers = Map.of(
            Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.gargoyle.ms"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Attributes.ARMOR, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.gargoyle.armor"), 6.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.MAGIC_RESIST, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.gargoyle.mr"), 6.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.gargoyle.ah"), 15, AttributeModifier.Operation.ADD_VALUE)
            );
    public GargoyleStoneplateItem() {
        super(new Item.Properties().stacksTo(1), gargoyleModifiers, null, Registration.GARGOYLE_ABILITY);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (slotContext.entity().getData(Registration.GARGOYLE_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.GARGOYLE_COOLDOWN,
                        slotContext.entity().getData(Registration.GARGOYLE_COOLDOWN.value()) - 1);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.gargoyle"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
