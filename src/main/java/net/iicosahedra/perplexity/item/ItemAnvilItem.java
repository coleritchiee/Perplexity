package net.iicosahedra.perplexity.item;

import net.iicosahedra.perplexity.network.packet.UseAnvilPacket;
import net.iicosahedra.perplexity.setup.Registration;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class ItemAnvilItem extends Item{
    public static final int STATE_IDLE = 0;
    public static final int STATE_ROLLING = 1;
    public static final int STATE_REVEALED = 2;

    public ItemAnvilItem() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            Integer state = stack.get(Registration.COMPONENT_ANVIL_STATE.value());
            if (state == null || state == STATE_IDLE) {
                PacketDistributor.sendToServer(new UseAnvilPacket(hand));
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }
            return InteractionResultHolder.fail(stack);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}


