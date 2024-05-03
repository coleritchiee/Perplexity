package net.iicosahedra.perplexity.spell.component;

import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.SpellResolver;
import net.iicosahedra.perplexity.spell.targeting.CastActionResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class AbstractShape extends AbstractSpellPart{

    public AbstractShape(ResourceLocation registryName, String name) {
        super(registryName, name);
    }
    public AbstractShape(String tag, String name) {
        super(tag, name);
    }

    public abstract CastActionResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellContext context, SpellResolver resolver);

}
