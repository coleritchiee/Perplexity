package net.iicosahedra.perplexity.common.item;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.Spell;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellMapSavedData;
import net.iicosahedra.perplexity.spell.SpellMapSavedDataManager;
import net.minecraft.core.BlockPos;
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
        BlockPos headPos = new BlockPos(2,16,438);
        SpellCasting.castSpell(pLevel, pPlayer, pUsedHand, SpellMapSavedDataManager.INSTANCE.getSpell(SpellMapSavedDataManager.hashBlockPos(headPos)));
        return super.use(pLevel,pPlayer,pUsedHand);
    }
}
