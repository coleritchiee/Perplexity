package net.iicosahedra.perplexity.network.packet;

import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record AnvilRollPacket(int seed, List<ResourceLocation> displayList, int revealIndex, int durationTicks) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLoc.create("anvil_roll");
    public static final Type<AnvilRollPacket> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, AnvilRollPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, AnvilRollPacket::seed,
                    ByteBufCodecs.collection(java.util.ArrayList::new, ResourceLocation.STREAM_CODEC), AnvilRollPacket::displayList,
                    ByteBufCodecs.VAR_INT, AnvilRollPacket::revealIndex,
                    ByteBufCodecs.VAR_INT, AnvilRollPacket::durationTicks,
                    AnvilRollPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


