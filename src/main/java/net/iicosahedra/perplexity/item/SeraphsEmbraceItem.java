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
import java.util.Objects;

public class SeraphsEmbraceItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> seraphsModifiers = Map.of(
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.seraphs.ap"), 10, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.seraphs.ability_haste"), 25, AttributeModifier.Operation.ADD_VALUE)
    );

    public SeraphsEmbraceItem() {
        super(new Item.Properties().stacksTo(1), seraphsModifiers, Registration.CHECK_MANA_ABILITY);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        slotContext.entity().getAttribute(Registration.ABILITY_POWER).addOrUpdateTransientModifier(
                new AttributeModifier(ResourceLoc.create("attributes.perplexity.seraphsconv"), 0.02 *slotContext.entity().getData(Registration.MANA), AttributeModifier.Operation.ADD_VALUE));

    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if(Objects.requireNonNull(slotContext.entity().getAttribute(Registration.ABILITY_POWER)).hasModifier(ResourceLoc.create("attributes.perplexity.seraphsconv"))){
            slotContext.entity().getAttribute(Registration.ABILITY_POWER).removeModifier(ResourceLoc.create("attributes.perplexity.seraphsconv"));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (ItemData.getCooldown(stack) > 0) {
                ItemData.setCooldown(stack, ItemData.getCooldown(stack) - 1);
            }

            if(slotContext.entity().getAttribute(Registration.ABILITY_POWER).hasModifier(ResourceLoc.create("attributes.perplexity.seraphsconv"))){
                if(slotContext.entity().getAttribute(Registration.ABILITY_POWER).getModifier(ResourceLoc.create("attributes.perplexity.seraphsconv")).amount() != 0.02 * slotContext.entity().getData(Registration.MANA)){
                    slotContext.entity().getAttribute(Registration.ABILITY_POWER).addOrUpdateTransientModifier(
                            new AttributeModifier(ResourceLoc.create("attributes.perplexity.seraphsconv"), 0.02 * slotContext.entity().getData(Registration.MANA), AttributeModifier.Operation.ADD_VALUE));
                }
            }
            else{
                slotContext.entity().getAttribute(Registration.ABILITY_POWER).addOrUpdateTransientModifier(
                        new AttributeModifier(ResourceLoc.create("attributes.perplexity.seraphsconv"), 0.02 *slotContext.entity().getData(Registration.MANA), AttributeModifier.Operation.ADD_VALUE));

            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.seraphs_lifeline"));
        tooltipComponents.add(Component.translatable("tooltip.perplexity.seraphs_awe"));
        tooltipComponents.add(Component.translatable("tooltip.perplexity.essence_reaver"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
