package com.Infinity.Nexus.Mod.item.custom.orbs;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.custom.Orb;
import com.Infinity.Nexus.Mod.worldgen.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Resource extends Orb {
    private final int range;
    List<ResourceKey<Level>> dimensions = List.of(
            ModDimensions.EXPLORAR_LEVEL_KEY,
            Level.NETHER,
            Level.END
    );

    public Resource(Properties pProperties, int stage, int range) {
        super(pProperties, stage);
        this.range = range;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        if (player == null || level.isClientSide() || !player.isShiftKeyDown()) {
            return super.onItemUseFirst(stack, context);
        }

        if (!(level instanceof ServerLevel serverLevel && dimensions.contains(serverLevel.dimension()))) {
            player.sendSystemMessage(Component.translatable("message.infinity_nexus.not_in_explorar"));
            return super.onItemUseFirst(stack, context);
        }
        performAction(context, player);
        return super.onItemUseFirst(stack, context);
    }

    private void performAction(UseOnContext context, Player player) {
        ServerLevel serverLevel = (ServerLevel) context.getLevel();
        BlockPos center = context.getClickedPos();
        int minY = -63;
        int maxY = (int) player.getY();

        Queue<BlockPos> oresQueue = findOres(minY, maxY, center, serverLevel);
        if (oresQueue.isEmpty()) {
            player.sendSystemMessage(Component.translatable("message.infinity_nexus.no_ores_found"));
            return;
        }

        player.sendSystemMessage(Component.translatable("message.infinity_nexus.start_extraction"));
        player.getMainHandItem().shrink(1);
        processOres(oresQueue, serverLevel, player, (int) player.getY() + 2);
    }

    private void processOres(Queue<BlockPos> oresQueue, ServerLevel serverLevel, Player player, int startY) {
        while (!oresQueue.isEmpty()) {
            BlockPos orePos = oresQueue.poll();
            if (!serverLevel.isLoaded(orePos)) continue;

            BlockPos surfacePos = findSurfacePosAbovePlayer(orePos, startY, serverLevel);
            if (surfacePos == null) {
                return;
            }

            BlockState oreState = serverLevel.getBlockState(orePos);
            if (oreState.isAir()) {
                return;
            }
            if(player.getOffhandItem().is(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL.get())){
                ItemEntity oreItem = new ItemEntity(serverLevel, player.getX(), player.getY(), player.getZ(), new ItemStack(oreState.getBlock()));
                BlockState replace  = orePos.getY() >= 0 ? Blocks.STONE.defaultBlockState() : Blocks.DEEPSLATE.defaultBlockState();
                serverLevel.setBlock(orePos, replace, 3);
                serverLevel.addFreshEntity(oreItem);
                continue;
            }
            serverLevel.setBlock(surfacePos, oreState, 3);
            BlockState replace  = orePos.getY() >= 0 ? Blocks.STONE.defaultBlockState() : Blocks.DEEPSLATE.defaultBlockState();
            serverLevel.setBlock(orePos, replace, 3);
        }
        player.sendSystemMessage(Component.translatable("message.infinity_nexus.extraction_complete"));
    }

    private BlockPos findSurfacePosAbovePlayer(BlockPos orePos, int startY, ServerLevel serverLevel) {
        int maxHeight = serverLevel.getMaxBuildHeight();
        BlockPos surfacePos = new BlockPos(orePos.getX(), startY, orePos.getZ());

        while (surfacePos.getY() < maxHeight && !serverLevel.getBlockState(surfacePos).isAir()) {
            surfacePos = surfacePos.above();
        }
        return (surfacePos.getY() >= maxHeight) ? null : surfacePos;
    }

    private Queue<BlockPos> findOres(int minY, int maxY, BlockPos center, ServerLevel serverLevel) {
        Queue<BlockPos> oresQueue = new LinkedList<>();
        for (int x = -this.range; x <= this.range; x++) {
            for (int z = -this.range; z <= this.range; z++) {
                for (int y = minY; y <= maxY; y++) {
                    BlockPos pos = new BlockPos(center.getX() + x, y, center.getZ() + z);
                    BlockState state = serverLevel.getBlockState(pos);
                    List<TagKey<Block>> tags = state.getTags().toList();
                    if (tags.toString().contains("c:ores")) {
                        oresQueue.add(pos);
                    }
                }
            }
        }
        return oresQueue;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.resource_orb_description"));
        tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.orb_range").append(Component.literal(" " + this.range)));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
