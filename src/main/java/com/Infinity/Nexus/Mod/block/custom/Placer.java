package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.block.entity.PlacerBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Placer extends BaseMachineBlock {
    public static final MapCodec<BaseEntityBlock> CODEC = simpleCodec(Placer::new);
    public static IntegerProperty ROT = IntegerProperty.create("rot", 0, 3);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public Placer(Properties pProperties) {
        super(pProperties, CODEC, null, null, null);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void dropContents(Level level, BlockPos pos, BlockEntity blockEntity) {
        if (blockEntity instanceof PlacerBlockEntity) {
            ((PlacerBlockEntity) blockEntity).drops();
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if(!Objects.requireNonNull(pContext.getPlayer()).isShiftKeyDown()) {
            int dir = switch (Objects.requireNonNull(pContext.getPlayer()).getDirection()) {
                case WEST -> 0;
                case SOUTH -> 1;
                case EAST -> 2;
                default -> 3;
            };
            if (pContext.getNearestLookingDirection().getOpposite().getAxis().isHorizontal()) {
                return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite()).setValue(ROT, 0);
            } else {
                return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection()).setValue(ROT, dir);
            }
        }else{
            int dir = switch (Objects.requireNonNull(pContext.getPlayer()).getDirection()) {
                case WEST -> 2;
                case SOUTH -> 3;
                case EAST -> 0;
                default -> 1;
            };
            if (pContext.getNearestLookingDirection().getOpposite().getAxis().isHorizontal()) {
                return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection()).setValue(ROT, 0);
            } else {
                return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite()).setValue(ROT, dir);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT, ROT);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PlacerBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PLACER_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
    @Override
    protected String[] getDescription() {
        return new String[]{
                Component.translatable("item.infinity_nexus.placer_description").getString()
        };
    }
}