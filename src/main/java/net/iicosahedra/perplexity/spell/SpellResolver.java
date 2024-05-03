package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.spell.component.AbstractEffect;
import net.iicosahedra.perplexity.spell.component.AbstractShape;
import net.iicosahedra.perplexity.spell.component.AbstractSpellPart;
import net.iicosahedra.perplexity.spell.targeting.CastActionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class SpellResolver{
    public List<AbstractShape> shapes =  new ArrayList<>();
    public List<AbstractEffect> effects = new ArrayList<>();
    public Spell spell;
    public SpellContext context;

    public SpellResolver(Spell spell, SpellContext context){
        this.spell = spell;
        this.context = context;
        for(AbstractSpellPart part: spell.getComponents()){
            if(part instanceof AbstractShape){
                shapes.add((AbstractShape) part);
            }
            if(part instanceof AbstractEffect){
                effects.add((AbstractEffect) part);
            }
        }
    }

    public boolean onCast(ItemStack stack, Level level){
        for (AbstractShape shape : shapes) {
            CastActionResult result = shape.onCast(stack, context.getCaster(), level, context, this);
            if(result == CastActionResult.SUCCESS){
                expendMana();
            }
            return result.success;
        }
        return false;
    }

    public void expendMana() {

    }

    public void processEffects(HitResult result) {
        for (AbstractEffect effect : effects) {
            effect.onCast(result, context.getCaster().level(), context.getCaster(), context, this);
        }
    }
}
