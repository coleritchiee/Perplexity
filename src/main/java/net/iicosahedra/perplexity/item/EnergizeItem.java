package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Map;

public class EnergizeItem extends RelicItem {
    public EnergizeItem(Properties properties, Map<Holder<Attribute>, AttributeModifier> attributeModifiers, @Nullable Holder<MobEffect> passive, @Nullable Holder<ActiveAbility> active) {
        super(properties, attributeModifiers, passive, active);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if(!slotContext.entity().level().isClientSide()) {
            slotContext.entity().removeEffect(Registration.ENERGIZE);
            slotContext.entity().setData(Registration.ENERGIZE_STACKS, 0);
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        if(!slotContext.entity().level().isClientSide()) {
            slotContext.entity().addEffect(new MobEffectInstance(Registration.ENERGIZE, -1,0,false, false));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().hasEffect(Registration.ENERGIZE)){
            slotContext.entity().addEffect(new MobEffectInstance(Registration.ENERGIZE, -1,0,false, false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.energize"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
