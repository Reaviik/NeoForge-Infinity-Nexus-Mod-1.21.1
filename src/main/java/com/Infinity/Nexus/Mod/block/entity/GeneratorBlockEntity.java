package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.InfinityNexusCore;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.EnergyUtils;
import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Core.utils.SoundUtils;
import com.Infinity.Nexus.Mod.block.custom.Generator;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.screen.generator.GeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlockEntity extends BaseMenuProviderBlockEntity{
    private int fuel = 0;
    private int ENERGY_TRANSFER = 30;

    public GeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.GENERATOR_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.generator"),
                new int[] {0},
                0,
                new int[] {1, 2, 3, 4},
                5,
                ConfigUtils.generator_energy_storage_capacity,
                ConfigUtils.generator_energy_transfer_rate

        );
    }

    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getBurnTime(null) >= 1;
                case 1,2,3,4 -> ModUtils.isUpgrade(stack);
                case 5 -> ModUtils.isComponent(stack);

                default -> super.isItemValid(slot, stack);
            };
        }
    };

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.generator").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GeneratorMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("generator.progress", progress);
        pTag.putInt("generator.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag , registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("generator.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("generator.energy"));
    }

    public void drops() {
        dropContents(level, this.worldPosition, this.itemHandler);
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    public IEnergyStorage getEnergyStorage(Direction direction) {
        return ENERGY_STORAGE;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        pLevel.setBlock(pPos, pState.setValue(Generator.LIT, machineLevel), 3);

        if (isRedstonePowered(pPos)) {
            return;
        }

        distributeEnergy();
        if(getMachineLevel() <= 0){
            return;
        }

        if(!canInsertEnergy(this)){
            return;
        }

        if (fuel <= 0){
            if(!hasFuel(this)){
                return;
            }
            removeFuel();
        }
        pLevel.setBlock(pPos, pState.setValue(Generator.LIT, machineLevel+9), 3);
        insertEnergy();
        increaseCraftingProgress();
        decreaseFuel();
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            resetProgress();
        }
    }

    private void setMaxTransfer(int machineLevel) {
        int energy = (machineLevel + (ModUtils.getStrength(this.itemHandler, UPGRADE_SLOTS))) * 30;
        ENERGY_TRANSFER = energy <= 0 ? 30 : energy;
    }

    private void distributeEnergy() {
        if (level == null || level.isClientSide() || ENERGY_STORAGE.getEnergyStored() <= 0) {
            return;
        }
        for (Direction direction : Direction.values()) {
            if (ENERGY_STORAGE.getEnergyStored() <= 0) break;

            BlockPos neighborPos = getBlockPos().relative(direction);
            BlockEntity neighborBlockEntity = level.getBlockEntity(neighborPos);

            if (neighborBlockEntity == null || neighborBlockEntity instanceof SolarBlockEntity || neighborBlockEntity instanceof GeneratorBlockEntity) {
                continue;
            }

            try {
                IEnergyStorage neighborStorage = EnergyUtils.getBlockCapabilityEnergyHandler(level, neighborPos, direction);
                if (neighborStorage == null) continue;

                EnergyUtils.transferEnergy(ENERGY_STORAGE, neighborStorage, ENERGY_TRANSFER);
            } catch (Exception e) {
                InfinityNexusCore.LOGGER.error("Failed to transfer energy to neighbor at {}: {}", neighborPos, e.getMessage());
            }
        }
    }


    private void decreaseFuel() {
        fuel -= ((getMachineLevel() + 1) + (ModUtils.getSpeed(this.itemHandler, UPGRADE_SLOTS)));
    }

    private void removeFuel() {
        if(hasFuel(this)){
            ItemStackHandlerUtils.extractItem(INPUT_SLOT[0], 1, false, itemHandler);
        }
    }

    private void insertEnergy() {
        try {
            EnergyUtils.insertEnergy(ENERGY_STORAGE, ENERGY_TRANSFER, false);
        } catch (Exception e) {
            System.out.println("&f[INM&f]&4: Failed to find energy cap.");
            e.printStackTrace();
        }
    }

    private boolean canInsertEnergy(GeneratorBlockEntity generatorBlockEntity) {
        try {
            return (generatorBlockEntity.ENERGY_STORAGE.getEnergyStored() + ENERGY_TRANSFER) < generatorBlockEntity.ENERGY_STORAGE.getMaxEnergyStored();
        } catch (Exception e) {
            System.out.println("&f[INM&f]&4: Failed to find energy cap.");
            e.printStackTrace();
            return false;
        }
    }

    private boolean hasFuel(GeneratorBlockEntity generatorBlockEntity) {
        ItemStack fuel = generatorBlockEntity.itemHandler.getStackInSlot(INPUT_SLOT[0]);
        int burnTime = fuel.getBurnTime(null);
        setMaxProgress(burnTime);
        this.fuel = burnTime;
        return burnTime > 0;
    }


    private void setMaxProgress(int burnTime) {
        maxProgress = burnTime;
    }

    @Override
    protected void resetProgress() {
        if(ModUtils.getMuffler(itemHandler, UPGRADE_SLOTS) < 1){
            SoundUtils.playSound(level, worldPosition, SoundSource.BLOCKS, SoundEvents.FIRE_EXTINGUISH, 0.1f, 1.0f);
        }
        progress = 0;
    }


    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }

    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
}