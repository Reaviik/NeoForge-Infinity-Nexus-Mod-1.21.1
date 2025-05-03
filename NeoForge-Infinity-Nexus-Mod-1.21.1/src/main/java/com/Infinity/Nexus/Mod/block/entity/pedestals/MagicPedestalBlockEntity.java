package com.Infinity.Nexus.Mod.block.entity.pedestals;


import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MagicPedestalBlockEntity extends BasePedestalBlockEntity {
    public MagicPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGIC_PEDESTAL_BE.get(), pos, state);
    }
}