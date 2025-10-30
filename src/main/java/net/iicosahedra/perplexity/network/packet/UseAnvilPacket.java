package net.iicosahedra.perplexity.network.packet;

import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.codec.StreamCodec;

public record UseAnvilPacket(InteractionHand hand) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLoc.create("use_anvil");
    public static final Type<UseAnvilPacket> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, UseAnvilPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    p -> p.hand().ordinal(),
                    ord -> new UseAnvilPacket(InteractionHand.values()[ord])
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


