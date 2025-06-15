package me.emafire003.dev.beampass.networking;

import me.emafire003.dev.beampass.BeamPass;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.List;

public record SyncBlocksPayloadS2C(List<String> ids) implements CustomPayload {

    public static final Id<SyncBlocksPayloadS2C> ID = new Id<>(
            Identifier.of(BeamPass.MOD_ID, "sync_beampassable_blocks")
    );

    public static final PacketCodec<PacketByteBuf, SyncBlocksPayloadS2C> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING.collect(PacketCodecs.toList()), SyncBlocksPayloadS2C::ids,
            SyncBlocksPayloadS2C::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
