package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.entity.BatteryBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Battery extends BaseMachineBlock {
    public static final MapCodec<BaseEntityBlock> CODEC = simpleCodec(Battery::new);
    int energy = 0;
    public Battery(Properties properties) {
        super(properties, CODEC, null, null, null);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BatteryBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) return null;
        return createTickerHelper(blockEntityType, ModBlockEntities.BATTERY_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    protected void dropContents(Level level, BlockPos pos, BlockEntity blockEntity) {
        if (blockEntity instanceof BatteryBlockEntity) {
            ((BatteryBlockEntity) blockEntity).drops();
        }
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BatteryBlockEntity entity =  (BatteryBlockEntity) pLevel.getBlockEntity(pPos);
        if(entity != null && !pLevel.isClientSide()){
            entity.setEnergyAndComponent(pStack);
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
            if(!Screen.hasShiftDown()){
                components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
            }else{
                String component = "Empty";
                if(stack.has(ModDataComponents.ITEM_STACK)){
                    component = stack.get(ModDataComponents.ITEM_STACK.value()).itemStack().getDisplayName().getString();
                }
                components.add(Component.translatable("item.infinity_nexus.battery_description"));
                components.add(Component.translatable("Energy: " + stack.getOrDefault(ModDataComponents.ENERGY, 0)));
                components.add(Component.translatable("Component: " + component));
            }
        super.appendHoverText(stack, context, components, flag);
    }
}
