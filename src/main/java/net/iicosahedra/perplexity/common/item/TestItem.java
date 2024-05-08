package net.iicosahedra.perplexity.common.item;

import net.iicosahedra.perplexity.spell.Spell;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.effects.EffectBreak;
import net.iicosahedra.perplexity.spell.effects.EffectDamage;
import net.iicosahedra.perplexity.spell.effects.EffectFling;
import net.iicosahedra.perplexity.spell.shapes.ShapeProj;
import net.iicosahedra.perplexity.spell.shapes.ShapeSelf;
import net.iicosahedra.perplexity.spell.shapes.ShapeTouch;
import net.minecraft.world.InteractionHand;
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
        Spell spell = new Spell();
        spell.add(ShapeProj.INSTANCE);
        spell.add(ShapeSelf.INSTANCE);
        spell.add(EffectFling.INSTANCE);
        return SpellCasting.castSpell(pLevel, pPlayer, pUsedHand, spell);
    }
}
