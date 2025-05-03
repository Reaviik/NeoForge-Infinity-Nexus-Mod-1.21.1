package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.utils.FluidUtils;
import com.Infinity.Nexus.Core.utils.SendParticlesPath;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class TranslocatorFluidBlockEntity extends TranslocatorBlockEntityBase {

    private static final int CAPACITY = 10000;
    private static final int MAX_TRANSFER = 10000;
    private final FluidTank FLUID_STORAGE = createFluidStorage();
    public TranslocatorFluidBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TRASLOCATOR_FLUID_BE.get(), pPos, pBlockState);
    }

    private FluidTank createFluidStorage() {
        return new FluidTank(CAPACITY) {
            @Override
            public void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }
            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("translocator.progress", progress);
        pTag.putInt("translocator.max_progress", maxProgress);
        pTag.putInt("translocator.mode", mode);
        pTag.putInt("translocator.step", step);
        pTag.putString("translocator.filter", filter == null ? "" : String.join(";", filter));
        pTag.putInt("translocator.filterIndex", filterIndex);
        pTag = FLUID_STORAGE.writeToNBT(registries, pTag);

        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("translocator.progress");
        maxProgress = pTag.getInt("translocator.max_progress");
        mode = pTag.getInt("translocator.mode");
        step = pTag.getInt("translocator.step");
        filter = pTag.getString("translocator.filter").isEmpty() ? new String[0] : pTag.getString("translocator.filter").split(";");
        filterIndex = pTag.getInt("translocator.filterIndex");
        FLUID_STORAGE.readFromNBT(registries, pTag);
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        if (mode == 0) {
            if(hasProgressFinished()) {
                resetProgress();
                depositFluid(pLevel, pPos);
            }else{
                increaseProgress();
            }
        } else {
            if(canSend()) {
                if(hasProgressFinished()) {
                    resetProgress();
                    sendFluid(pLevel);
                    upgradeStep();
                }
                increaseProgress();
            } else {
                if(hasProgressFinished()) {
                    resetProgress();
                    pullFluid(pLevel, pPos, pState);
                }
                increaseProgress();
            }
        }
        updateLit(pLevel, pState);
        setChanged(pLevel, pPos, pState);
    }

    @Override
    protected boolean canSend() {
        return FLUID_STORAGE.getFluidAmount() > 0;
    }

    private void pullFluid(Level pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity entity = getInventoryPos(pLevel, pPos, pState);
        if (entity == null) return;

        IFluidHandler sourceHandler = FluidUtils.getBlockFluidHandler(entity.getBlockPos(), pLevel);
        if (sourceHandler == null) return;
        if(sourceHandler.getFluidInTank(0).isEmpty()) return;
        FluidUtils.transferFromTankToTank(sourceHandler, FLUID_STORAGE, MAX_TRANSFER);
    }

    private void sendFluid(Level pLevel) {
        BlockPos targetPos = getDestination();
        BlockEntity entity = pLevel.getBlockEntity(targetPos);
        if (entity == null) return;

        IFluidHandler targetHandler = FluidUtils.getBlockFluidHandler(entity.getBlockPos(), pLevel);
        if (targetHandler == null) return;

        SendParticlesPath.makePath((ServerLevel) this.getLevel(), ParticleTypes.BUBBLE, worldPosition, entity.getBlockPos(), 0.5D, 0.5D, 0.5D);

        if(entity instanceof TranslocatorFluidBlockEntity translocator){
            translocator.receiveFluid(FLUID_STORAGE);
            return;
        }

        FluidUtils.transferFromTankToTank(FLUID_STORAGE, targetHandler, MAX_TRANSFER);
    }

    private void receiveFluid(IFluidHandler fluidHandler) {
        FluidUtils.transferFromTankToTank(fluidHandler, FLUID_STORAGE, MAX_TRANSFER);
        progress = maxProgress - ConfigUtils.translocator_skip_progress;
    }

    private void depositFluid(Level pLevel, BlockPos pPos) {
        BlockEntity entity = getInventoryPos(pLevel, pPos, getBlockState());
        if (entity == null) return;

        IFluidHandler targetHandler = FluidUtils.getBlockFluidHandler(entity.getBlockPos(), pLevel);
        if (targetHandler == null) return;
        FluidUtils.transferFromTankToTank(FLUID_STORAGE, targetHandler, MAX_TRANSFER);
    }

    public IFluidHandler getFluidHandler(Direction direction) {
        return FLUID_STORAGE;
    }

    protected void updateLit(Level level, BlockState state) {
        int lit = 6;
        int step = maxProgress / 5;

        if (mode == 1) {
            if (canSend()) {
                lit = (progress >= 4 * step) ? 5 : (progress >= 3 * step) ? 4 : (progress >= 2 * step) ? 3 : (progress >= step) ? 2 : 1;
            }
        } else if (mode == 0) {
            if (canSend()) {
                lit = (progress <= step) ? 5 : (progress <= 2 * step) ? 4 : (progress <= 3 * step) ? 3 : (progress <= 4 * step) ? 2 : 1;
            }
        }

        setLit(level, state, lit);
    }

}
