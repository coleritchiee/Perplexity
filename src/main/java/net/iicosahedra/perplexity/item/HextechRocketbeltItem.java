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

public class HextechRocketbeltItem extends RelicItem {
    private static final Map<Holder<Attribute>, AttributeModifier> rocketbeltModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.rocketbelt.hp"), 3.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.rocketbelt.ability_power"), 6, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.rocketbelt.ability_haste"), 15, AttributeModifier.Operation.ADD_VALUE)
    );
    public HextechRocketbeltItem() {
        super(new Item.Properties().stacksTo(1), rocketbeltModifiers, null, Registration.ROCKETBELT_ABILITY);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (slotContext.entity().getData(Registration.ROCKETBELT_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.ROCKETBELT_COOLDOWN,
                        slotContext.entity().getData(Registration.ROCKETBELT_COOLDOWN.value()) - 1);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.rocketbelt"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
