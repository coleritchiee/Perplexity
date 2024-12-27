package net.iicosahedra.perplexity.network.packet;

import io.netty.buffer.ByteBuf;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record AbilityUsePacket(int slot) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AbilityUsePacket> TYPE = new CustomPacketPayload.Type<AbilityUsePacket>(ResourceLoc.create("ability_use_packet"));

    public static final StreamCodec<ByteBuf, AbilityUsePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            AbilityUsePacket::slot,
            AbilityUsePacket::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
