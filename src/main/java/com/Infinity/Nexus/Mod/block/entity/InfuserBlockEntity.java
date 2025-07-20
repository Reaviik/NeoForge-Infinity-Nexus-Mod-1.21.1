package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Core.utils.SoundUtils;
import com.Infinity.Nexus.Mod.block.custom.Infuser;
import com.Infinity.Nexus.Mod.block.custom.pedestals.BasePedestal;
import com.Infinity.Nexus.Mod.block.entity.pedestals.*;
import com.Infinity.Nexus.Mod.recipe.InfuserRecipes;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.SingleMachinesRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InfuserBlockEntity extends BaseBlockEntity {

    //Slots
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    protected final ContainerData data;
    //Misc
    private int progress = 0;
    public int maxProgress = 200;
    public ItemStack recipeOutput = ItemStack.EMPTY;

    public InfuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.INFUSER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> InfuserBlockEntity.this.progress;
                    case 1 -> InfuserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }
            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> InfuserBlockEntity.this.progress = pValue;
                    case 1 -> InfuserBlockEntity.this.maxProgress = pValue;
                }
            }
            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                getLevel().setBlock(worldPosition, getBlockState().setValue(Infuser.LIT, getBlockState().getValue(Infuser.LIT)), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> true;
                case 1 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };


    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = Lazy.of(() -> itemHandler);
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("infuser.progress", progress);

        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("infuser.progress");
    }
    public ItemStack getRenderStack(){
        ItemStack stack = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
        ItemStack input = this.itemHandler.getStackInSlot(INPUT_SLOT);
        if(!stack.isEmpty() && !input.isEmpty()){
            return input;
        }else if(stack.isEmpty() && !input.isEmpty()){
            return input;
        }else if(!stack.isEmpty() && input.isEmpty()){
            return stack;
        }else{
            return ItemStack.EMPTY;
        }
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
        this.stopPedestalAnimation(this.worldPosition);
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }
        if(itemHandler.getStackInSlot(0).isEmpty()){
            if(progress > 0) {
                stopPedestalAnimation(pPos);
                resetProgress();
            }
            if(level.getBlockState(pPos).getValue(Infuser.LIT) != 3){
                this.getLevel().setBlock(pPos, pState.setValue(Infuser.LIT, 3), 3);
            }
            return;
        }

        Optional<RecipeHolder<InfuserRecipes>> recipe = getCurrentRecipe();
        if (!hasRecipe(recipe)) {
            if(progress > 0) {
                stopPedestalAnimation(pPos);
            }
            resetProgress();
            return;
        }
        int[] pedestals = this.getCurrentRecipe().get().value().getPedestals();
        managePedestals(pLevel, pPos, pState, pedestals);

        increaseCraftingProgress();
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem(recipe);
            startPedestalAnimation(pPos, false, pedestals);
            ModUtils.ejectItemsWhePusher(pPos,new int[]{INPUT_SLOT}, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            this.getLevel().setBlock(pPos, pState.setValue(Infuser.LIT, 0), 3);
            resetProgress();
        }
    }

    private void managePedestals(Level pLevel, BlockPos pPos, BlockState pState, int[] pedestals) {
        if(progress == 0){
            startPedestalAnimation(pPos, false, pedestals);
            pLevel.setBlock(pPos, pState.setValue(Infuser.LIT, 1), 3);
        }
        if(progress == 1){
            startPedestalAnimation(pPos, true, pedestals);
        }
        if(progress == 180){
            summonLightning(pPos);
        }
    }

    private void startPedestalAnimation(BlockPos pos, boolean work, int[] pedestals) {

        Map<Integer, BlockPos> pedestalPositions = Map.of(
                1, pos.west(2).south(1),
                2, pos.west(2).north(1),
                3, pos.north(2),
                4, pos.east(2).north(1),
                5, pos.east(2).south(1),
                6, pos.south(2)
        );

        for (int pedestalIndex : pedestals) {
            BlockPos pedestalPos = pedestalPositions.get(pedestalIndex);
            if (pedestalPos != null) {
                level.setBlock(
                        pedestalPos,
                        this.level.getBlockState(pedestalPos).setValue(BasePedestal.WORK, work),
                        3
                );
            }
        }
    }
    private void stopPedestalAnimation(BlockPos pos) {
        List<BlockPos> pedestalPositions = List.of(
                pos.north(2),
                pos.south(2),
                pos.east(2).north(1),
                pos.east(2).south(1),
                pos.west(2).south(1),
                pos.west(2).north(1)
        );

        pedestalPositions.forEach(pedestalPos -> {
            try {
                level.setBlock(pedestalPos, level.getBlockState(pedestalPos).setValue(BasePedestal.WORK, false), 3);
            } catch (Exception ignored) {
            }
        });
    }



    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress ++;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem(Optional<RecipeHolder<InfuserRecipes>> recipe) {
        ItemStack result = recipe.get().value().getResultItem(null);
        ItemStackHandlerUtils.extractItem(0, recipe.get().value().getCount(), false, itemHandler);
        result.setCount(this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount());
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, result);

        SoundUtils.playSound(level, worldPosition,  SoundSource.BLOCKS, SoundEvents.ANVIL_FALL, 0.3f, 1.0f);

    }
    private void summonLightning(BlockPos pos) {
        if (!level.isClientSide()) {
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
            if (lightning != null && !isRedstonePowered(pos)) {
                lightning.moveTo(pos.getX() + 0.5, pos.getY()+1, pos.getZ() + 0.5);
                lightning.setVisualOnly(true);
                lightning.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1.0f, 1.0f);
                level.addFreshEntity(lightning);
            }
        }
    }
    private boolean hasRecipe(Optional<RecipeHolder<InfuserRecipes>> recipe) {

        if (recipe.isEmpty()) {
            recipeOutput = ItemStack.EMPTY;
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());
        recipeOutput = result.copy();

        return ItemStackHandlerUtils.canInsertItemAndAmountIntoOutputSlot(result.getItem(), result.getCount(), OUTPUT_SLOT, itemHandler)
                && hasPedestals(worldPosition, recipe.get().value().getPedestals());
    }


    private boolean hasPedestals(BlockPos pos, int[] pedestals) {
        // Mapear os índices para as posições relativas dos pedestais
        Map<Integer, BlockPos> pedestalPositions = Map.of(
                1, pos.west(2).south(1),  // Tech Pedestal
                2, pos.west(2).north(1),  // Resource Pedestal
                3, pos.north(2),          // Magic Pedestal
                4, pos.east(2).north(1),  // Decor Pedestal
                5, pos.east(2).south(1),  // Creativity Pedestal
                6, pos.south(2)           // Exploration Pedestal
        );

        // Verificar os pedestais necessários na receita
        for (int pedestalIndex : pedestals) {
            BlockPos pedestalPos = pedestalPositions.get(pedestalIndex);
            if (pedestalPos == null) {
                return false; // Posição inválida para o pedestal
            }

            boolean isValid = switch (pedestalIndex) {
                case 1 -> this.level.getBlockEntity(pedestalPos) instanceof TechPedestalBlockEntity;
                case 2 -> this.level.getBlockEntity(pedestalPos) instanceof ResourcePedestalBlockEntity;
                case 3 -> this.level.getBlockEntity(pedestalPos) instanceof MagicPedestalBlockEntity;
                case 4 -> this.level.getBlockEntity(pedestalPos) instanceof DecorPedestalBlockEntity;
                case 5 -> this.level.getBlockEntity(pedestalPos) instanceof CreativityPedestalBlockEntity;
                case 6 -> this.level.getBlockEntity(pedestalPos) instanceof ExplorationPedestalBlockEntity;
                default -> false;
            };

            if (!isValid) {
                return false; // Um dos pedestais necessários não está presente
            }
        }

        return true; // Todos os pedestais necessários estão presentes
    }


    private Optional<RecipeHolder<InfuserRecipes>> getCurrentRecipe() {
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.INFUSER_RECIPE_TYPE.get(), new SingleMachinesRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT)), level);
    }

    public static int getInputSlot() {
        return INPUT_SLOT;
    }

    public static int getOutputSlot() {
        return OUTPUT_SLOT;
    }

    public void removeStack(Player player, int slot) {
        level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), itemHandler.getStackInSlot(slot).copy()));
        this.getLevel().setBlock(this.getBlockPos(), this.getBlockState().setValue(Infuser.LIT, 0), 3);
        this.itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
    }

    public void addStack(ItemStack copy, Player player) {
        if(this.itemHandler.getStackInSlot(getInputSlot()).isEmpty()) {
            this.itemHandler.setStackInSlot(getInputSlot(), copy);
            this.getLevel().setBlock(this.getBlockPos(), this.getBlockState().setValue(Infuser.LIT, 0), 3);
            player.getMainHandItem().setCount(0);
        }
    }
}