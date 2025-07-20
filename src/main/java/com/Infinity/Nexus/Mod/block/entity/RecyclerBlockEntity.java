package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Core.utils.SoundUtils;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.custom.Recycler;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.screen.recycler.RecyclerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RecyclerBlockEntity extends BaseMenuProviderBlockEntity{
    private static final int ENERGY_REQ = 1500;

    public RecyclerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.RECYCLER_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.recycler"),
                new int[] {0},
                1,
                new int[] {2, 3, 4, 5},
                6,
                ConfigUtils.recycler_energy_storage_capacity,
                ConfigUtils.recycler_energy_transfer_rate

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
                case 0 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 1 -> true;
                case 2, 3, 4, 5 -> ModUtils.isUpgrade(stack);
                case 6 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }
        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate, boolean fromAutomation) {
            if (slot == 1) {
                return super.extractItem(slot, amount, simulate, false);
            }
            return super.extractItem(slot, amount, simulate, fromAutomation);
        }
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot == 1) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.recycler").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RecyclerMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    public IEnergyStorage getEnergyStorage(Direction direction) {
        return ENERGY_STORAGE;
    }


    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("recycler.progress", progress);
        pTag.putInt("recycler.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("recycler.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("recycler.energy"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        pLevel.setBlock(pPos, pState.setValue(Recycler.LIT, machineLevel), 3);

        if (isRedstonePowered(pPos)) {
            return;
        }

        if(getMachineLevel() <= 0){
            return;
        }

        if (!hasEnoughEnergy()) {
            return;
        }
        if(itemHandler.getStackInSlot(INPUT_SLOT[0]).isEmpty()){
            return;
        }
        setMaxProgress(machineLevel);
        pLevel.setBlock(pPos, pState.setValue(Recycler.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem();
            ModUtils.ejectItemsWhePusher(pPos,UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }

    private void craftItem() {
        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
        ModUtils.useComponent(component, level, this.getBlockPos());

        itemHandler.getStackInSlot(INPUT_SLOT[0]).shrink(1);
        int chance = new Random().nextInt(100);
        if(chance < 5 && itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() < 64){
            ItemStackHandlerUtils.insertItem(OUTPUT_SLOT, new ItemStack(ModItemsProgression.RESIDUAL_MATTER.get(), 1), false, itemHandler);
        }

        SoundUtils.playSoundHideoutMuffler(level, worldPosition, itemHandler, UPGRADE_SLOTS, SoundEvents.GRAVEL_FALL);
    }

    private void setMaxProgress(int machineLevel) {
        int duration = 130;
        int halfDuration = duration / 2;
        int speedReduction = halfDuration / 16;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS); //16

        int reducedDuration = speed * speedReduction;
        int reducedLevel = machineLevel * (halfDuration / 8);
        duration = duration - reducedDuration - reducedLevel;

        maxProgress = Math.max(duration, ConfigUtils.assembler_minimum_tick);
    }

    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
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