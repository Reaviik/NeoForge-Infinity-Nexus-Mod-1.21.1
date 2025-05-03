package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.block.entity.RecyclerBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


public class Recycler extends BaseMachineBlock {
    public static final MapCodec<BaseEntityBlock> CODEC = simpleCodec(Recycler::new);
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public Recycler(Properties pProperties) {
        super(pProperties, CODEC);
    }


    @Override
    protected void dropContents(Level level, BlockPos pos, BlockEntity blockEntity) {
        if (blockEntity instanceof RecyclerBlockEntity) {
            ((RecyclerBlockEntity) blockEntity).drops();
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RecyclerBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.RECYCLER_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}