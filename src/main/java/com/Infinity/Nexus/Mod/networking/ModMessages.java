package com.Infinity.Nexus.Mod.networking;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.networking.packet.AreaVisibilityS2CPacket;
import com.Infinity.Nexus.Mod.networking.packet.MobCrusherAreaC2SPacket;
import com.Infinity.Nexus.Mod.networking.packet.ToggleAreaC2SPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModMessages {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(InfinityNexusMod.MOD_ID)
                .versioned("1.0")
                .optional();

        // Registrar pacotes C2S
        registrar.playToServer(
                ToggleAreaC2SPacket.TYPE,
                ToggleAreaC2SPacket.STREAM_CODEC,
                ToggleAreaC2SPacket::handle
        );

        registrar.playToServer(
                MobCrusherAreaC2SPacket.TYPE,
                MobCrusherAreaC2SPacket.STREAM_CODEC,
                MobCrusherAreaC2SPacket::handle
        );

        // Registrar pacotes S2C (se necessário)
        registrar.playToClient(
                AreaVisibilityS2CPacket.TYPE,
                AreaVisibilityS2CPacket.STREAM_CODEC,
                AreaVisibilityS2CPacket::handle
        );
    }

    public static void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.sendToServer(packet);
    }

    public static void sendToPlayer(CustomPacketPayload packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }
}