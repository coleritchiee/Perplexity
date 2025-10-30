package net.iicosahedra.perplexity.network;

import net.iicosahedra.perplexity.ability.ActiveAbility;
import net.iicosahedra.perplexity.network.packet.AbilityUsePacket;
import net.iicosahedra.perplexity.network.packet.ProcessDeltaMovementPacket;
import net.iicosahedra.perplexity.network.packet.ProcessFlightMovementPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler{
    public static void handleAbility(final AbilityUsePacket abilityUsePacket, final IPayloadContext context){
        ActiveAbility.useAbility(context.player(), abilityUsePacket.slot());
    }

    public static void processDeltaMovement(final ProcessDeltaMovementPacket packet, final IPayloadContext context){

    }
    public static void processFlightMovement(final ProcessFlightMovementPacket packet, final IPayloadContext context){
    }

    public static void onUseAnvil(final net.iicosahedra.perplexity.network.packet.UseAnvilPacket packet, final IPayloadContext context){
        var player = context.player();
        var stack = player.getItemInHand(packet.hand());
        if (!(stack.getItem() instanceof net.iicosahedra.perplexity.item.ItemAnvilItem)) return;
        java.util.Random rng = new java.util.Random();
        int seed = rng.nextInt();
        var pool = net.iicosahedra.perplexity.util.AnvilPool.getRelicItemIds();
        if (pool.isEmpty()) return;
        int duration = 120; // 6 seconds at 20 tps
        int listSize = 40;
        java.util.ArrayList<net.minecraft.resources.ResourceLocation> display = new java.util.ArrayList<>(listSize);
        for (int i = 0; i < listSize - 1; i++) {
            display.add(pool.get(rng.nextInt(pool.size())));
        }
        // Final result
        net.minecraft.resources.ResourceLocation result = pool.get(rng.nextInt(pool.size()));
        display.add(result);
        int revealIndex = listSize - 1;

        stack.set(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_SEED.value(), seed);
        stack.set(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_RESULT.value(), result);
        stack.set(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_STATE.value(), net.iicosahedra.perplexity.item.ItemAnvilItem.STATE_ROLLING);
        stack.set(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_START_TICK.value(), (int) player.level().getGameTime());

        var packetOut = new net.iicosahedra.perplexity.network.packet.AnvilRollPacket(seed, display, revealIndex, duration);
        if (player instanceof net.minecraft.server.level.ServerPlayer sp) {
            net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(sp, packetOut);
        }

        // Auto-award after duration on server to keep flow moving even if client doesn't claim
        net.iicosahedra.perplexity.util.TickScheduler.schedule(duration, () -> {
            // Find the current anvil stack by seed to handle inventory moves
            int currentSeed = seed;
            var target = findAnvilStackBySeed(player, currentSeed);
            var id = (target != null) ? target.get(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_RESULT.value()) : null;
            if (id != null) {
                var item = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(id);
                if (item != net.minecraft.world.item.Items.AIR) {
                    var reward = new net.minecraft.world.item.ItemStack(item);
                    // Prefer placing into the same hand slot if it became empty after consumption
                    var preferredHand = (player.getMainHandItem() == target) ? net.minecraft.world.InteractionHand.MAIN_HAND :
                            ((player.getOffhandItem() == target) ? net.minecraft.world.InteractionHand.OFF_HAND : net.minecraft.world.InteractionHand.MAIN_HAND);
                    if (target != null && player instanceof net.minecraft.server.level.ServerPlayer) {
                        giveReward(player, target, preferredHand, reward);
                        // consume and clear components on the remaining stack
                        target.shrink(1);
                        clearAnvilComponents(target);
                    } else if (target != null) {
                        // Player likely offline/invalid; just reset the item so they can roll again later
                        clearAnvilComponents(target);
                    }
                }
            }
        });
    }

    public static void onAnvilClaim(final net.iicosahedra.perplexity.network.packet.AnvilClaimPacket packet, final IPayloadContext context){
        var player = context.player();
        var stack = player.getItemInHand(packet.hand());
        var resultId = stack.get(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_RESULT.value());
        if (resultId != null) {
            var item = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(resultId);
            if (item != net.minecraft.world.item.Items.AIR) {
                var reward = new net.minecraft.world.item.ItemStack(item);
                // consume one anvil first to free the hand slot, then place reward
                stack.shrink(1);
                giveReward(player, stack, packet.hand(), reward);
                clearAnvilComponents(stack);
            } else {
                stack.shrink(1);
                clearAnvilComponents(stack);
            }
        }
    }

    private static void giveReward(net.minecraft.world.entity.player.Player player,
                                   net.minecraft.world.item.ItemStack anvilStack,
                                   net.minecraft.world.InteractionHand preferredHand,
                                   net.minecraft.world.item.ItemStack reward) {
        // If the preferred hand now has an empty slot (anvil consumed), put the reward there
        net.minecraft.world.item.ItemStack current = player.getItemInHand(preferredHand);
        if (current.isEmpty()) {
            player.setItemInHand(preferredHand, reward);
            return;
        }
        // Otherwise try normal inventory insert
        if (!player.addItem(reward)) {
            // Finally, drop at feet
            player.drop(reward, false);
        }
    }

    private static net.minecraft.world.item.ItemStack findAnvilStackBySeed(net.minecraft.world.entity.player.Player player, int seed) {
        var main = player.getMainHandItem();
        if (isAnvilWithSeed(main, seed)) return main;
        var off = player.getOffhandItem();
        if (isAnvilWithSeed(off, seed)) return off;
        var inv = player.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            var st = inv.getItem(i);
            if (isAnvilWithSeed(st, seed)) return st;
        }
        return null;
    }

    private static boolean isAnvilWithSeed(net.minecraft.world.item.ItemStack stack, int seed) {
        if (stack == null || !(stack.getItem() instanceof net.iicosahedra.perplexity.item.ItemAnvilItem)) return false;
        Integer s = stack.get(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_SEED.value());
        Integer state = stack.get(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_STATE.value());
        return s != null && s == seed && state != null && state == net.iicosahedra.perplexity.item.ItemAnvilItem.STATE_ROLLING;
    }

    private static void clearAnvilComponents(net.minecraft.world.item.ItemStack stack) {
        if (stack == null) return;
        stack.set(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_STATE.value(), net.iicosahedra.perplexity.item.ItemAnvilItem.STATE_IDLE);
        stack.remove(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_RESULT.value());
        stack.remove(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_SEED.value());
        stack.remove(net.iicosahedra.perplexity.setup.Registration.COMPONENT_ANVIL_START_TICK.value());
    }
}
