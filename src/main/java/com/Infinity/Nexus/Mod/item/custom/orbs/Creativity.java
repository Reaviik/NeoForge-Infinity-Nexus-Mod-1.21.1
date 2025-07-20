package com.Infinity.Nexus.Mod.item.custom.orbs;

import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.item.custom.Orb;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.*;

public class Creativity extends Orb {
    private static final int[] RADIUS_BY_TIER = {32, 64, 128};
    private static int MAX_DURABILITY = 250;
    private Mode currentMode = Mode.REPLACE_SIMILAR;
    private final int tier;

    public Creativity(Properties pProperties, int tier) {
        super(pProperties, tier);
        this.tier = tier;
        MAX_DURABILITY = 256 * tier;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();

        if(level.isClientSide() || pContext.getHand() != InteractionHand.MAIN_HAND){
            return InteractionResult.FAIL;
        }

        if(Objects.requireNonNull(pContext.getPlayer()).isShiftKeyDown()){
            switchMode();
        }

        BlockPos clickedPos = pContext.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }

        if (clickedState.isAir()) {
            return InteractionResult.FAIL;
        }

        int radius = RADIUS_BY_TIER[tier];
        ItemStack orb = pContext.getItemInHand();

        if (orb.getOrDefault(ModDataComponents.BASIC_INT, MAX_DURABILITY) <= 0) {
            return InteractionResult.FAIL;
        }

        switch(currentMode) {
            case REPLACE_SIMILAR:
                replaceSimilarBlocks(level, clickedPos, radius, serverPlayer);
                break;
            case REPLACE_SIMILAR_WALL:
                replaceSimilarBlocksWall(level, clickedPos, radius, serverPlayer);
                break;
        }

        consumeDurability(orb);
        return InteractionResult.SUCCESS;
    }


    private void replaceSimilarBlocks(Level level, BlockPos startPos, int maxBlocks, ServerPlayer player) {
        ItemStack offHandItem = player.getOffhandItem();

        if (!(offHandItem.getItem() instanceof BlockItem blockItem)) {
            return;
        }

        BlockState targetState = level.getBlockState(startPos);
        BlockState replaceState = blockItem.getBlock().defaultBlockState();

        if (targetState.getBlock() == replaceState.getBlock()) {
            return;
        }

        Set<BlockPos> processed = new HashSet<>();
        Queue<BlockPos> toProcess = new LinkedList<>();
        toProcess.add(startPos);
        int replacedCount = 0;

        while (!toProcess.isEmpty() && replacedCount < maxBlocks && offHandItem.getCount() > 0) {
            BlockPos currentPos = toProcess.poll();

            if (processed.contains(currentPos)) continue;
            processed.add(currentPos);

            BlockState currentState = level.getBlockState(currentPos);
            if (!currentState.is(targetState.getBlock())) continue;

            if (canHarvestBlock(player, currentState, level, currentPos) &&
                    tryHarvestBlock(player, level, currentPos) &&
                    canPlaceBlock(replaceState, level, currentPos)) {

                level.setBlock(currentPos, replaceState, 3);
                offHandItem.shrink(1);
                replacedCount++;

                for (Direction direction : Direction.values()) {
                    BlockPos adjacentPos = currentPos.relative(direction);
                    if (!processed.contains(adjacentPos) && level.isLoaded(adjacentPos)) {
                        toProcess.add(adjacentPos);
                    }
                }
            }
        }
    }

    private void replaceSimilarBlocksWall(Level level, BlockPos startPos, int maxBlocks, ServerPlayer player) {
        ItemStack offHandItem = player.getOffhandItem();

        // Verifica se há um BlockItem na segunda mão
        if (!(offHandItem.getItem() instanceof BlockItem blockItem)) {
            return;
        }

        BlockState targetState = level.getBlockState(startPos);
        BlockState replaceState = blockItem.getBlock().defaultBlockState();

        // Se o bloco inicial já for igual ao de substituição, cancela
        if (targetState.getBlock() == replaceState.getBlock()) {
            return;
        }

        Set<BlockPos> processed = new HashSet<>();
        Queue<BlockPos> toProcess = new LinkedList<>();
        toProcess.add(startPos);
        int replacedCount = 0;

        // Obtém a direção que o jogador está olhando (aproximada)
        Vec3 lookAngle = player.getLookAngle();
        Direction primaryDirection = Direction.getNearest(lookAngle.x, lookAngle.y, lookAngle.z);

        while (!toProcess.isEmpty() && replacedCount < maxBlocks && offHandItem.getCount() > 0) {
            BlockPos currentPos = toProcess.poll();

            if (processed.contains(currentPos)) continue;
            processed.add(currentPos);

            BlockState currentState = level.getBlockState(currentPos);
            if (!currentState.is(targetState.getBlock())) continue;

            if (isBlockExposed(level, currentPos)) {
                if (canHarvestBlock(player, currentState, level, currentPos) &&
                        tryHarvestBlock(player, level, currentPos) &&
                        canPlaceBlock(replaceState, level, currentPos)) {

                    level.setBlock(currentPos, replaceState, 3);
                    offHandItem.shrink(1);
                    replacedCount++;

                    addAdjacentPositions(toProcess, processed, currentPos, primaryDirection, level);
                }
            }
        }
    }

    private boolean isBlockExposed(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacentPos);

            if (adjacentState.isAir() || adjacentState.canBeReplaced()) {
                return true;
            }
        }
        return false;
    }

    private void addAdjacentPositions(Queue<BlockPos> queue, Set<BlockPos> processed, BlockPos currentPos,
                                      Direction primaryDirection, Level level) {
        BlockPos primaryPos = currentPos.relative(primaryDirection);
        if (!processed.contains(primaryPos) && level.isLoaded(primaryPos)) {
            queue.add(primaryPos);
        }

        for (Direction direction : Direction.values()) {
            if (direction != primaryDirection) {
                BlockPos adjacentPos = currentPos.relative(direction);
                if (!processed.contains(adjacentPos) && level.isLoaded(adjacentPos)) {
                    queue.add(adjacentPos);
                }
            }
        }
    }


    private boolean canHarvestBlock(Player player, BlockState state, Level level, BlockPos pos) {
        return state.getDestroySpeed(level, pos) >= 0 && player.hasCorrectToolForDrops(state);
    }

    private boolean tryHarvestBlock(ServerPlayer player, Level level, BlockPos pos) {
        return player.gameMode.destroyBlock(pos);
    }

    private boolean canPlaceBlock(BlockState newState, Level level, BlockPos pos) {
        BlockState current = level.getBlockState(pos);
        return current.isAir() || current.canBeReplaced();
    }


    public void switchMode() {
        currentMode = Mode.values()[(currentMode.ordinal() + 1) % Mode.values().length];
        System.out.println(currentMode);
    }

    public enum Mode {
        REPLACE_SIMILAR,
        REPLACE_SIMILAR_WALL
    }

    private void consumeDurability(ItemStack orb) {
        int durability = orb.getOrDefault(ModDataComponents.BASIC_INT, MAX_DURABILITY);
        orb.set(ModDataComponents.BASIC_INT, Math.max(0, durability - 1));
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 64;
    }
}