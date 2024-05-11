package net.iicosahedra.perplexity.common.item;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.Spell;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pPlayer.getItemInHand(pUsedHand) != pPlayer.getMainHandItem()){
            return new InteractionResultHolder<>(InteractionResult.FAIL, pPlayer.getOffhandItem());
        }
        Spell newSpell =  pPlayer.getMainHandItem().get(Registration.SPELL);
        return SpellCasting.castSpell(pLevel, pPlayer, pUsedHand, newSpell);
    }
}
