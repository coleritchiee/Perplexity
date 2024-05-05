package net.iicosahedra.perplexity.spell;
import net.iicosahedra.perplexity.spell.component.AbstractSpellPart;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Spell implements Cloneable{
    public List<AbstractSpellPart> components = new ArrayList<>();
    public String name = "";

    public Spell(List<AbstractSpellPart> components){
        this.components = components == null ? new ArrayList<>() : components;
    }

    public Spell(){
    }

    public Spell(AbstractSpellPart... components){
        super();
        add(components);
    }

    public Spell add(AbstractSpellPart spellPart){
        components.add(spellPart);
        return this;
    }

    public Spell add(AbstractSpellPart... spellParts){
        for(AbstractSpellPart part: spellParts){
            add(part);
        }
        return this;
    }

    public boolean isEmpty(){
        return components == null || components.isEmpty();
    }

    public boolean isValid(){
        return !this.isEmpty();
    }

    public Spell setComponents(@NotNull List<AbstractSpellPart> components){
        this.components = components;
        return this;
    }

    public List<AbstractSpellPart> getComponents() {
        return components;
    }

    @Override
    public Spell clone(){
       try{
           Spell clone = (Spell) super.clone();
              clone.components = new ArrayList<>(this.components);
              return clone;
       }
       catch (CloneNotSupportedException e){
           throw new AssertionError();
       }
    }
}
