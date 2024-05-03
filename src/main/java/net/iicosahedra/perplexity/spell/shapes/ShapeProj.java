package net.iicosahedra.perplexity.spell.shapes;

import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.SpellResolver;
import net.iicosahedra.perplexity.spell.component.AbstractShape;
import net.iicosahedra.perplexity.spell.entity.EntityProj;
import net.iicosahedra.perplexity.spell.targeting.CastActionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ShapeProj extends AbstractShape {

    public static final ShapeProj INSTANCE = new ShapeProj();

    public ShapeProj() {
        super("shape.proj", "proj");
    }

    @Override
    public CastActionResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellResolver resolver) {
        shootProj(world, playerEntity, resolver);
        return CastActionResult.SUCCESS;
    }

    public void shootProj(Level world, LivingEntity shooter, SpellResolver resolver){
        EntityProj proj = new EntityProj(world, resolver);
        proj.shoot(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 1.5f, 0.8f);
        world.addFreshEntity(proj);
    }
}
