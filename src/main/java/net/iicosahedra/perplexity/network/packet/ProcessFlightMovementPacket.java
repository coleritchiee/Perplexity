package net.iicosahedra.perplexity.network.packet;

import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

public class ProcessFlightMovementPacket implements CustomPacketPayload {
    public final Vec3 v;

    public static final CustomPacketPayload.Type<ProcessFlightMovementPacket> TYPE = new CustomPacketPayload.Type<ProcessFlightMovementPacket>(ResourceLoc.create("process_flight_packet"));
    public static final StreamCodec<FriendlyByteBuf, ProcessFlightMovementPacket> STREAM_CODEC = CustomPacketPayload.codec(ProcessFlightMovementPacket::write, ProcessFlightMovementPacket::new);

    public ProcessFlightMovementPacket(FriendlyByteBuf friendlyByteBuf) {
        v = friendlyByteBuf.readVec3();
    }

    public ProcessFlightMovementPacket(Vec3 v) {
        this.v = v;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVec3(v);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
