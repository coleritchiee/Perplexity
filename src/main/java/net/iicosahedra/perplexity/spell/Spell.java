package net.iicosahedra.perplexity.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.iicosahedra.perplexity.spell.components.AbstractModifier;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.components.ISpellComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Spell {
    private List<AbstractShape> shapes = new ArrayList<>();
    private List<AbstractEffect> effects = new ArrayList<>();
    private List<AbstractModifier> modifiers = new ArrayList<>();

    private List<ResourceLocation> componentList = new ArrayList<>();

    private List<Integer> affinities = new ArrayList<>();

    private int shapeCost;
    private int effectCost;
    private int modifierCost;

    private int manaCost;

    private int tier;

    private String name;

    public Spell(){}

    public Spell(String name, ISpellComponent... components){
        this.name = name;
        add(components);
    }

    public Spell add(ISpellComponent... components) {
        for(ISpellComponent component:components){
            if(component instanceof AbstractEffect){
                if(((AbstractEffect) component).getTier() > tier){
                    affinities.add(((AbstractEffect) component).getAffinity());
                    tier = ((AbstractEffect) component).getTier();
                }
                effects.add((AbstractEffect) component);
            }
            if(component instanceof AbstractModifier){
                if(((AbstractModifier) component).getTier() > tier){
                    tier = ((AbstractModifier) component).getTier();
                }
                modifiers.add((AbstractModifier) component);
            }
            if(component instanceof AbstractShape){
                if(((AbstractShape) component).getTier() > tier){
                    tier = ((AbstractShape) component).getTier();
                }
                shapes.add((AbstractShape) component);
            }
            componentList.add(component.getRegistryName());
        }
        updateManaCost();
        return this;
    }

    private void updateManaCost() {
        int i  = 0;
        for(AbstractEffect effect: effects){
            if(i == 0){
                effectCost = effect.getManaCost();
            }
            else{
                effectCost += effect.getManaCost();
            }
            i++;
        }
        i  = 0;
        for(AbstractShape shape: shapes){
            if(i == 0){
                shapeCost = shape.getManaCost();
            }
            else{
                shapeCost += shape.getManaCost();
            }
            i++;
        }
        i  = 0;
        for(AbstractModifier modifier: modifiers){
            if(i == 0){
                modifierCost = modifier.getManaCost();
            }
            else{
                modifierCost += modifier.getManaCost();
            }
            i++;
        }
        manaCost = effectCost+shapeCost+modifierCost;
    }

    public boolean isEmpty(){
        return shapes.isEmpty()||effects.isEmpty()|| modifiers.isEmpty();
    }
    public List<AbstractEffect> getEffects() {
        return effects;
    }
    public List<AbstractModifier> getModifiers() {
        return modifiers;
    }
    public List<AbstractShape> getShapes() {
        return shapes;
    }

    public List<ResourceLocation> getComponentList(){
        return componentList;
    }

    public int getManaCost(){
        return manaCost;
    }

    public int getTier(){
        return tier;
    }

    //public static final Codec<Spell> SPELL_CODEC = RecordCodecBuilder.create(spellInstance ->
}
