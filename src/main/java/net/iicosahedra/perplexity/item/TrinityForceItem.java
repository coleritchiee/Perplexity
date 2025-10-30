package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.iicosahedra.perplexity.util.ItemData;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Map;

public class TrinityForceItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> triForceModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.triforce.hp"), 4, AttributeModifier.Operation.ADD_VALUE),
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.triforce.ad"), 3.6, AttributeModifier.Operation.ADD_VALUE),
            Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.triforce.as"), 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.triforce.ability_haste"), 15, AttributeModifier.Operation.ADD_VALUE)
            );


    public TrinityForceItem() {
        super(new Item.Properties().stacksTo(1), triForceModifiers, null);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.triforce"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
