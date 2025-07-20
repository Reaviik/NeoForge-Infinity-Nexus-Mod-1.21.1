package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.*;
import com.Infinity.Nexus.Mod.block.custom.Assembler;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.recipe.AssemblerRecipes;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInput;
import com.Infinity.Nexus.Mod.screen.assembler.AssemblerMenu;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AssemblerBlockEntity extends BaseMenuProviderBlockEntity {

    private static final int FLUID_ITEM_INPUT_SLOT = 14;
    private static final int FLUID_ITEM_OUTPUT_SLOT = 15;
    //Configs
    private static final int FLUID_STORAGE_CAPACITY = ConfigUtils.assembler_fluid_storage_capacity;
    private static final int ENERGY_REQ = ConfigUtils.assembler_energy_request;

    public AssemblerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.ASSEMBLY_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.assembler"),
                new int[] {0, 1, 2, 3, 4, 5, 6, 7},
                8,
                new int[] {9, 10, 11, 12},
                13,
                ConfigUtils.assembler_energy_storage_capacity,
                ConfigUtils.assembler_energy_transfer_rate

        );
    }

    private final FluidTank FLUID_STORAGE = new FluidTank(FLUID_STORAGE_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!getLevel().isClientSide) {
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.LUBRICANT_SOURCE.get();
        }
    };
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(16) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1,2,3,4,5,6,7 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 9,10,11,12 -> ModUtils.isUpgrade(stack);
                case 13 -> ModUtils.isComponent(stack);
                case 14,15 -> FluidUtils.isFluidHandlerItem(stack);
                default -> false;
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
            if (slot < 0 || slot > 7) {
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
            for (int i = 0; i <= 7; i++) {
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
            if (slot >= 0 && slot <= 7) {
                return 1;
            }
            return super.getStackLimit(slot, stack);
        }
    };

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("assembler.progress", progress);
        pTag.putInt("assembler.energy", ENERGY_STORAGE.getEnergyStored());
        pTag = FLUID_STORAGE.writeToNBT(registries, pTag);
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("assembler.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("assembler.energy"));
        FLUID_STORAGE.readFromNBT(registries, pTag);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.assembler").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AssemblerMenu(pContainerId, pPlayerInventory,this, this.data, itemHandler);
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
    public IFluidHandler getFluidHandler(Direction direction) {
        return FLUID_STORAGE;
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }

        FluidUtils.transferFromItemToTank(FLUID_STORAGE, itemHandler, FLUID_ITEM_OUTPUT_SLOT, FLUID_ITEM_INPUT_SLOT);
        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        pLevel.setBlock(pPos, pState.setValue(Assembler.LIT, machineLevel), 3);

        if(!canTick(itemHandler)){
            return;
        }

        if (isRedstonePowered(pPos)) {
            return;
        }
        if(itemHandler.getStackInSlot(0).isEmpty()){
            return;
        }

        Optional<RecipeHolder<AssemblerRecipes>> recipe = getCurrentRecipe();
        if (!hasRecipe(recipe)) {
            resetProgress();
            return;
        }

        maxProgress = ProgressUtils.setMaxProgress(machineLevel, recipe.get().value().getDuration(), itemHandler, UPGRADE_SLOTS, ConfigUtils.assembler_minimum_tick);
        data.set(1, maxProgress);
        if (!hasEnoughEnergy()) {
            return;
        }
        pLevel.setBlock(pPos, pState.setValue(Assembler.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        extractRecipeEnergy(recipe);
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem(recipe);
            extractFluid(machineLevel);
            ModUtils.ejectItemsWhePusher(pPos,UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }

    private void extractFluid(int machineLevel) {
        FluidUtils.drainFluidFromTank(FLUID_STORAGE, machineLevel+1);
    }

    private void extractRecipeEnergy(Optional<RecipeHolder<AssemblerRecipes>> recipe) {
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

    private void craftItem(Optional<RecipeHolder<AssemblerRecipes>> recipe) {
        ItemStack result = recipe.get().value().getResultItem(null);

        for (int slot : INPUT_SLOT) {
            ItemStackHandlerUtils.extractItem(slot, 1, false, itemHandler);
        }
        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
        ModUtils.useComponent(component, level, worldPosition);

        result.setCount(result.getCount() + itemHandler.getStackInSlot(OUTPUT_SLOT).getCount());
        ItemStackHandlerUtils.setStackInSlot(OUTPUT_SLOT, result, itemHandler);

        SoundUtils.playSoundHideoutMuffler(level, worldPosition, itemHandler, UPGRADE_SLOTS, SoundEvents.ANVIL_USE);
    }

    private boolean hasRecipe(Optional<RecipeHolder<AssemblerRecipes>> recipe) {
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());
        return ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), result.getCount(), OUTPUT_SLOT, itemHandler);
    }

    private Optional<RecipeHolder<AssemblerRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(9);
        inventory.setItem(0, itemHandler.getStackInSlot(COMPONENT_SLOT));
        for (int i = 0; i < 8; i++) {
            inventory.setItem(i+1, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.ASSEMBLY_RECIPE_TYPE.get(), new MultipleMachinesRecipeInput(inventory, 9), level);
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