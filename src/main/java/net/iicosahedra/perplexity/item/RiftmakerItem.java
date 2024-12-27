package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
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

public class RiftmakerItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> riftModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.riftmaker.hp"), 3.5, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.riftmaker.ability_power"), 7, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.riftmaker.ability_haste"), 15, AttributeModifier.Operation.ADD_VALUE)
    );
    public RiftmakerItem() {
        super(new Item.Properties().stacksTo(1), riftModifiers, Registration.RIFT_EFFECT, null);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if(Objects.requireNonNull(slotContext.entity().getAttribute(Registration.ABILITY_POWER)).hasModifier(ResourceLoc.create("attribute.perplexity.void_inf.ap"))){
            slotContext.entity().getAttribute(Registration.ABILITY_POWER).removeModifier(ResourceLoc.create("attribute.perplexity.void_inf.ap"));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (slotContext.entity().getData(Registration.RIFT_COOLDOWN) == 0) {
                slotContext.entity().setData(Registration.RIFT_STACKS, 0);
                if(slotContext.entity().getAttribute(Registration.OMNIVAMP).hasModifier(ResourceLoc.create("attribute.perplexity.rift.omni"))) {
                    slotContext.entity().getAttribute(Registration.OMNIVAMP).removeModifier(ResourceLoc.create("attribute.perplexity.rift.omni"));
                }
            }
            if (slotContext.entity().getData(Registration.RIFT_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.RIFT_COOLDOWN,
                        slotContext.entity().getData(Registration.RIFT_COOLDOWN.value()) - 1);
            }
            slotContext.entity().getAttribute(Registration.ABILITY_POWER).addOrUpdateTransientModifier(new AttributeModifier(ResourceLoc.create("attribute.perplexity.void_inf.ap"), slotContext.entity().getMaxHealth()*0.02, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.riftmaker_corr"));
        tooltipComponents.add(Component.translatable("tooltip.perplexity.riftmaker_infu"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
