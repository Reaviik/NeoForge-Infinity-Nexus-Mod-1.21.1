package com.Infinity.Nexus.Mod.recipe;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record MultipleMachinesRecipeInputWithFluid(SimpleContainer stacks, int ItemSize, List<FluidStack> fluids, int fluidSize) implements RecipeInput {

    @Override
    public ItemStack getItem(int pIndex) {
        if(pIndex > stacks.getItems().size()) {
            return ItemStack.EMPTY;
        }
        return stacks.getItem(pIndex);
    }

    @Override
    public int size() {
        return ItemSize;
    }

    public FluidStack getFluid(int pIndex) {
        if(pIndex > fluids.size()) {
            return FluidStack.EMPTY;
        }
        return fluids.get(pIndex);
    }

    public int getFluidSize() {
        return fluidSize;
    }
}
