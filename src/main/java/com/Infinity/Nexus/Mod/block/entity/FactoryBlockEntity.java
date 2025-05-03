package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.*;
import com.Infinity.Nexus.Mod.block.custom.Factory;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.recipe.FactoryRecipes;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInput;
import com.Infinity.Nexus.Mod.screen.factory.FactoryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FactoryBlockEntity extends BaseMenuProviderBlockEntity {
    private static final int ENERGY_REQ = 32;

    public FactoryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.FACTORY_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.factory"),
                new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
                16,
                new int[]{17, 18, 19, 20},
                21,
                ConfigUtils.factory_energy_storage_capacity,
                ConfigUtils.factory_energy_transfer_rate
        );
    }



    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(22) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 16 -> false;
                case 17,18,19,20 -> ModUtils.isUpgrade(stack);
                case 21 -> ModUtils.isComponent(stack);

                default -> super.isItemValid(slot, stack);
            };
        }
        //By gsoldera
        @Override
        public int getSlotLimit(int slot) {
            if (slot >= 0 && slot <= 7) {
                return 1;
            }
            return super.getSlotLimit(slot);
        }

        @Override
        @NotNull
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot < 0 || slot > 15) {
                return super.insertItem(slot, stack, simulate);
            }

            if (!isItemValid(slot, stack)) {
                return stack;
            }

            if (simulate) {
                ItemStack existing = getStackInSlot(slot);
                if (existing.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                return stack;
            }

            // Procura o primeiro slot vazio
            for (int i = 0; i <= 15; i++) {
                ItemStack existingStack = getStackInSlot(i);
                if (existingStack.isEmpty()) {
                    ItemStack singleItem = stack.copy();
                    singleItem.setCount(1);
                    setStackInSlot(i, singleItem);

                    ItemStack remainder = stack.copy();
                    remainder.shrink(1);
                    return remainder;
                }
            }

            return stack;
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            if (slot >= 0 && slot <= 15) {
                return 1;
            }
            return super.getStackLimit(slot, stack);
        }
    };

    public ItemStack getRenderStack(int slot){
        return itemHandler.getStackInSlot(slot).isEmpty() ? ItemStack.EMPTY : itemHandler.getStackInSlot(slot);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("factory.progress", progress);
        pTag.putInt("factory.maxProgress", maxProgress);
        pTag.putInt("factory.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("factory.progress");
        maxProgress = pTag.getInt("factory.maxProgress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("factory.energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        dropContents(level, this.worldPosition, this.itemHandler);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.factory").append(" LV "+ getMachineLevel());
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FactoryMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }

    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public int getCurrentProgress() {
        return this.progress;
    }
    public int getMaxProgress() {
        return maxProgress;
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }
        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        pLevel.setBlock(pPos, pState.setValue(Factory.LIT, machineLevel), 3);

        if(!canTick(itemHandler)){
            return;
        }

        if (isRedstonePowered(pPos)) {
            return;
        }

        Optional<RecipeHolder<FactoryRecipes>> recipe = getCurrentRecipe();
        if (!hasRecipe(recipe)) {
            resetProgress();
            return;
        }

        maxProgress = ProgressUtils.setMaxProgress(machineLevel, recipe.get().value().getDuration(), itemHandler, UPGRADE_SLOTS, ConfigUtils.factory_minimum_tick);
        data.set(1, maxProgress);
        if (!hasEnoughEnergy()) {
            return;
        }
        pLevel.setBlock(pPos, pState.setValue(Factory.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        extractRecipeEnergy(recipe);
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem(recipe);
            ModUtils.ejectItemsWhePusher(pPos.above(),UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }

    private void extractRecipeEnergy(Optional<RecipeHolder<FactoryRecipes>> recipe) {
        EnergyUtils.extractEnergyFromRecipe(
                ENERGY_STORAGE,
                recipe.get().value().getEnergy(),
                getMachineLevel() + 1,
                maxProgress,
                itemHandler,
                UPGRADE_SLOTS
        );
    }

    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }


    private void craftItem(Optional<RecipeHolder<FactoryRecipes>> recipe) {
        ItemStack result = recipe.get().value().getResultItem(null);

        for (int slot : INPUT_SLOT) {
            ItemStackHandlerUtils.extractItem(slot, 1, false, itemHandler);
        }
        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
        ModUtils.useComponent(component, level, this.getBlockPos());

        result.setCount(result.getCount() + itemHandler.getStackInSlot(OUTPUT_SLOT).getCount());
        ItemStackHandlerUtils.setStackInSlot(OUTPUT_SLOT, result, itemHandler);

        SoundUtils.playSoundHideoutMuffler(level, worldPosition, itemHandler, UPGRADE_SLOTS, SoundEvents.RESPAWN_ANCHOR_AMBIENT);
    }

    private boolean hasRecipe(Optional<RecipeHolder<FactoryRecipes>> recipe) {
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());

        return ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), result.getCount(), OUTPUT_SLOT, itemHandler);
    }

    private Optional<RecipeHolder<FactoryRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(17);
        inventory.setItem(0, itemHandler.getStackInSlot(COMPONENT_SLOT));
        for (int i = 0; i < 16; i++) {
            inventory.setItem(i+1, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.FACTORY_RECIPE_TYPE.get(), new MultipleMachinesRecipeInput(inventory, 17), level);
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

    public IItemHandler getRecipeInventory() {
        return this.itemHandler;
    }
}