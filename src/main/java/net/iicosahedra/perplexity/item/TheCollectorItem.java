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

public class TheCollectorItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> collectorModifiers = Map.of(
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.collector.ad"), 5, AttributeModifier.Operation.ADD_VALUE),
            Registration.LETHALITY, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.collector.lethality"), 10, AttributeModifier.Operation.ADD_VALUE),
            Registration.CRIT_CHANCE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.collector.crit_chance"), 25, AttributeModifier.Operation.ADD_VALUE)
    );

    public TheCollectorItem() {
        super(new Item.Properties().stacksTo(1), collectorModifiers, null);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.collector"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
