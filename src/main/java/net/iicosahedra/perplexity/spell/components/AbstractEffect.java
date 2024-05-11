package net.iicosahedra.perplexity.spell.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.iicosahedra.perplexity.spell.Affinities;
import net.iicosahedra.perplexity.spell.Spell;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEffect implements ISpellComponent{
    private int affinity;
    private int manaCost;

    private int tier;

    private final ResourceLocation registryName;
    private String name;
    public AbstractEffect(ResourceLocation registryName, String name, int tier, int manaCost, int affinity) {
        this.registryName = registryName;
        this.name = name;
        this.affinity = affinity;
        this.tier = tier;
        this.manaCost = manaCost;
    }
    public void onCast(HitResult hitResult, Level world, @NotNull LivingEntity caster, SpellContext spellContext, SpellCasting casting) {
        if(hitResult instanceof BlockHitResult blockHitResult){
            onCastOnBlock(blockHitResult, world, caster, spellContext, casting);
        }
        else if(hitResult instanceof EntityHitResult entityHitResult){
            onCastOnEntity(entityHitResult, world, caster, spellContext, casting);
        }
    }
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public void onCastOnEntity(EntityHitResult entityHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellCasting casting) {
    }

    public void onCastOnBlock(BlockHitResult blockHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellCasting casting) {
    }

    public int getTier() {
        return tier;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getAffinity(){
        return affinity;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

}
