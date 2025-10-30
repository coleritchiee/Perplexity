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

public class GoredrinkerItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> goreModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.goredrinker.hp"), 4, AttributeModifier.Operation.ADD_VALUE),
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.goredrinker.ad"), 5.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.OMNIVAMP, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.goredrinker.omnivamp"), 8, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.goredrinker.ability_haste"), 20, AttributeModifier.Operation.ADD_VALUE)
    );
    public GoredrinkerItem() {
        super(new Item.Properties().stacksTo(1), goreModifiers, Registration.GOREDRINKER_ABILITY);
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
        tooltipComponents.add(Component.translatable("tooltip.perplexity.goredrinker"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
