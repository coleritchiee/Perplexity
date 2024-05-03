package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.spell.component.AbstractSpellPart;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SpellContext implements Cloneable{
    private boolean isCancelled;

    private Spell spell;
    private ItemStack castingItem = ItemStack.EMPTY;
    private LivingEntity caster;
    private Level level;

    public CompoundTag tag = new CompoundTag();

    private int index = 0;

    public SpellContext(@NotNull Spell spell, LivingEntity caster, Level level) {
        this.level = level;
        this.spell = spell;
        this.caster = caster;
        this.isCancelled = false;
    }

    public @Nullable AbstractSpellPart nextPart(){
        this.index++;
        AbstractSpellPart part = null;
        try{
            part = getSpell().components.get(index-1);
        }
        catch (Throwable e){
            System.out.println("rip");
        }
        return part;
    }

    public boolean hasNextPart(){
        return spell.isValid() && !isCancelled && index < spell.components.size();
    }

    public @NotNull Spell getSpell() {
        return spell == null? new Spell() : spell;
    }

    public SpellContext resetCastCounter(){
        index = 0;
        this.isCancelled = false;
        return this;
    }

    public SpellContext withSpell(Spell spell){
        this.spell = spell;
        return this;
    }

    public ItemStack getCastingItem() {
        return castingItem;
    }

    public boolean isCancelled(){
        return isCancelled;
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public@NotNull Spell getRemainingSpell() {
        if (getIndex() >= getSpell().components.size())
            return getSpell().clone().setComponents(new ArrayList<>());
        return getSpell().clone().setComponents(new ArrayList<>(getSpell().components.subList(getIndex(), getSpell().components.size())));
    }

    @Override
    public SpellContext clone(){
        try{
            SpellContext clone = (SpellContext) super.clone();
            clone.spell = this.spell.clone();
            clone.castingItem = this.castingItem.copy();
            clone.caster = this.caster;
            clone.level = this.level;
            clone.tag = this.tag.copy();
            clone.index = this.index;
            return clone;
        }
        catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

    public void setCaster(@Nullable LivingEntity caster) {
        this.caster = caster;
    }

    public LivingEntity getCaster() {
        return caster;
    }

    public void setCastingItem(ItemStack stack) {
        this.castingItem = stack.copy();
    }
}
