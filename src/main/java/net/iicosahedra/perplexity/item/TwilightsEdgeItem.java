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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Map;

public class TwilightsEdgeItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> twilightsModifiers = Map.of(
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.twilights.ad"), 7, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.twilights.ability_power"), 10, AttributeModifier.Operation.ADD_VALUE)
    );
    public TwilightsEdgeItem() {
        super(new Properties().stacksTo(1), twilightsModifiers, Registration.TWILIGHTS_EFFECT, Registration.TWILIGHTS_ABILITY);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(!slotContext.entity().level().isClientSide()) {
            if (slotContext.entity().getData(Registration.TWILIGHTS_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.TWILIGHTS_COOLDOWN,
                        slotContext.entity().getData(Registration.TWILIGHTS_COOLDOWN.value()) - 1);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.twilights"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
