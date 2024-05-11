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

    private int tier;

    private final ResourceLocation registryName;
    private String name;
    public AbstractShape(ResourceLocation registryName, String name, int tier, int manaCost) {
        this.registryName = registryName;
        this.name = name;
        this.tier = tier;
        this.manaCost = manaCost;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public abstract CastResult onCast(@Nullable ItemStack stack, LivingEntity player, Level world, SpellContext context, SpellCasting casting);

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getManaCost() {
        return manaCost;
    }
}
