package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.*;
import com.Infinity.Nexus.Mod.block.custom.Crusher;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.recipe.CrusherRecipes;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInput;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherMenu;
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

public class CrusherBlockEntity extends BaseMenuProviderBlockEntity {

    public CrusherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.CRUSHER_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.crusher"),
                new int[] {0},
                1,
                new int[]{2, 3, 4, 5},
                6,
                ConfigUtils.crusher_energy_storage_capacity,
                ConfigUtils.crusher_energy_transfer_rate
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
                case 1 -> false;
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
    };
    @Override
    public Component getDisplayName() {
        return super.getDisplayName().copy().append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CrusherMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }


    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.saveAdditional(pTag, registries);
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("crusher.progress", progress);
        pTag.putInt("crusher.energy", ENERGY_STORAGE.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("crusher.progress");
        ENERGY_STORAGE.setEnergy(tag.getInt("crusher.energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        dropContents(level, this.worldPosition, this.itemHandler);
    }


    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        pLevel.setBlock(pPos, pState.setValue(Crusher.LIT, machineLevel), 3);

        if (!canTick(itemHandler)) {
            return;
        }

        if (isRedstonePowered(pPos)) {
            return;
        }

        Optional<RecipeHolder<CrusherRecipes>> recipe = getCurrentRecipe();

        if (!hasRecipe(recipe)) {
            resetProgress();
            return;
        }

        maxProgress = ProgressUtils.setMaxProgress(machineLevel, recipe.get().value().getDuration(), itemHandler, UPGRADE_SLOTS, ConfigUtils.crusher_minimum_tick);
        data.set(1, maxProgress);
        if (!hasEnoughEnergy()) {
            return;
        }

        pLevel.setBlock(pPos, pState.setValue(Crusher.LIT, machineLevel + 9), 3);
        increaseCraftingProgress();
        extractRecipeEnergy(recipe);
        setChanged();

        if (hasProgressFinished()) {
            craftItem(machineLevel + 1, recipe);
            ModUtils.ejectItemsWhePusher(pPos, UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }
    private void extractRecipeEnergy(Optional<RecipeHolder<CrusherRecipes>> recipe) {
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
        return ENERGY_STORAGE.getEnergyStored() >= 32;
    }

    private void craftItem(int machineLevel, Optional<RecipeHolder<CrusherRecipes>> recipe) {
        ItemStack result = recipe.get().value().getResultItem(null);
        int duplicate = getDuplicationValue(machineLevel, recipe, result);
        ItemStackHandlerUtils.extractItem(INPUT_SLOT[0], recipe.get().value().getCount(), false, itemHandler);
        result.setCount((result.getCount() + duplicate) + itemHandler.getStackInSlot(OUTPUT_SLOT).getCount());

        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
        ModUtils.useComponent(component, level, this.getBlockPos());
        ItemStackHandlerUtils.setStackInSlot(OUTPUT_SLOT, result, itemHandler);

        SoundUtils.playSoundHideoutMuffler(level, worldPosition, itemHandler, UPGRADE_SLOTS, SoundEvents.BASALT_BREAK);
    }

    private int getDuplicationValue( int machineLevel, Optional<RecipeHolder<CrusherRecipes>> recipe, ItemStack result) {
        int duplicationValue = recipe.get().value().canDuplicate() ? Math.round(result.getCount() * (machineLevel / 2.0F)) : result.getCount();
        return duplicationValue > 1 ? duplicationValue : 0;
    }

    private boolean hasRecipe(Optional<RecipeHolder<CrusherRecipes>> recipe) {
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());

        return ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), getDuplicationValue(getMachineLevel(), recipe, result)+1, OUTPUT_SLOT, itemHandler);
    }

    private Optional<RecipeHolder<CrusherRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(2);
        inventory.setItem(0, itemHandler.getStackInSlot(COMPONENT_SLOT));
        inventory.setItem(1, itemHandler.getStackInSlot(INPUT_SLOT[0]));
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.CRUSHER_RECIPE_TYPE.get(),
                new MultipleMachinesRecipeInput(inventory, 2), level);
    }

    private int getMachineLevel() {
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }

    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
}