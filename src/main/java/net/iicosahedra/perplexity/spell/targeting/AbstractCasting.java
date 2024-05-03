package net.iicosahedra.perplexity.spell.targeting;

import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.SpellResolver;
import net.iicosahedra.perplexity.spell.component.AbstractSpellPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public abstract class AbstractCasting extends AbstractSpellPart {

    public AbstractCasting(String tag, String name) {
        super(tag, name);
    }

    public AbstractCasting(ResourceLocation registryName, String name) {
        super(registryName, name);
    }

    public abstract CastActionResult onCast(@Nullable ItemStack stack, LivingEntity playerEntity, SpellContext context, SpellResolver resolver);

    public abstract CastActionResult onCastOnBlock(UseOnContext context, SpellContext spellContext, SpellResolver resolver);

    public abstract CastActionResult onCastOnBlock(BlockHitResult blockHitResult, LivingEntity caster, SpellContext spellContext, SpellResolver resolver);

    public abstract CastActionResult onCastOnEntity(@Nullable ItemStack stack, LivingEntity target, LivingEntity caster, SpellContext spellContext, SpellResolver resolver);
}
