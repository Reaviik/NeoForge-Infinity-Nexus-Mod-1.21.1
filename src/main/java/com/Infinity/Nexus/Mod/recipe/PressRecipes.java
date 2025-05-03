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
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import javax.annotation.Nonnull;
import java.util.List;

public record PressRecipes(@Nonnull List<SizedIngredient> ingredients, ItemStack output, int duration, int energy)
            implements Recipe<MultipleMachinesRecipeInput> {


    @Override
    public boolean matches(MultipleMachinesRecipeInput inputs, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        if(!ingredients.get(0).test(inputs.getItem(0))) {
            return false;
        }

        return ingredients.get(1).test(inputs.getItem(1)) && ingredients.get(2).test(inputs.getItem(2));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        ingredients.forEach(i -> list.add(i.ingredient()));
        return list;
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
        return ModRecipes.PRESS_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.PRESS_RECIPE_TYPE.get();
    }

    public int getDuration() {
        return duration;
    }

    public int getEnergy() {
        return energy;
    }
    public int getInputCount() {
        return ingredients.get(1).count();
    }


    public static class Serializer implements RecipeSerializer<PressRecipes> {

        public static final MapCodec<PressRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(PressRecipes::ingredients),
                ItemStack.CODEC.fieldOf("output").forGetter(PressRecipes::output),
                Codec.INT.fieldOf("duration").forGetter(PressRecipes::getDuration),
                Codec.INT.fieldOf("energy").forGetter(PressRecipes::getEnergy)
        ).apply(inst, PressRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, PressRecipes> STREAM_CODEC = StreamCodec.composite(
                SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), PressRecipes::ingredients,
                ItemStack.STREAM_CODEC, PressRecipes::output,
                ByteBufCodecs.INT, PressRecipes::getDuration,
                ByteBufCodecs.INT, PressRecipes::getEnergy,
                PressRecipes::new
        );

        @Override
        public MapCodec<PressRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, PressRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}