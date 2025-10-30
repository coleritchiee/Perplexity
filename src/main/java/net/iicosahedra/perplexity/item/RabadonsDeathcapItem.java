package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
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

public class RabadonsDeathcapItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> rabadonsModifiers = Map.of(
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.rabadons.ap"), 13, AttributeModifier.Operation.ADD_VALUE)
    );


    public RabadonsDeathcapItem() {
        super(new Item.Properties().stacksTo(1), rabadonsModifiers, null);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            slotContext.entity().getAttribute(Registration.ABILITY_POWER).addOrUpdateTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.opus.ap"), 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if(Objects.requireNonNull(slotContext.entity().getAttribute(Registration.ABILITY_POWER)).hasModifier(ResourceLoc.create("attribute.perplexity.opus.ap"))){
            slotContext.entity().getAttribute(Registration.ABILITY_POWER).removeModifier(ResourceLoc.create("attribute.perplexity.opus.ap"));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.rabadons"));
        tooltipComponents.add(Component.translatable("tooltip,perplexity.raba_desc"));


        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
