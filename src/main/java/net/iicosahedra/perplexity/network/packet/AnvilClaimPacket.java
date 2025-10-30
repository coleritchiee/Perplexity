package net.iicosahedra.perplexity.network.packet;

import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

public record AnvilClaimPacket(InteractionHand hand) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLoc.create("anvil_claim");
    public static final Type<AnvilClaimPacket> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, AnvilClaimPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    p -> p.hand().ordinal(),
                    ord -> new AnvilClaimPacket(InteractionHand.values()[ord])
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


