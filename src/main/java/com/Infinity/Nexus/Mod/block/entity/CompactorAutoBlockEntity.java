package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.EnergyUtils;
import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Core.utils.SoundUtils;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.recipe.CompactorRecipes;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInput;
import com.Infinity.Nexus.Mod.screen.compactor.CompactorMenu;
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

public class CompactorAutoBlockEntity extends BaseMenuProviderBlockEntity{
    private final int maxProgress = 480;
    private final int ENERGY_REQ = 16000;

    public CompactorAutoBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.COMPACTOR_AUTO_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.compactor_auto"),
                new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26},
                27,
                new int[] {},
                28,
                ConfigUtils.compactor_auto_energy_storage_capacity,
                ConfigUtils.compactor_auto_energy_transfer_rate
        );
        data.set(1, maxProgress);
    }
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(29) {
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
                case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 ->
                        !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 28 -> ModUtils.isComponent(stack);
                default -> false;
            };
        }
        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate, boolean fromAutomation) {
            if (slot == 27) {
                return super.extractItem(slot, amount, simulate, false);
            }
            return super.extractItem(slot, amount, simulate, fromAutomation);
        }
        //By gsoldera
        @Override
        public int getSlotLimit(int slot) {
            if (slot >= 0 && slot <= 26) {
                return 1;
            }
            return super.getSlotLimit(slot);
        }

        @Override
        @NotNull
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot < 0 || slot > 26) {
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
            for (int i = 0; i <= 26; i++) {
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
            if (slot >= 0 && slot <= 26) {
                return 1;
            }
            return super.getStackLimit(slot, stack);
        }
    };
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("compactor_auto.progress", progress);
        pTag.putInt("compactor_auto.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("compactor_auto.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("compactor_auto.energy"));
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.compactor_auto");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CompactorMenu(pContainerId, pPlayerInventory,this, this.data, itemHandler);
    }
    public void drops() {
        dropContents(level, this.worldPosition, this.itemHandler);
    }

    public IEnergyStorage getEnergyStorage(Direction direction) {
        return ENERGY_STORAGE;
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }
        if(isRedstonePowered(pPos)){
            return;
        }
        if(!canTick(itemHandler)){
            return;
        }
        if (!hasEnoughEnergy()) {
            return;
        }
        if(!hasProgressFinished()){
            increaseCraftingProgress();
            if(progress > 0){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            return;
        }
        Optional<RecipeHolder<CompactorRecipes>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            resetProgress();
            return;
        }
        craft(pLevel, recipe);
        resetProgress();
        removeEnergy();
        setChanged();
    }

    private void removeEnergy() {
        EnergyUtils.extractEnergy(ENERGY_STORAGE, ENERGY_REQ, false);
    }

    private void craft(Level pLevel, Optional<RecipeHolder<CompactorRecipes>> recipe) {
        ItemStack result = recipe.get().value().getResultItem(pLevel.registryAccess());
        for (int slot : INPUT_SLOT) {
            ItemStackHandlerUtils.extractItem(slot, 1, false, itemHandler);
        }
        ItemStackHandlerUtils.setStackInSlot(OUTPUT_SLOT, result, itemHandler);
        SoundUtils.playSound(pLevel, this.worldPosition, SoundSource.BLOCKS, SoundEvents.TURTLE_EGG_CRACK, 0.5f, 1.0f);
    }
    private Optional<RecipeHolder<CompactorRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(27);
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.COMPACTOR_RECIPE_TYPE.get(), new MultipleMachinesRecipeInput(inventory, 27), level);
    }
    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    public int getMaxProgress() {
        return this.maxProgress;
    }

    public int getCurrentProgress() {
        return this.progress;
    }

    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }

    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
}
