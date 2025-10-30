package net.iicosahedra.perplexity.util;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.item.ItemAnvilItem;
import net.iicosahedra.perplexity.setup.Registration;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class AnvilStateMaintenance {
    private static final int MAX_ROLL_TICKS = 20 * 30; // 30s safety timeout

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        resetStaleAnvils(event.getEntity());
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        // Periodically sweep all players and reset stale rolling anvils
        if (event.getServer() == null) return;
        for (var player : event.getServer().getPlayerList().getPlayers()) {
            resetStaleAnvils(player);
        }
    }

    private static void resetStaleAnvils(net.minecraft.world.entity.player.Player player) {
        long now = player.level().getGameTime();
        // main hand
        resetIfStale(player.getMainHandItem(), now);
        // off hand
        resetIfStale(player.getOffhandItem(), now);
        // inventory
        var inv = player.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            resetIfStale(inv.getItem(i), now);
        }
    }

    private static void resetIfStale(net.minecraft.world.item.ItemStack stack, long now) {
        if (!(stack.getItem() instanceof ItemAnvilItem)) return;
        Integer state = stack.get(Registration.COMPONENT_ANVIL_STATE.value());
        if (state == null || state != ItemAnvilItem.STATE_ROLLING) return;
        Integer start = stack.get(Registration.COMPONENT_ANVIL_START_TICK.value());
        if (start == null) start = (int) now;
        if (now - start > MAX_ROLL_TICKS) {
            // Timeout reached: reset so the player can roll again later
            stack.set(Registration.COMPONENT_ANVIL_STATE.value(), ItemAnvilItem.STATE_IDLE);
            stack.remove(Registration.COMPONENT_ANVIL_SEED.value());
            stack.remove(Registration.COMPONENT_ANVIL_RESULT.value());
            stack.remove(Registration.COMPONENT_ANVIL_START_TICK.value());
        }
    }
}


