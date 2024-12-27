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

public class ThornmailItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> thornmailModifiers = Map.of(
            Attributes.MAX_HEALTH, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.thornmail.hp"), 1.5, AttributeModifier.Operation.ADD_VALUE),
            Attributes.ARMOR, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.thornmail.armor"), 7.5, AttributeModifier.Operation.ADD_VALUE)
    );


    public ThornmailItem() {
        super(new Item.Properties().stacksTo(1), thornmailModifiers, Registration.THORNMAIL_EFFECT, null);
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.thornmail"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
