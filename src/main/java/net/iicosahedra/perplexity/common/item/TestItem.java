package net.iicosahedra.perplexity.common.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
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
        Integer key = pPlayer.getMainHandItem().get(Registration.SPELL);
        SpellCasting.castSpell(pLevel, pPlayer, pUsedHand, SpellMapSavedDataManager.INSTANCE.getSpell(key));
        return super.use(pLevel,pPlayer,pUsedHand);
    }
}
