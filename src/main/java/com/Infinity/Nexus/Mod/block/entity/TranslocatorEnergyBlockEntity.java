package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.utils.EnergyUtils;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Core.utils.SendParticlesPath;
import com.Infinity.Nexus.Mod.block.custom.TranslocatorEnergy;
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
import net.neoforged.neoforge.energy.IEnergyStorage;

public class TranslocatorEnergyBlockEntity extends TranslocatorBlockEntityBase {

    private static final int CAPACITY = 1240000;
    private static final int MAX_TRANSFER = 512000;
    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();

    public TranslocatorEnergyBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TRASLOCATOR_ENERGY_BE.get(), pPos, pBlockState);
    }
    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(CAPACITY, MAX_TRANSFER) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
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
        pTag.putInt("translocator.energy", ENERGY_STORAGE.getEnergyStored());

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
        ENERGY_STORAGE.setEnergy(pTag.getInt("translocator.energy"));
    }
    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        if (mode == 0) {
            if(hasProgressFinished()) {
                resetProgress();
                depositEnergy(pLevel, pPos);
            }else{
                increaseProgress();
            }
        } else {
            if(canSend()) {
                if(hasProgressFinished()) {
                    resetProgress();
                    sendEnergy(pLevel);
                    upgradeStep();
                }
                increaseProgress();
            } else {
                if(hasProgressFinished()) {
                    resetProgress();
                    pullEnergy(pLevel, pPos, pState);
                }
                increaseProgress();
            }
        }
        updateLit(pLevel, pState);
        setChanged(pLevel, pPos, pState);
    }

    @Override
    protected boolean canSend() {
        return ENERGY_STORAGE.getEnergyStored() > 0 && !itemHandler.getStackInSlot(UPGRADE_SLOT).isEmpty();
    }

    private void pullEnergy(Level pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity entity = getInventoryPos(pLevel, pPos, pState);
        if (entity == null) return;

        IEnergyStorage sourceHandler = EnergyUtils.getBlockCapabilityEnergyHandler(pLevel, entity.getBlockPos(), getBlockState().getValue(TranslocatorEnergy.FACING).getOpposite());
        if (sourceHandler == null) return;

        EnergyUtils.transferEnergy(sourceHandler, ENERGY_STORAGE, MAX_TRANSFER);
    }

    private void sendEnergy(Level pLevel) {
        BlockPos targetPos = getDestination();
        BlockEntity entity = pLevel.getBlockEntity(targetPos);
        if (entity == null) return;

        IEnergyStorage targetHandler = EnergyUtils.getBlockCapabilityEnergyHandler(pLevel, entity.getBlockPos(), null);
        if (targetHandler == null) return;

        SendParticlesPath.makePath((ServerLevel) this.getLevel(), ParticleTypes.ELECTRIC_SPARK, worldPosition, entity.getBlockPos(), 0.5D, 0.5D, 0.5D);

        if(entity instanceof TranslocatorEnergyBlockEntity translocator){
            translocator.receiveEnergy(ENERGY_STORAGE);
            return;
        }

        EnergyUtils.transferEnergy(ENERGY_STORAGE, targetHandler, MAX_TRANSFER);
    }

    private void receiveEnergy(ModEnergyStorage energyStorage) {
        EnergyUtils.transferEnergy(energyStorage, ENERGY_STORAGE, MAX_TRANSFER);
        progress = maxProgress - ConfigUtils.translocator_skip_progress;
    }

    private void depositEnergy(Level pLevel, BlockPos pPos) {
        BlockEntity entity = getInventoryPos(pLevel, pPos, getBlockState());
        if (entity == null) return;

        IEnergyStorage targetHandler = EnergyUtils.getBlockCapabilityEnergyHandler(pLevel, entity.getBlockPos(), getBlockState().getValue(TranslocatorEnergy.FACING).getOpposite());
        if (targetHandler == null) return;

        EnergyUtils.transferEnergy(ENERGY_STORAGE, targetHandler, MAX_TRANSFER);
    }

    public IEnergyStorage getEnergyStorage(Direction direction) {
        return ENERGY_STORAGE;
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
