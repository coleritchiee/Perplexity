package net.iicosahedra.perplexity.common.item;

import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.Spell;
import net.iicosahedra.perplexity.spell.SpellMapSavedDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestSetItem extends Item {
    public TestSetItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Spell spell = new Spell("spell");
        spell.add(Registration.SHAPE_SELF.get());
        spell.add(Registration.EFFECT_FLING.get());
        BlockPos headPos = new BlockPos(3,16,438);
        SpellMapSavedDataManager.INSTANCE.addSpell(SpellMapSavedDataManager.hashBlockPos(headPos), spell);
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
