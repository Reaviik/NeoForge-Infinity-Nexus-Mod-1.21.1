package com.Infinity.Nexus.Mod.networking.packet;

import com.Infinity.Nexus.Core.utils.GetResourceLocation;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.MobCrusherBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MobCrusherAreaC2SPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<MobCrusherAreaC2SPacket> TYPE =
            new Type<>(GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, "mob_crusher_area"));

    public static final StreamCodec<FriendlyByteBuf, MobCrusherAreaC2SPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            MobCrusherAreaC2SPacket::pos,
            MobCrusherAreaC2SPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MobCrusherAreaC2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player == null) return;

            ServerLevel level = player.serverLevel();
            if (level.getBlockEntity(packet.pos()) instanceof MobCrusherBlockEntity blockEntity) {
                blockEntity.shouldShowArea();
                blockEntity.setChanged();
                level.sendBlockUpdated(packet.pos(),
                        blockEntity.getBlockState(),
                        blockEntity.getBlockState(),
                        3);
            }
        });
    }
}