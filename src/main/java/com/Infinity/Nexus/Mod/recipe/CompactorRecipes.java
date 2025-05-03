package com.Infinity.Nexus.Mod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public record CompactorRecipes(List<Ingredient> inputItems, ItemStack output)
        implements Recipe<MultipleMachinesRecipeInput> {


    @Override
    public boolean matches(MultipleMachinesRecipeInput multipleMachinesRecipeInput, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        for (int i = 0; i < inputItems.size(); i++) {
            if (!inputItems.get(i).test(multipleMachinesRecipeInput.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return (NonNullList<Ingredient>) inputItems;
    }

    @Override
    public ItemStack assemble(MultipleMachinesRecipeInput multipleMachinesRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }


    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.COMPACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.COMPACTOR_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CompactorRecipes> {
        public static final MapCodec<CompactorRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.listOf(0, 27).fieldOf("ingredients").forGetter(o -> o.inputItems),
                ItemStack.CODEC.fieldOf("output").forGetter(CompactorRecipes::output)
        ).apply(inst, CompactorRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CompactorRecipes> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC, 27),
                CompactorRecipes::inputItems,
                ItemStack.STREAM_CODEC,
                CompactorRecipes::output,
                CompactorRecipes::new
        );

        @Override
        public MapCodec<CompactorRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CompactorRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}