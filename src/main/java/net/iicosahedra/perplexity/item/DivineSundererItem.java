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

public class DivineSundererItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> divineSundererModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.ds.hp"), 4, AttributeModifier.Operation.ADD_VALUE),
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.ds.ad"), 5.5, AttributeModifier.Operation.ADD_VALUE));


    public DivineSundererItem() {
        super(new Item.Properties().stacksTo(1), divineSundererModifiers, Registration.DIVINE_SUNDERER_EFFECT, null);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (slotContext.entity().getData(Registration.DIVINE_SUNDERER_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.DIVINE_SUNDERER_COOLDOWN,
                        slotContext.entity().getData(Registration.DIVINE_SUNDERER_COOLDOWN.value()) - 1);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.divinesunderer"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
