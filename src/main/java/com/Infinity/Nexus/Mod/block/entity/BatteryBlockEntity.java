package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.InfinityNexusCore;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.*;
import com.Infinity.Nexus.Mod.block.custom.Battery;
import com.Infinity.Nexus.Mod.component.ItemStackComponent;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.screen.battery.BatteryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BatteryBlockEntity extends BaseMenuProviderBlockEntity {

    protected ModEnergyStorage BATTERY_ENERGY_STORAGE = this.createEnergyStorage();
    public BatteryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.BATTERY_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.battery"),
                new int[] {0},
                1,
                new int[] {2,3,4,5},
                6,
                ConfigUtils.battery_energy_storage_capacity,  //540000000,
                ConfigUtils.battery_energy_transfer_rate  //512000,
        );
    }
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0, 1 -> stack.getCapability(Capabilities.EnergyStorage.ITEM).canReceive();
                case 2, 3, 4, 5 -> ModUtils.isUpgrade(stack);
                case 6 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    @Override
    protected ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(ENERGY_CAPACITY, ENERGY_TRANSFER) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                if (level != null) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
                }
            }

            @Override
            public int getMaxEnergyStored() {
                int machineLevel = getMachineLevel();
                return (int)(ENERGY_CAPACITY * Math.pow((machineLevel + 1)/10.0, 1.5));
            }

            @Override
            public int getEnergyStored() {
                int energy = Math.min(super.getEnergyStored(), this.getMaxEnergyStored());
                setEnergy(energy);
                return energy;
            }
        };
    }
    public void drops() {
        ItemStack stack = new ItemStack(this.getBlockState().getBlock().asItem());
        stack = storageComponentAndEnergy(stack, BATTERY_ENERGY_STORAGE, itemHandler, COMPONENT_SLOT);
        ItemEntity battery = new ItemEntity(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), stack);
        this.level.addFreshEntity(battery);

        dropContents(level, this.worldPosition, this.itemHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", this.itemHandler.serializeNBT(registries));
        pTag.putInt("battery.progress", progress);
        pTag.putInt("battery.energy", this.BATTERY_ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag , registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        this.itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("battery.progress");
        this.BATTERY_ENERGY_STORAGE.setEnergy(pTag.getInt("battery.energy"));
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.battery").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BatteryMenu(pContainerId, pPlayerInventory, this, this.data, this.itemHandler);
    }
    public IItemHandler getItemHandler(Direction direction) {
        return this.itemHandler;
    }
    public IEnergyStorage getEnergyStorage(Direction direction) {
        return this.BATTERY_ENERGY_STORAGE;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        pLevel.setBlock(pPos, pState.setValue(Battery.LIT, machineLevel), 3);

        if(this.itemHandler.getStackInSlot(COMPONENT_SLOT).isEmpty()){
            return;
        }

        if (isRedstonePowered(pPos)) {
            return;
        }

        maxProgress = ProgressUtils.setMaxProgress(machineLevel, 9, this.itemHandler, UPGRADE_SLOTS, 1);
        data.set(1, maxProgress);
        if (!hasEnoughEnergy()) {
            return;
        }

        distributeEnergy();
        fillDrainEnergyItem();

        increaseCraftingProgress();
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            ModUtils.ejectItemsWhePusher(pPos.above(),UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, this.itemHandler, pLevel);
            resetProgress();
        }
    }

    private void fillDrainEnergyItem() {
        EnergyUtils.fillItem(BATTERY_ENERGY_STORAGE, itemHandler, INPUT_SLOT[0], OUTPUT_SLOT, 1000);
    }

    private boolean hasEnoughEnergy() {
        return this.BATTERY_ENERGY_STORAGE.getEnergyStored() >= 1;
    }

    private void distributeEnergy() {
        if (level == null || level.isClientSide() || BATTERY_ENERGY_STORAGE.getEnergyStored() <= 0) {
            return;
        }
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = getBlockPos().relative(direction);
            BlockEntity neighborBlockEntity = level.getBlockEntity(neighborPos);

            if (neighborBlockEntity == null
                    || neighborBlockEntity instanceof SolarBlockEntity
                    || neighborBlockEntity instanceof GeneratorBlockEntity
                    || neighborBlockEntity instanceof TranslocatorEnergyBlockEntity
                    || neighborBlockEntity instanceof BatteryBlockEntity) {
                continue;
            }

            try {
                IEnergyStorage neighborStorage = EnergyUtils.getBlockCapabilityEnergyHandler(level, neighborPos, direction);
                if (neighborStorage == null) continue;

                EnergyUtils.transferEnergy(BATTERY_ENERGY_STORAGE, neighborStorage, ENERGY_TRANSFER);
            } catch (Exception e) {
                InfinityNexusCore.LOGGER.error("Failed to transfer energy to neighbor at {}: {}", neighborPos, e.getMessage());
            }
        }
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


    public void setEnergyAndComponent(ItemStack stack) {
        ItemStackComponent itemStack = stack.has(ModDataComponents.ITEM_STACK) ? stack.get(ModDataComponents.ITEM_STACK) : null;
        if(itemStack != null){
            ItemStackHandlerUtils.setStackInSlot(COMPONENT_SLOT, itemStack.itemStack().copy(), itemHandler);
        }
        int energy = stack.getOrDefault(ModDataComponents.ENERGY, 0);

        if(energy > 0){
            fillRecursively(energy);
        }
    }

    private void fillRecursively(int remainingEnergy) {
        int transfer = Math.min(remainingEnergy, ENERGY_TRANSFER);
        int actuallyInserted = EnergyUtils.insertEnergy(BATTERY_ENERGY_STORAGE, transfer, false);
        int newRemaining = remainingEnergy - actuallyInserted;

        if(newRemaining > 0) {
            fillRecursively(newRemaining);
        }
    }
}