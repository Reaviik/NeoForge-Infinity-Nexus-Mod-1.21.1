package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.FluidUtils;
import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.SoundUtils;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.recipe.FermentationBarrelRecipes;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInputWithFluid;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FermentationBarrelBlockEntity extends BaseBlockEntity implements MenuProvider {
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,2 -> true;
                case 1,3 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate, boolean fromAutomation) {
            if (slot == 1 || slot == 3) {
                return super.extractItem(slot, amount, simulate, false);
            }
            return super.extractItem(slot, amount, simulate, fromAutomation);
        }
    };

    private final FluidTank inputFluidHandler = new FluidTank(INPUT_FLUID_CAPACITY) {
        @Override
        public void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };
    private static final int INPUT_FLUID_ITEM = 0;
    private static final int OUTPUT_FLUID_ITEM = 1;
    private static final int INPUT_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;
    private final FluidTank FLUID_STORAGE = inputFluidHandler;
    private static final int INPUT_FLUID_CAPACITY = 8000;
    private static final int OUTPUT_FLUID_CAPACITY = 8000;

    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);
    private Lazy<IFluidHandler> lazyFluidHandler = Lazy.of(() -> FLUID_STORAGE);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 0;
    private double nextTarget = maxProgress * 0.025;


    public FermentationBarrelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FERMENTATION_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FermentationBarrelBlockEntity.this.progress;
                    case 1 -> FermentationBarrelBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FermentationBarrelBlockEntity.this.progress = pValue;
                    case 1 -> FermentationBarrelBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = Lazy.of(() -> itemHandler);
        lazyFluidHandler = Lazy.of(() -> FLUID_STORAGE);
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.fermentation_barrel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FermentationBarrelMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", this.itemHandler.serializeNBT(registries));
        pTag.putInt("fermentation_barrel.progress", progress);
        pTag.putInt("fermentation_barrel.max_progress", maxProgress);
        pTag = FLUID_STORAGE.writeToNBT(registries, pTag);
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        this.itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("fermentation_barrel.progress");
        maxProgress = pTag.getInt("fermentation_barrel.max_progress");
        FLUID_STORAGE.readFromNBT(registries, pTag);
    }

    public static long getInputFluidCapacity() {
        return INPUT_FLUID_CAPACITY;
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    public IFluidHandler getFluidHandler(Direction direction) {
        return FLUID_STORAGE;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }

        FluidUtils.transferFromItemToTank(FLUID_STORAGE, itemHandler, OUTPUT_FLUID_ITEM, INPUT_FLUID_ITEM);
        if (isRedstonePowered(pPos)) {
            return;
        }

        if(itemHandler.getStackInSlot(INPUT_SLOT).isEmpty()){
            return;
        }

        Optional<RecipeHolder<FermentationBarrelRecipes>> recipe = getCurrentRecipe();
        if (!hasRecipe(recipe)) {
            resetProgress();
            return;
        }

        setMaxProgress();
        increaseCraftingProgress(pPos);
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem(recipe);
            resetProgress();
        }
    }

    private void setMaxProgress() {
        int time = getCurrentRecipe().get().value().getDuration();

        if(this.level.getBlockState(getBlockPos().below()).getBlock() instanceof MyceliumBlock){
            time = time / 2;
        }
        if(itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItemsAdditions.INFINITY_INGOT.get()){
            time = time * itemHandler.getStackInSlot(INPUT_SLOT).getCount();
        }
        maxProgress = time;
    }


    private void resetProgress() {
        progress = 0;
    }

    private void craftItem(Optional<RecipeHolder<FermentationBarrelRecipes>> recipe) {
        int recipeFluidInputAmount = recipe.get().value().getInputFluidStack().getAmount();
        int recipeInputCount = recipe.get().value().getInputCount();
        int recipeResultCount = recipe.get().value().getResultItem(null).getCount();
        ItemStack result = recipe.get().value().getResultItem(null);


        int inputCount = recipe.get().value().getInputCount();
        int fluidInputCount = recipe.get().value().getInputFluidStack().getAmount();
        while (itemHandler.getStackInSlot(INPUT_SLOT).getCount() >= inputCount && this.FLUID_STORAGE.getFluidAmount() >= fluidInputCount) {
            if(!ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), recipeResultCount, OUTPUT_SLOT, itemHandler)){
                break;
            }
            FluidUtils.drainFluidFromTank(FLUID_STORAGE, recipeFluidInputAmount);
            result.setCount(this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + recipeResultCount);
            ItemStackHandlerUtils.setStackInSlot(OUTPUT_SLOT, result, itemHandler);
            if(!itemHandler.getStackInSlot(INPUT_SLOT).is(ModItemsAdditions.STRAINER.get())){
                ItemStackHandlerUtils.extractItem(INPUT_SLOT, recipeInputCount, false, itemHandler);
            }

        }
        SoundUtils.playSound(level, worldPosition, SoundSource.BLOCKS, SoundEvents.BREWING_STAND_BREW, 1.0f, 1.0f);
    }


    private boolean hasRecipe(Optional<RecipeHolder<FermentationBarrelRecipes>> recipe) {
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());
        return ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), result.getCount(), OUTPUT_SLOT, itemHandler);
    }


    private Optional<RecipeHolder<FermentationBarrelRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        inventory.setItem(0, itemHandler.getStackInSlot(2));

        return this.level.getRecipeManager().getRecipeFor(ModRecipes.FERMENTATION_RECIPE_TYPE.get(), new MultipleMachinesRecipeInputWithFluid(inventory, 1, List.of(this.FLUID_STORAGE.getFluid()), 1), level);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress(BlockPos pPos) {
        if (progress >= nextTarget) {
        double x = pPos.getX() + 0.5;
        double y = pPos.getY() + 1;
        double z = pPos.getZ() + 0.5;
        var level = (ServerLevel) this.getLevel();
        level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y + 0.1, z, 1, 0, 0, 0, 0.01D);
        nextTarget += maxProgress * 0.025;
    }
        progress ++;
    }

    public static int getInputSlot() {
        return INPUT_SLOT;
    }

    public static int getOutputSlot() {
        return OUTPUT_SLOT;
    }
}