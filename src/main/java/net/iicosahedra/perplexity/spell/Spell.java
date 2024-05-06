package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.iicosahedra.perplexity.spell.components.AbstractModifier;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.components.ISpellComponent;

import java.util.ArrayList;
import java.util.List;

public class Spell {
    private List<AbstractShape> shapes = new ArrayList<>();
    private List<AbstractEffect> effects = new ArrayList<>();
    private List<AbstractModifier> modifiers = new ArrayList<>();

    private String name;

    public Spell(){}

    public Spell(String name, ISpellComponent... components){
        this.name = name;
        add(components);
    }

    public Spell add(ISpellComponent... components) {
        for(ISpellComponent component:components){
            if(component instanceof AbstractEffect){
                effects.add((AbstractEffect) component);
            }
            if(component instanceof AbstractModifier){
                modifiers.add((AbstractModifier) component);
            }
            if(component instanceof AbstractShape){
                shapes.add((AbstractShape) component);
            }
        }
        return this;
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
}
