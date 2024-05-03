package net.iicosahedra.perplexity.spell;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpellCasting {

    public static InteractionResultHolder<ItemStack> castSpell(Level world, LivingEntity entity, InteractionHand hand, @NotNull Spell spell){
        ItemStack stack = entity.getItemInHand(hand);
        SpellResolver resolver = new SpellResolver(spell, new SpellContext(spell, entity, world));
        if (world.isClientSide && spell.isValid()) {
            return InteractionResultHolder.pass(entity.getItemInHand(hand));
        }
        if(resolver.onCast(stack, world)){
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }
}
