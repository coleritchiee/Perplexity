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

import java.util.List;
import java.util.Map;

public class NashorsToothItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> nashorsModifiers = Map.of(
            Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.nashors.as"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Registration.ABILITY_POWER, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.nashors.ap"), 8, AttributeModifier.Operation.ADD_VALUE),
            Registration.ABILITY_HASTE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.nashors.ah"), 15, AttributeModifier.Operation.ADD_VALUE)
    );


    public NashorsToothItem() {
        super(new Item.Properties().stacksTo(1), nashorsModifiers, null);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.nashors"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
