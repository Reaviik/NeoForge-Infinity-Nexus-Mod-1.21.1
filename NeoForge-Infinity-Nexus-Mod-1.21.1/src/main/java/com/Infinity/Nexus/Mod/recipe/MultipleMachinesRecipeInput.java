package com.Infinity.Nexus.Mod.recipe;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MultipleMachinesRecipeInput(SimpleContainer stacks, int size) implements RecipeInput {

    @Override
    public ItemStack getItem(int pIndex) {
        if(pIndex > stacks.getItems().size()) {
            return ItemStack.EMPTY;
        }
        return stacks.getItem(pIndex);
    }

    @Override
    public int size() {
        return size;
    }
}
