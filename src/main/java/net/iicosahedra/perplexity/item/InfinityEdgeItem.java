package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class InfinityEdgeItem extends RelicItem{
    private static final Map<Holder<Attribute>, AttributeModifier> ieModifiers = Map.of(
            Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.ad"), 7, AttributeModifier.Operation.ADD_VALUE),
            Registration.CRIT_CHANCE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.ie.crit_chance"), 25, AttributeModifier.Operation.ADD_VALUE),
            Registration.CRIT_DAMAGE, new AttributeModifier(ResourceLoc.create("attribute.perplexity.player.item.ie.crit_damage"), 40, AttributeModifier.Operation.ADD_VALUE)
    );

    public InfinityEdgeItem() {
        super(new Item.Properties().stacksTo(1), ieModifiers, null, null);
    }
}
