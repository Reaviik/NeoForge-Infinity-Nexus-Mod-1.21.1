package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.custom.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = InfinityNexusMod.MOD_ID)
public class HammerAreaBreakUsage {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(final BlockEvent.BreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack mainHandItem = player.getMainHandItem();

        if (!(mainHandItem.getItem() instanceof HammerItem) || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        final BlockPos initialBlockPos = event.getPos();
        if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
            return;
        }

        processHammerBreak(mainHandItem, initialBlockPos, serverPlayer);
    }

    private static void processHammerBreak(ItemStack mainHandItem, BlockPos initialBlockPos, ServerPlayer serverPlayer) {
        final int baseRange = mainHandItem.getOrDefault(ModDataComponents.HAMMER_RANGE.get(), 0);
        final int effectiveRange = mainHandItem.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_HAMMER.get() ? baseRange + 2 : baseRange + 1;

        for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(effectiveRange, initialBlockPos, serverPlayer)) {
            if (pos == initialBlockPos || !mainHandItem.isCorrectToolForDrops(serverPlayer.level().getBlockState(pos))) {
                continue;
            }

            HARVESTED_BLOCKS.add(pos);
            serverPlayer.gameMode.destroyBlock(pos);
            double x = pos.getX()+0.5D;
            double y = pos.getY()+0.5D;
            double z = pos.getZ()+0.5D;
            serverPlayer.serverLevel().sendParticles(ParticleTypes.SCULK_SOUL, x, y, z, 4, 0, 0, 0, 0);
            HARVESTED_BLOCKS.remove(pos);
        }
    }
}