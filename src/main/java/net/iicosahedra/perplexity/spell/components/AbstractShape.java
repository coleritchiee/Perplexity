package net.iicosahedra.perplexity.spell.components;

import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.targeting.CastResult;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
public abstract class AbstractShape implements ISpellComponent{
    private int manaCost;

    private final ResourceLocation registryName;
    private String name;
    public AbstractShape(ResourceLocation registryName, String name) {
        this.registryName = registryName;
        this.name = name;
    }
    public AbstractShape(String tag, String name) {
        this(ResourceLoc.create(tag), name);
    }
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public abstract CastResult onCast(@Nullable ItemStack stack, LivingEntity player, Level world, SpellContext context, SpellCasting casting);
}
