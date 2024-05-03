package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.spell.collections.SpellParts;
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

    public CompoundTag serialize(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        CompoundTag componentTag = new CompoundTag();
        for(int i = 0; i < components.size(); i++){
             AbstractSpellPart part = components.get(i);
             componentTag.putString("part" + i, part.getRegistryName().toString());
        }
        componentTag.putInt("size", components.size());
        tag.put("component", componentTag);
        return tag;
    }

    public static Spell fromTag(@Nullable CompoundTag tag){
        if(tag == null){
            return new Spell();
        }
        Spell spell = new Spell();
        spell.name = tag.getString("name");
        CompoundTag componentTag = tag.getCompound("component");
        int size = componentTag.getInt("size");
        for(int i = 0; i < size; i++){
            ResourceLocation regName = new ResourceLocation(componentTag.getString("part" + i));
            AbstractSpellPart part = SpellParts.SPELL_PARTS.get(regName);
            if (part != null) {
                spell.add(part);
            }
        }
        return spell;

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
