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

public class RapidFirecannonItem extends EnergizeItem{
    private static final Map<Holder<Attribute>, AttributeModifier> rfcModifiers = Map.of(
            Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.rfc.as"), 0.35, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.rfc.ms"), 0.04, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Registration.CRIT_CHANCE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.rfc.crit_chance"), 25, AttributeModifier.Operation.ADD_VALUE)
    );

    public RapidFirecannonItem() {
        super(new Item.Properties().stacksTo(1), rfcModifiers, null);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.ENTITY_INTERACTION_RANGE)).hasModifier(ResourceLoc.create("attribute.perplexity.energize.eir"))){
            slotContext.entity().getAttribute(Attributes.ENTITY_INTERACTION_RANGE).removeModifier(ResourceLoc.create("attribute.perplexity.energize.eir"));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(ItemData.getStacks(stack)==119){
            slotContext.entity().getAttribute(Attributes.ENTITY_INTERACTION_RANGE)
                    .addTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.energize.eir"), 2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.rfc"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
