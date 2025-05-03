package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMenuProviderBlockEntity extends BaseBlockEntity implements MenuProvider {
    protected int[] INPUT_SLOT;
    protected int OUTPUT_SLOT;
    protected int[] UPGRADE_SLOTS;
    protected int COMPONENT_SLOT;
    protected int ENERGY_CAPACITY;
    protected int ENERGY_TRANSFER;

    protected int progress = 0;
    protected int maxProgress = 0;
    protected ContainerData data;
    protected Component displayName;

    protected ModEnergyStorage ENERGY_STORAGE;

    public BaseMenuProviderBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState blockState,
            Component displayName,
            int[] INPUT_SLOT,
            int OUTPUT_SLOT,
            int[] UPGRADE_SLOTS,
            int COMPONENT_SLOT,
            int ENERGY_CAPACITY,
            int ENERGY_TRANSFER
    ) {
        super(type, pos, blockState);
        this.displayName = displayName;
        this.INPUT_SLOT = INPUT_SLOT;
        this.OUTPUT_SLOT = OUTPUT_SLOT;
        this.UPGRADE_SLOTS = UPGRADE_SLOTS;
        this.COMPONENT_SLOT = COMPONENT_SLOT;
        this.ENERGY_CAPACITY = ENERGY_CAPACITY;
        this.ENERGY_TRANSFER = ENERGY_TRANSFER;
        this.ENERGY_STORAGE = ENERGY_CAPACITY <= 0 ? null : createEnergyStorage();
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> progress = pValue;
                    case 1 -> maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    public void dropContents(Level level, BlockPos worldPosition, ItemStackHandler itemHandler) {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    protected ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(ENERGY_CAPACITY, ENERGY_TRANSFER) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                if (level != null) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
                }
            }
        };
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return ENERGY_STORAGE;
    }

    protected boolean canTick(ItemStackHandler itemHandler) {
        if(itemHandler.getStackInSlot(getComponentSlot()).isEmpty()){
            return false;
        }
        for(int slot : getInputSlot()) {
            if(itemHandler.getStackInSlot(slot).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ContainerData getContainerData() {
        return this.data;
    }


    protected boolean hasProgressFinished() {
        data.set(0, progress);
        return progress >= maxProgress;
    }

    protected void increaseCraftingProgress() {
        data.set(0, progress);
        progress++;
    }
    protected void resetProgress() {
        data.set(0, 0);
        progress = 0;
    }

    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    public int getOutputSlot() {
        return OUTPUT_SLOT;
    }

    public int[] getUpgradeSlot() {
        return UPGRADE_SLOTS;
    }

    public int getComponentSlot() {
        return COMPONENT_SLOT;
    }
    
}
