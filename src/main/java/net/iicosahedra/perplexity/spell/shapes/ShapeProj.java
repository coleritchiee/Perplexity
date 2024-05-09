package net.iicosahedra.perplexity.spell.shapes;

import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.entity.EntityProj;
import net.iicosahedra.perplexity.spell.targeting.CastResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ShapeProj extends AbstractShape {
    public ShapeProj() {
        super("shape.proj","proj",0,0);
    }

    @Override
    public CastResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellCasting casting) {
        shootProj(world, playerEntity, casting);
        return CastResult.SUCCESS;
    }

    public void shootProj(Level world, LivingEntity shooter, SpellCasting casting){
        EntityProj proj = new EntityProj(world, casting);
        proj.shoot(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 1.5f, 0.8f);
        world.addFreshEntity(proj);
    }
}
