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

public class WarmogsArmorItem extends RelicItem {
    private static final Map<Holder<Attribute>, AttributeModifier> warmogsModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.warmogs.hp"), 10, AttributeModifier.Operation.ADD_VALUE),
            Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.warmogs.ms"), 0.04, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            );

    public WarmogsArmorItem() {
        super(new Item.Properties().stacksTo(1), warmogsModifiers, Registration.WARMOGS_EFFECT, null);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if(slotContext.entity().getMaxHealth() >= 40) {
            if (slotContext.entity().getData(Registration.WARMOGS_COMBAT_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.WARMOGS_COMBAT_COOLDOWN,
                        slotContext.entity().getData(Registration.WARMOGS_COMBAT_COOLDOWN.value()) - 1);
            } else if (slotContext.entity().getData(Registration.WARMOGS_REGEN_COOLDOWN.value()) > 0) {
                slotContext.entity().setData(Registration.WARMOGS_REGEN_COOLDOWN,
                        slotContext.entity().getData(Registration.WARMOGS_REGEN_COOLDOWN.value()) - 1);
            } else if (slotContext.entity().getData(Registration.WARMOGS_REGEN_COOLDOWN.value()) == 0) {
                slotContext.entity().heal(0.025f * slotContext.entity().getMaxHealth());
                slotContext.entity().setData(Registration.WARMOGS_REGEN_COOLDOWN, 20);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.warmogs"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
