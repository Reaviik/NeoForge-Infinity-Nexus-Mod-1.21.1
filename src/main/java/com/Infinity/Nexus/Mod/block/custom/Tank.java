package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.custom.common.CommonUpgrades;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.block.entity.TankBlockEntity;
import com.Infinity.Nexus.Mod.component.FluidStackComponent;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Tank extends BaseEntityBlock {
    public static final MapCodec<Tank> CODEC = simpleCodec(Tank::new);
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 1);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 16, 13);

    public Tank(Properties pProperties) {
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

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
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
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TankBlockEntity) {
                ((TankBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(player.getMainHandItem().is(Items.BUCKET.asItem())){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof TankBlockEntity tank) {
                tank.fillBucket(player.getMainHandItem(), player, level);
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
        }else{
            CommonUpgrades.setUpgrades(level, pos, player);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        TankBlockEntity entity =  (TankBlockEntity) pLevel.getBlockEntity(pPos);
        if(entity != null && !pLevel.isClientSide()){
            entity.setFluidAndUpgrade(pStack);
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TankBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.TANK_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        //TODO CAPABILITY
        if (stack.has(ModDataComponents.TANK_FLUID)) {
            FluidStackComponent tankComponent = stack.get(ModDataComponents.TANK_FLUID);
            FluidStack fluidStack = tankComponent.fluidStack();
            if (!stack.equals(FluidStack.EMPTY) && fluidStack != null) {
                boolean endless = fluidStack.getAmount() >= ConfigUtils.tank_capacity;
                components.add(Component.literal(fluidStack.getHoverName().getString() + (endless ? " (" + Component.translatable("tooltip.infinity_nexus_mod.tank_endless").getString() + ")" : " " + fluidStack.getAmount() + "mB/" + ConfigUtils.tank_capacity + "mB")));
            } else {
                components.add(Component.translatable("tooltip.infinity_nexus_mod.tank_empty").append(Component.literal(": 0mB/" + ConfigUtils.tank_capacity + "mB")));
            }
            if (ConfigUtils.tank_can_endless) {
                if (Screen.hasShiftDown()) {
                    components.add(Component.translatable("item.infinity_nexus.tank_description"));
                } else {
                    components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
                }
            }
        }
        super.appendHoverText(stack, context, components, flag);
    }
}