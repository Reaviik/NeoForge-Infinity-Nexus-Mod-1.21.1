package com.Infinity.Nexus.Mod.block.custom.pedestals;

import com.Infinity.Nexus.Mod.block.entity.pedestals.ResourcePedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ResourcePedestal extends BasePedestal {

    public ResourcePedestal(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        BlockState state = pState.setValue(BasePedestal.WORK, false);
        return new ResourcePedestalBlockEntity(pPos, state);
    }
}
