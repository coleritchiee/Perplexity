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

public class NavoriFlickerbladeItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> navoriModifiers = Map.of(
            Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.navori.as"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.navori.ms"), 0.04, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Registration.CRIT_CHANCE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.navori.crit_chance"), 25, AttributeModifier.Operation.ADD_VALUE)
            );


    public NavoriFlickerbladeItem() {
        super(new Item.Properties().stacksTo(1), navoriModifiers, null);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.navori"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
