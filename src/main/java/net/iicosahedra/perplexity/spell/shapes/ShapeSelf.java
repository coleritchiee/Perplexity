package net.iicosahedra.perplexity.spell.shapes;

import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.SpellResolver;
import net.iicosahedra.perplexity.spell.component.AbstractShape;
import net.iicosahedra.perplexity.spell.targeting.CastActionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class ShapeSelf extends AbstractShape {
    public static final ShapeSelf INSTANCE = new ShapeSelf();

    public ShapeSelf() {
        super("shape.self", "self");
    }

    @Override
    public CastActionResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellResolver resolver) {
        resolver.processEffects(new EntityHitResult(playerEntity));
        return CastActionResult.SUCCESS;
    }
}
