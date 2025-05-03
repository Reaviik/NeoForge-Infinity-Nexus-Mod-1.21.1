package com.Infinity.Nexus.Mod.recipe;

import com.mojang.serialization.Codec;
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

import java.util.ArrayList;
import java.util.List;

public record FactoryRecipes(List<Ingredient> inputItems, ItemStack output, int duration, int energy)
            implements Recipe<MultipleMachinesRecipeInput> {

    @Override
    public boolean matches(MultipleMachinesRecipeInput multipleMachinesRecipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        int matches = 0;
        List<Integer> slotsVerificados = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (inputItems.get(i).test(multipleMachinesRecipeInput.getItem(j)) && !slotsVerificados.contains(j)) {
                    matches++;
                    slotsVerificados.add(j);
                    break;
                }
            }
        }
        return matches == 17;
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
        return ModRecipes.FACTORY_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FACTORY_RECIPE_TYPE.get();
    }

    public int getDuration() {
        return duration;
    }

    public int getEnergy() {
        return energy;
    }


    public static class Serializer implements RecipeSerializer<FactoryRecipes> {

        public static final MapCodec<FactoryRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.listOf(0, 17).fieldOf("ingredients").forGetter(o -> o.inputItems),
                ItemStack.CODEC.fieldOf("output").forGetter(FactoryRecipes::output),
                Codec.INT.fieldOf("duration").forGetter(FactoryRecipes::getDuration),
                Codec.INT.fieldOf("energy").forGetter(FactoryRecipes::getEnergy)
        ).apply(inst, FactoryRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FactoryRecipes> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC, 17),
                FactoryRecipes::inputItems,
                ItemStack.STREAM_CODEC, FactoryRecipes::output,
                ByteBufCodecs.INT, FactoryRecipes::getDuration,
                ByteBufCodecs.INT, FactoryRecipes::getEnergy,
                FactoryRecipes::new
        );

        @Override
        public MapCodec<FactoryRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FactoryRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}