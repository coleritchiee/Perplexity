package net.iicosahedra.perplexity.spell.shapes;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.targeting.CastResult;
import net.minecraft.resources.ResourceLocation;
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
    public CastResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellCasting casting) {
        casting.processEffects(new EntityHitResult(playerEntity));
        return CastResult.SUCCESS;
    }
}
