package net.iicosahedra.perplexity.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpellContext {
    private Spell spell;
    private ItemStack castingItem = ItemStack.EMPTY;
    private LivingEntity caster;
    private Level level;

    public SpellContext(@NotNull Spell spell, LivingEntity caster, Level level) {
        this.level = level;
        this.castingItem = castingItem;
        this.spell = spell;
        this.caster = caster;
    }

    public LivingEntity getCaster() {
        return caster;
    }
}
