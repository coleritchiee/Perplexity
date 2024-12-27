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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Map;

public class SeraphsEmbraceItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> seraphsModifiers = Map.of(
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.seraphs.ap"), 10, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.seraphs.ability_haste"), 25, AttributeModifier.Operation.ADD_VALUE)
    );

    public SeraphsEmbraceItem() {
        super(new Item.Properties().stacksTo(1), seraphsModifiers, Registration.SERAPHS_EFFECT, null);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (slotContext.entity().getData(Registration.SERAPHS_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.SERAPHS_COOLDOWN,
                        slotContext.entity().getData(Registration.SERAPHS_COOLDOWN) - 1);
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
