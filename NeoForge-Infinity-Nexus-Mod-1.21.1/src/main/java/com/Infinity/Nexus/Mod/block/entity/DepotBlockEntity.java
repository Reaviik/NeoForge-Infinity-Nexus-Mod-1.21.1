package com.Infinity.Nexus.Mod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;

public class DepotBlockEntity extends DepotBlockEntityBase{
    public DepotBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DEPOT_BE.get(), pPos, pBlockState);
    }

    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
}