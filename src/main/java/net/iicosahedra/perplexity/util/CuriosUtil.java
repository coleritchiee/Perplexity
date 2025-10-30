package net.iicosahedra.perplexity.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

public final class CuriosUtil {

    private CuriosUtil() {}

    public static Optional<ItemStack> findFirstEquipped(Player player, Class<? extends Item> itemClass) {
        Optional<ICuriosItemHandler> handler = CuriosApi.getCuriosInventory(player);
        if (handler.isEmpty()) {
            return Optional.empty();
        }
        var curios = handler.get().getCurios();
        for (var entry : curios.values()) {
            var stacks = entry.getStacks();
            for (int i = 0; i < stacks.getSlots(); i++) {
                ItemStack stack = stacks.getStackInSlot(i);
                if (!stack.isEmpty() && itemClass.isInstance(stack.getItem())) {
                    return Optional.of(stack);
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<ItemStack> findEquippedInSlot(Player player, int slotIndex, Class<? extends Item> itemClass) {
        Optional<ICuriosItemHandler> handler = CuriosApi.getCuriosInventory(player);
        if (handler.isEmpty()) {
            return Optional.empty();
        }
        return handler.get().getCurios().values().stream()
                .flatMap(curio -> {
                    if (slotIndex >= 0 && slotIndex < curio.getStacks().getSlots()) {
                        ItemStack stack = curio.getStacks().getStackInSlot(slotIndex);
                        return java.util.stream.Stream.of(stack);
                    }
                    return java.util.stream.Stream.empty();
                })
                .filter(stack -> !stack.isEmpty() && itemClass.isInstance(stack.getItem()))
                .findFirst();
    }
}


