package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.utils.GetNewAABB;
import com.Infinity.Nexus.Mod.block.custom.Compactor;
import com.Infinity.Nexus.Mod.recipe.CompactorRecipes;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.recipe.MultipleMachinesRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;

public class CompactorBlockEntity extends BaseBlockEntity {
    private boolean redstone = false;
    private int progress = 0;
    private int maxProgress = 100;
    AABB aabb = GetNewAABB.getAABB(worldPosition.getX() - 1, worldPosition.getY() - 1, worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 3, worldPosition.getZ() + 2);

    public CompactorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COMPACTOR_BE.get(), pPos, pBlockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        boolean isPowered = isRedstonePowered(pPos);
        if(progress < maxProgress){
            progress++;
        }else {
            progress = 0;
            verifySpace(pLevel, pPos);
        }

        if (isPowered != redstone) {
            redstone = isPowered;
            if (isPowered && verifySpace(pLevel, pPos)) {
                craft(pLevel, pPos);
            }
        }

        setChanged();
    }

    private void craft(Level pLevel, BlockPos pPos) {
        Optional<RecipeHolder<CompactorRecipes>> recipe = getCurrentRecipe();
        if(!recipe.isEmpty()) {
            List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, aabb);
            ServerLevel level = (ServerLevel) getLevel();
            if (!entities.isEmpty()) {
                entities.get(0).remove(Entity.RemovalReason.DISCARDED);
                ItemStack result = recipe.get().value().getResultItem(pLevel.registryAccess());
                ItemEntity output = new ItemEntity(level, pPos.getX(), pPos.getY() + 2, pPos.getZ(), result.copy());
                level.addFreshEntity(output);
                removeBlocks();
            }
        }
    }
    private boolean  verifySpace(Level pLevel, BlockPos pPos) {
        boolean free = true;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                BlockPos adjacentPos = pPos.offset(x, 0, z);
                BlockState adjacentState = pLevel.getBlockState(adjacentPos);
                if (adjacentState.getBlock() != Blocks.BARRIER) {
                    free = false;
                }
            }
        }
        if(free){
            pLevel.setBlock(pPos, this.getBlockState().setValue(Compactor.LIT, 0), 3);
        }else{
            pLevel.setBlock(pPos, this.getBlockState().setValue(Compactor.LIT, 1), 3);
        }
        return free;
    }
    private void removeBlocks() {
        int centerX = this.worldPosition.getX();
        int centerY = this.worldPosition.getY() + 2;
        int centerZ = this.worldPosition.getZ();
        BlockPos center = new BlockPos(centerX, centerY, centerZ);
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y >= -1; y--) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos pos = new BlockPos(centerX + x, centerY + y, centerZ + z);
                    if(!center.equals(pos)) {
                        level.destroyBlock(pos, false);
                    }
                }
            }
        }
    }



    private Optional<RecipeHolder<CompactorRecipes>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(27);
        int centerX = this.worldPosition.getX();
        int centerY = this.worldPosition.getY() + 2;
        int centerZ = this.worldPosition.getZ();
        int index = 0;
        for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    BlockPos pos = new BlockPos(centerX + x, centerY + y, centerZ + z);

                    if (x == 0 && y == 0 && z == 0) {
                        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, aabb);
                        if (!entities.isEmpty()) {
                            ItemStack centerItem = entities.get(0).getItem();
                            if (centerItem.getCount() == 1) {
                                inventory.setItem(index++, centerItem);
                            }
                        }
                    } else {
                        BlockState state = this.level.getBlockState(pos);
                        ItemStack item = new ItemStack(state.getBlock().asItem());
                        inventory.setItem(index++, item);
                    }
                }
            }
        }
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.COMPACTOR_RECIPE_TYPE.get(), new MultipleMachinesRecipeInput(inventory, 27), level);
    }

    @Override
    protected boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos.north()) || this.level.hasNeighborSignal(pPos.south()) || this.level.hasNeighborSignal(pPos.west()) || this.level.hasNeighborSignal(pPos.east());
    }
}