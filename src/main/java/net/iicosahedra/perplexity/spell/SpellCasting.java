package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.iicosahedra.perplexity.spell.components.AbstractModifier;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.targeting.CastResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpellCasting {
    public static InteractionResultHolder<ItemStack> castSpell(Level world, LivingEntity entity, InteractionHand hand, @NotNull Spell spell){
        ItemStack stack = entity.getItemInHand(hand);
        SpellCasting casting = new SpellCasting(spell, new SpellContext(spell, entity, world));
        if (world.isClientSide && !spell.isEmpty()) {
            return InteractionResultHolder.pass(entity.getItemInHand(hand));
        }
        if(casting.onCast(stack, world)){
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }

    public List<AbstractShape> shapes =  new ArrayList<>();
    public List<AbstractEffect> effects = new ArrayList<>();
    public List<AbstractModifier> modifiers = new ArrayList<>();
    public Spell spell;
    public SpellContext context;

    public SpellCasting(Spell spell, SpellContext context){
        this.spell = spell;
        this.context = context;
        this.shapes = spell.getShapes();
        this.effects = spell.getEffects();
        this.modifiers = spell.getModifiers();
    }

    public boolean onCast(ItemStack stack, Level level){
        if(context.getCaster().getData(Registration.MANA)<spell.getManaCost()){
            //TODO: Message player not enough mana
            return false;
        }
        if(context.getCaster().getData(Registration.TIER)<spell.getTier()){
            //TODO: Message player not high enough tier
            return false;
        }
        boolean success = false;
        for (AbstractShape shape : shapes) {
            CastResult result = shape.onCast(stack, context.getCaster(), level, context, this);
            if(result == CastResult.SUCCESS){
                expendMana();
            }
            success =  result.success;
        }
        return success;
    }

    private void expendMana() {
        context.getCaster().setData(Registration.MANA, context.getCaster().getData(Registration.MANA)- spell.getManaCost());
    }

    public void processEffects(HitResult result) {
        for (AbstractEffect effect : effects) {
            effect.onCast(result, context.getCaster().level(), context.getCaster(), context, this);
        }
    }
}
