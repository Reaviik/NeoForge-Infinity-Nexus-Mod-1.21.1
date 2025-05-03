package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.*;
import com.Infinity.Nexus.Mod.block.custom.Squeezer;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInput;
import com.Infinity.Nexus.Mod.recipe.SqueezerRecipes;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerMenu;
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

public class SqueezerBlockEntity extends BaseMenuProviderBlockEntity{
    private static final int FLUID_SLOT = 2;
    private static final int OUTPUT_FLUID_SLOT = 3;
    private static final int fluidCapacity = 5000;

    private final FluidTank FLUID_STORAGE = createFluidStorage();


    public SqueezerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(
                ModBlockEntities.SQUEEZER_BE.get(),
                pPos,
                pBlockState,
                Component.translatable("block.infinity_nexus_mod.assembler"),
                new int[] {0},
                1,
                new int[] {4, 5, 6, 7},
                8,
                ConfigUtils.squeezer_energy_storage_capacity,
                ConfigUtils.squeezer_energy_transfer_rate

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
                case 0,1 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 2 -> true;
                case 3 -> false;
                case 4,5,6,7 -> ModUtils.isUpgrade(stack);
                case 8 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate, boolean fromAutomation) {
            return (slot == 1 || slot == 3)? super.extractItem(slot, amount, simulate, false) : super.extractItem(slot, amount, simulate, fromAutomation);
        }
    };
    private FluidTank createFluidStorage() {
        return new FluidTank(fluidCapacity) {
            @Override
            public void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("squeezer.progress", progress);
        pTag.putInt("squeezer.energy", ENERGY_STORAGE.getEnergyStored());
        pTag = FLUID_STORAGE.writeToNBT(registries, pTag);
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("squeezer.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("squeezer.energy"));
        FLUID_STORAGE.readFromNBT(registries, pTag);
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.squeezer").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SqueezerMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }
    public void drops() {
        dropContents(level, this.worldPosition, this.itemHandler);
    }
    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public static long getFluidCapacity() {
        return fluidCapacity;
    }
    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }
    public FluidStack getFluid() {
        return FLUID_STORAGE.getFluid();
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    public IFluidHandler getFluidHandler(Direction direction) {
        return FLUID_STORAGE;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        try {
            if (pLevel.isClientSide) {
                return;
            }

            FluidUtils.transferFromTankToItem(FLUID_STORAGE, itemHandler, OUTPUT_FLUID_SLOT, FLUID_SLOT);
            int machineLevel = getMachineLevel() - 1 <= 0 ? 0 : getMachineLevel() - 1;
            pLevel.setBlock(pPos, pState.setValue(Squeezer.LIT, machineLevel), 3);
            if (isRedstonePowered(pPos)) {
                return;
            }

            Optional<RecipeHolder<SqueezerRecipes>> recipe = getCurrentRecipe();
            if (!hasRecipe(recipe)) {
                resetProgress();
                return;
            }
            maxProgress = ProgressUtils.setMaxProgress(machineLevel, recipe.get().value().getDuration(), itemHandler, UPGRADE_SLOTS, ConfigUtils.squeezer_minimum_tick);
            if (!hasEnoughEnergy()) {
                return;
            }
            if(!canInsertOutputFluid()){
                return;
            }
            pLevel.setBlock(pPos, pState.setValue(Squeezer.LIT, machineLevel + 9), 3);
            increaseCraftingProgress();
            extractRecipeEnergy(recipe);
            setChanged(pLevel, pPos, pState);


            if (hasProgressFinished()) {
                craftItem(recipe);
                ModUtils.ejectItemsWhePusher(pPos.above(),UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
                resetProgress();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void extractRecipeEnergy(Optional<RecipeHolder<SqueezerRecipes>> recipe) {
        EnergyUtils.extractEnergyFromRecipe(
                ENERGY_STORAGE,
                recipe.get().value().getEnergy(),
                getMachineLevel() + 1,
                maxProgress,
                itemHandler,
                UPGRADE_SLOTS
        );
    }


    private boolean canInsertOutputFluid() {
        return FLUID_STORAGE.getSpace() >= getCurrentRecipe().get().value().getFluid().getAmount()  &&
                FLUID_STORAGE.getFluid().is(getCurrentRecipe().get().value().getFluid().getFluid()) ||
                FLUID_STORAGE.getFluid().isEmpty();
    }

    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ((getCurrentRecipe().get().value().getEnergy() + (getMachineLevel()*20)) / maxProgress);
    }

    private void craftItem(Optional<RecipeHolder<SqueezerRecipes>> recipe) {
        ItemStack result = recipe.get().value().getResultItem(null);

        ItemStackHandlerUtils.extractItem(INPUT_SLOT[0], result.getCount(), false, itemHandler);

        result.setCount(result.getCount() + itemHandler.getStackInSlot(OUTPUT_SLOT).getCount());
        ItemStackHandlerUtils.setStackInSlot(OUTPUT_SLOT, result, itemHandler);

        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
        ModUtils.useComponent(component, level, this.getBlockPos());

        FluidStack fluidStack = recipe.get().value().getFluid();
        this.FLUID_STORAGE.fill(new FluidStack(fluidStack.getFluid(), fluidStack.getAmount()), IFluidHandler.FluidAction.EXECUTE);

        SoundUtils.playSoundHideoutMuffler(level, worldPosition, itemHandler, UPGRADE_SLOTS, SoundEvents.BUCKET_EMPTY);

    }

    private boolean hasRecipe(Optional<RecipeHolder<SqueezerRecipes>> recipe) {
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());
        return ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), result.getCount(), OUTPUT_SLOT, itemHandler);
    }

    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }
    private Optional<RecipeHolder<SqueezerRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(2);
        inventory.setItem(0, itemHandler.getStackInSlot(COMPONENT_SLOT));
        inventory.setItem(1, itemHandler.getStackInSlot(INPUT_SLOT[0]));
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.SQUEEZER_RECIPE_TYPE.get(), new MultipleMachinesRecipeInput(inventory, 2), level);
    }

    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }
    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
}