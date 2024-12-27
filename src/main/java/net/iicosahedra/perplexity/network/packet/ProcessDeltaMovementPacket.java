package net.iicosahedra.perplexity.network.packet;

import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

public class ProcessDeltaMovementPacket implements CustomPacketPayload {
    public final Vec3 v;

    public static final CustomPacketPayload.Type<ProcessDeltaMovementPacket> TYPE = new CustomPacketPayload.Type<ProcessDeltaMovementPacket>(ResourceLoc.create("process_movement_packet"));
    public static final StreamCodec<FriendlyByteBuf, ProcessDeltaMovementPacket> STREAM_CODEC = CustomPacketPayload.codec(ProcessDeltaMovementPacket::write, ProcessDeltaMovementPacket::new);

    public ProcessDeltaMovementPacket(FriendlyByteBuf friendlyByteBuf) {
        v = friendlyByteBuf.readVec3();
    }

    public ProcessDeltaMovementPacket(Vec3 v) {
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
