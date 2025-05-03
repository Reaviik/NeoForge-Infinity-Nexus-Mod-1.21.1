package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.custom.common.CommonUpgrades;
import com.Infinity.Nexus.Mod.block.entity.CompactorBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Compactor extends BaseEntityBlock {
    public static final MapCodec<Compactor> CODEC = simpleCodec(Compactor::new);
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 1);
    public static final VoxelShape SHAPE = Block.box(-16, 0, -16, 32, 16, 32);

    public Compactor(Properties pProperties) {
        super(pProperties);
    }
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        CommonUpgrades.setUpgrades(level, pos, player);
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }



    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            boolean cancel = false;
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && z == 0) continue;

                    BlockPos adjacentPos = pPos.offset(x, 0, z);
                    BlockState adjacentState = pLevel.getBlockState(adjacentPos);
                    if (!adjacentState.isAir()) {
                        cancel = true;
                        break;
                    }
                }
                if (cancel) break;
            }
            if (!cancel) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && z == 0) continue;
                        BlockPos adjacentPos = pPos.offset(x, 0, z);
                        pLevel.setBlock(adjacentPos, Blocks.BARRIER.defaultBlockState(), 3);
                    }
                }
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                BlockPos adjacentPos = pPos.offset(x, 0, z);
                BlockState adjacentState = pLevel.getBlockState(adjacentPos);
                if (adjacentState.getBlock() == Blocks.BARRIER) {
                    pLevel.setBlock(adjacentPos, Blocks.AIR.defaultBlockState(), 3);
                    //pLevel.destroyBlock(pPos, false);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CompactorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.COMPACTOR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}