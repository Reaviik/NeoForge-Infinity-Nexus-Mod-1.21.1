package com.Infinity.Nexus.Mod.networking.packet;

import com.Infinity.Nexus.Core.utils.GetResourceLocation;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.MobCrusherBlockEntity;
import com.Infinity.Nexus.Mod.networking.ModMessages;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ToggleAreaC2SPacket(BlockPos pos, boolean showArea) implements CustomPacketPayload {
    public static final Type<ToggleAreaC2SPacket> TYPE =
            new Type<>(GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, "toggle_area"));

    public static final StreamCodec<FriendlyByteBuf, ToggleAreaC2SPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ToggleAreaC2SPacket::pos,
            ByteBufCodecs.BOOL,
            ToggleAreaC2SPacket::showArea,
            ToggleAreaC2SPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ToggleAreaC2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player == null) return;

            ServerLevel level = player.serverLevel();
            if (level.getBlockEntity(packet.pos()) instanceof MobCrusherBlockEntity blockEntity) {
                blockEntity.setShowArea(packet.showArea());
                blockEntity.setChanged();

                // Envia atualização para jogadores próximos
                for (ServerPlayer nearbyPlayer : level.players()) {
                    if (nearbyPlayer.distanceToSqr(packet.pos().getX(), packet.pos().getY(), packet.pos().getZ()) < 64 * 64) {
                        ModMessages.sendToPlayer(new AreaVisibilityS2CPacket(packet.pos(), packet.showArea()), nearbyPlayer);
                    }
                }

                level.sendBlockUpdated(packet.pos(),
                        blockEntity.getBlockState(),
                        blockEntity.getBlockState(),
                        3);
            }
        });
    }
}