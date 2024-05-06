package net.iicosahedra.perplexity.spell.components;

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
    private int manaCost;

    private final ResourceLocation registryName;
    private String name;
    public AbstractEffect(ResourceLocation registryName, String name) {
        this.registryName = registryName;
        this.name = name;
    }
    public AbstractEffect(String tag, String name) {
        this(ResourceLoc.create(tag), name);
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
}
