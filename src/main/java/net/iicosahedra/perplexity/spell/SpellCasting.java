package net.iicosahedra.perplexity.spell;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.iicosahedra.perplexity.spell.components.AbstractModifier;
import net.iicosahedra.perplexity.spell.components.AbstractShape;
import net.iicosahedra.perplexity.spell.event.SpellCastEvent;
import net.iicosahedra.perplexity.spell.targeting.CastResult;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpellCasting {
    public static CastResult castSpell(Level world, LivingEntity entity, InteractionHand hand, Spell spell, int spellKey){
        ItemStack stack = entity.getItemInHand(hand);
        if(spell == null){
            if(world.isClientSide) {
                entity.sendSystemMessage(Component.translatable("Spell not valid"));
            }
            return CastResult.FAIL;
        }
        SpellContext context = new SpellContext(spell, entity, world, spellKey);
        SpellCastEvent event = new SpellCastEvent(context);
        NeoForge.EVENT_BUS.post(event);

        if(world.isClientSide && event.isCanceled()){
            entity.sendSystemMessage(Component.translatable("Spell was interrupted"));
            return CastResult.FAIL;
        }

        SpellCasting casting = new SpellCasting(spell, context);
        if (world.isClientSide && !spell.isEmpty()) {
            return CastResult.FAIL;
        }
        if(casting.onCast(stack, world)){
            return CastResult.SUCCESS;
        }
        return CastResult.FAIL;
    }

    public static double processDamageScaling(double baseDamage, SpellContext context){
        double newDamage = baseDamage;
        double critScale = 1;
        double apScale = baseDamage * ((double)context.getCaster().getData(Registration.ABILITY_POWER)/100);
        if(context.getCaster().getData(Registration.SPELL_CRIT)){
            double critChance = (double) context.getCaster().getData(Registration.CRIT_CHANCE);
            if(Math.random()*100 < critChance){
                critScale *= 2;
            }
        }
        newDamage = (baseDamage+apScale)*critScale;
        int lifeSteal = context.getCaster().getData(Registration.LIFE_STEAL);
        if(lifeSteal>0){
            float amount = (float)(((float) lifeSteal /100)*newDamage);
            context.getCaster().heal(amount);
        }
        return newDamage;
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
            context.getCaster().sendSystemMessage(Component.translatable("You do not have enough mana to cast this spell"));
            return false;
        }
        if(context.getCaster().getData(Registration.TIER)<spell.getTier()){
            context.getCaster().sendSystemMessage(Component.translatable("You are not high enough circle to cast this spell"));
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
