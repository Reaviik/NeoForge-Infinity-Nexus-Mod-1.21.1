package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.entity.TranslocatorBlockEntityBase;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class TranslocatorBase extends BaseEntityBlock {
    public static MapCodec<TranslocatorBase> CODEC;
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 6);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static String description = "item.infinity_nexus.translocator_description";
    private static final VoxelShape CEILING_AABB  = Stream.of(
            Shapes.box(0.3125, 0.9375, 0.3125, 0.6875, 1, 0.6875),
            Shapes.box(0.40625, 0.53125, 0.40625, 0.59375, 0.9375, 0.59375)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    private static final VoxelShape FLOOR_AABB = Stream.of(
            Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875),
            Shapes.box(0.40625, 0.0625, 0.40625, 0.59375, 0.46875, 0.59375)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    private static final VoxelShape NORTH_AABB =  Stream.of(
            Shapes.box(0.3125, 0.3125, 0.9375, 0.6875, 0.6875, 1),
            Shapes.box(0.40625, 0.40625, 0.53125, 0.59375, 0.59375, 0.9375)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    private static final VoxelShape SOUTH_AABB = Stream.of(
            Shapes.box(0.3125, 0.3125, 0, 0.6875, 0.6875, 0.0625),
            Shapes.box(0.40625, 0.40625, 0.0625, 0.59375, 0.59375, 0.46875)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    private static final VoxelShape WEST_AABB = Stream.of(
            Shapes.box(0.9375, 0.3125, 0.3125, 1, 0.6875, 0.6875),
            Shapes.box(0.53125, 0.40625, 0.40625, 0.9375, 0.59375, 0.59375)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    private static final VoxelShape EAST_AABB = Stream.of(
            Shapes.box(0, 0.3125, 0.3125, 0.0625, 0.6875, 0.6875),
            Shapes.box(0.0625, 0.40625, 0.40625, 0.46875, 0.59375, 0.59375)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));

    public TranslocatorBase(Properties pProperties, String pDescription, MapCodec<TranslocatorBase> codec) {
        super(pProperties);
        TranslocatorBase.description = pDescription;
        CODEC = codec;
    }
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getClickedFace());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }



    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        return switch (pState.getValue(FACING)) {
            case DOWN -> CEILING_AABB;
            case NORTH -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            case EAST -> EAST_AABB;
            default -> FLOOR_AABB;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TranslocatorBlockEntityBase) {
                ((TranslocatorBlockEntityBase) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TranslocatorBlockEntityBase translocator) {
                translocator.toggleMode(player.getItemInHand(hand).copy(), player, player.isShiftKeyDown(), pos);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable(description));
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }
        super.appendHoverText(stack, context, components, flag);
    }

}