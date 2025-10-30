package net.iicosahedra.perplexity.util;

import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.item.ItemStack;

public final class ItemData {

    private ItemData() {}

    public static int getCooldown(ItemStack stack) {
        Integer v = stack.get(Registration.COMPONENT_COOLDOWN_TICKS.value());
        return v == null ? 0 : v;
    }

    public static void setCooldown(ItemStack stack, int ticks) {
        stack.set(Registration.COMPONENT_COOLDOWN_TICKS.value(), ticks);
    }

    public static int getStacks(ItemStack stack) {
        Integer v = stack.get(Registration.COMPONENT_STACKS.value());
        return v == null ? 0 : v;
    }

    public static void setStacks(ItemStack stack, int stacks) {
        stack.set(Registration.COMPONENT_STACKS.value(), stacks);
    }

    public static int getStoredDamage(ItemStack stack) {
        Integer v = stack.get(Registration.COMPONENT_STORED_DAMAGE.value());
        return v == null ? 0 : v;
    }

    public static void setStoredDamage(ItemStack stack, int value) {
        stack.set(Registration.COMPONENT_STORED_DAMAGE.value(), value);
    }

    public static int getFlag(ItemStack stack) {
        Integer v = stack.get(Registration.COMPONENT_FLAG.value());
        return v == null ? 0 : v;
    }

    public static void setFlag(ItemStack stack, int value) {
        stack.set(Registration.COMPONENT_FLAG.value(), value);
    }
}


