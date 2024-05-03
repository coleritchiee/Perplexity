package net.iicosahedra.perplexity.spell.shapes;

import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.SpellResolver;
import net.iicosahedra.perplexity.spell.component.AbstractShape;
import net.iicosahedra.perplexity.spell.targeting.CastActionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class ShapeTouch extends AbstractShape {
    public static final ShapeTouch INSTANCE = new ShapeTouch();

    public ShapeTouch() {
        super("shape.touch", "touch");
    }

    @Override
    public CastActionResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellResolver resolver) {
        if(playerEntity instanceof Player) {
            HitResult result = playerEntity.pick(((Player) playerEntity).getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE), 1.0F, false);
            resolver.processEffects(result);
            return CastActionResult.SUCCESS;
        }
        return CastActionResult.FAIL;
    }
}
