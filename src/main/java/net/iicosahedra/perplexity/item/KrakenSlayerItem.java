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

public class KrakenSlayerItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> krakenSlayerModifiers = Map.of(
            Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.kraken.as"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.kraken.ad"), 4.5, AttributeModifier.Operation.ADD_VALUE),
            Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLoc.create("attribute.perplexity.item.kraken.ms"), 0.04, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );


    public KrakenSlayerItem() {
        super(new Item.Properties().stacksTo(1), krakenSlayerModifiers, Registration.KRAKEN_EFFECT, null);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.perplexity.kraken"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
