package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.*;
import com.Infinity.Nexus.Mod.block.custom.Smeltery;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInput;
import com.Infinity.Nexus.Mod.recipe.SmelteryRecipes;
import com.Infinity.Nexus.Mod.screen.smeltery.SmelteryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class SmelteryBlockEntity extends BaseMenuProviderBlockEntity {

    private static final int ENERGY_REQ = 32;

    public SmelteryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.SMELTERY_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.smeltery"),
                new int[] {0, 1, 2},
                3,
                new int[] {4,5,6,7},
                8,
                ConfigUtils.smelter_energy_storage_capacity,
                ConfigUtils.smelter_energy_transfer_rate

        );
    }
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0, 1, 2 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 3 -> true;
                case 4, 5, 6, 7 -> ModUtils.isUpgrade(stack);
                case 8 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate, boolean fromAutomation) {
            if (slot == 3) {
                return super.extractItem(slot, amount, simulate, false);
            }
            return super.extractItem(slot, amount, simulate, fromAutomation);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot == 3) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };


    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("smeltery.progress", progress);
        pTag.putInt("smeltery.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag , registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("smeltery.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("smeltery.energy"));
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.smeltery").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SmelteryMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
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
        pLevel.setBlock(pPos, pState.setValue(Smeltery.LIT, machineLevel), 3);

        if(itemHandler.getStackInSlot(COMPONENT_SLOT).isEmpty()){
            return;
        }

        if (isRedstonePowered(pPos)) {
            return;
        }

        Optional<RecipeHolder<SmelteryRecipes>> recipe = getCurrentRecipe();
        if (!hasRecipe(recipe)) {
            resetProgress();
            return;
        }
        maxProgress = ProgressUtils.setMaxProgress(machineLevel, recipe.get().value().getDuration(), itemHandler, UPGRADE_SLOTS, ConfigUtils.smelter_minimum_tick);
        data.set(1, maxProgress);
        if (!hasEnoughEnergy()) {
            return;
        }

        pLevel.setBlock(pPos, pState.setValue(Smeltery.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        extractRecipeEnergy(recipe);
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem();
            ModUtils.ejectItemsWhePusher(pPos,UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }

    private void extractRecipeEnergy(Optional<RecipeHolder<SmelteryRecipes>> recipe) {
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


    private void craftItem() {
        Optional<RecipeHolder<SmelteryRecipes>> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().getResultItem(null);
        for(int i = 1; i < recipe.get().value().getIngredients().size(); i++){
            int slotIndex = INPUT_SLOT[i-1];
            ItemStackHandlerUtils.extractItem(slotIndex, recipe.get().value().getInputCount(i), false, itemHandler);
        }
        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
        ModUtils.useComponent(component, level, this.getBlockPos());
        ItemStackHandlerUtils.insertItem(OUTPUT_SLOT, result, false, itemHandler);
        SoundUtils.playSound(level, worldPosition, SoundSource.BLOCKS, SoundEvents.LAVA_EXTINGUISH, 0.3f, 1.0f);
    }

    private boolean hasRecipe(Optional<RecipeHolder<SmelteryRecipes>> recipe) {
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());
        return ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), result.getCount(), OUTPUT_SLOT, itemHandler);
    }

    private Optional<RecipeHolder<SmelteryRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(4);
        inventory.setItem(0, itemHandler.getStackInSlot(COMPONENT_SLOT));
        inventory.setItem(1, itemHandler.getStackInSlot(0));
        inventory.setItem(2, itemHandler.getStackInSlot(1));
        inventory.setItem(3, itemHandler.getStackInSlot(2));

        return this.level.getRecipeManager().getRecipeFor(ModRecipes.SMELTRERY_RECIPE_TYPE.get(), new MultipleMachinesRecipeInput(inventory, 4), level);
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