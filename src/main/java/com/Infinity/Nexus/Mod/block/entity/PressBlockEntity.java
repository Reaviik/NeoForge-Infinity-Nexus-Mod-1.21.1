package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.*;
import com.Infinity.Nexus.Mod.block.custom.Press;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInput;
import com.Infinity.Nexus.Mod.recipe.PressRecipes;
import com.Infinity.Nexus.Mod.screen.press.PressMenu;
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

public class PressBlockEntity extends BaseMenuProviderBlockEntity {

    public PressBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.PRESS_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.press"),
                new int[] {0},
                2,
                new int[] {3, 4, 5, 6},
                7,
                ConfigUtils.press_energy_storage_capacity,
                ConfigUtils.press_energy_transfer_rate

        );
    }
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 2 -> true;
                case 3,4,5,6 -> ModUtils.isUpgrade(stack);
                case 7 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }
        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate, boolean fromAutomation) {
            if (slot == 2) {
                return super.extractItem(slot, amount, simulate, false);
            }
            return super.extractItem(slot, amount, simulate, fromAutomation);
        }
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot == 2) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.press").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PressMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }
    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    public void drops() {
        dropContents(level, this.worldPosition, this.itemHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("press.progress", progress);
        pTag.putInt("press.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(pTag , registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("press.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("press.energy"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        pLevel.setBlock(pPos, pState.setValue(Press.LIT, machineLevel), 3);

        if(!canTick(itemHandler)){
            return;
        }

        if (isRedstonePowered(pPos)) {
            return;
        }

        Optional<RecipeHolder<PressRecipes>> recipe = getCurrentRecipe();
        if (!hasRecipe(recipe)) {
            resetProgress();
            return;
        }

        maxProgress = ProgressUtils.setMaxProgress(machineLevel, recipe.get().value().getDuration(), itemHandler, UPGRADE_SLOTS, ConfigUtils.press_minimum_tick);
        data.set(1, maxProgress);

        if (!hasEnoughEnergy()) {
            return;
        }
        pLevel.setBlock(pPos, pState.setValue(Press.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        extractRecipeEnergy(recipe);
        setChanged(pLevel, pPos, pState);


        if (hasProgressFinished()) {
            craftItem(recipe);
            ModUtils.ejectItemsWhePusher(pPos,UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }
    private void extractRecipeEnergy(Optional<RecipeHolder<PressRecipes>> recipe) {
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
        return ENERGY_STORAGE.getEnergyStored() >= ((getCurrentRecipe().get().value().getEnergy() + (getMachineLevel()*20)) / maxProgress);
    }


    private void craftItem(Optional<RecipeHolder<PressRecipes>> recipe) {
        ItemStack result = recipe.get().value().getResultItem(level.registryAccess());

        ItemStackHandlerUtils.extractItem(INPUT_SLOT[0], recipe.get().value().getInputCount(), false, itemHandler);
        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        ModUtils.useComponent(component, level, this.getBlockPos());

        ItemStackHandlerUtils.insertItem(OUTPUT_SLOT, result, false, itemHandler);

        SoundUtils.playSoundHideoutMuffler(level, worldPosition, itemHandler, UPGRADE_SLOTS, SoundEvents.ANVIL_FALL);
    }

    private boolean hasRecipe(Optional<RecipeHolder<PressRecipes>> recipe) {

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());

        return ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), result.getCount(), OUTPUT_SLOT, itemHandler);
    }

    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }
    private Optional<RecipeHolder<PressRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(3);
        inventory.setItem(0, itemHandler.getStackInSlot(COMPONENT_SLOT));
        inventory.setItem(1, itemHandler.getStackInSlot(0));
        inventory.setItem(2, itemHandler.getStackInSlot(1));

        return this.level.getRecipeManager().getRecipeFor(ModRecipes.PRESS_RECIPE_TYPE.get(), new MultipleMachinesRecipeInput(inventory, 3), level);
    }

    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }
    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }

}