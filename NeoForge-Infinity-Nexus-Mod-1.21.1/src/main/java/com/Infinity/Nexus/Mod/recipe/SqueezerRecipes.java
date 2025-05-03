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
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

public record SqueezerRecipes(@Nonnull List<SizedIngredient> ingredients, ItemStack output, int duration, int energy, FluidStack fluidStack)
            implements Recipe<MultipleMachinesRecipeInput> {

    @Override
    public boolean matches(MultipleMachinesRecipeInput inputs, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        if(!ingredients.get(0).test(inputs.getItem(0))) {
            return false;
        }

        return ingredients.get(1).test(inputs.getItem(1));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        ingredients.forEach(i -> list.add(i.ingredient()));
        return list;
    }

    @Override
    public ItemStack assemble(MultipleMachinesRecipeInput inputs, HolderLookup.Provider provider) {
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
        return ModRecipes.SQUEEZER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SQUEEZER_RECIPE_TYPE.get();
    }
    public int getDuration() {
        return duration;
    }

    public int getEnergy() {
        return energy;
    }

    public FluidStack getFluid() {
        return fluidStack;
    }


    public static class Serializer implements RecipeSerializer<SqueezerRecipes> {

        public static final MapCodec<SqueezerRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(SqueezerRecipes::ingredients),
                ItemStack.CODEC.fieldOf("output").forGetter(SqueezerRecipes::output),
                Codec.INT.fieldOf("duration").forGetter(SqueezerRecipes::getDuration),
                Codec.INT.fieldOf("energy").forGetter(SqueezerRecipes::getEnergy),
                FluidStack.CODEC.fieldOf("fluidType").forGetter(SqueezerRecipes::getFluid)
        ).apply(inst, SqueezerRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SqueezerRecipes> STREAM_CODEC = StreamCodec.composite(
                SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), SqueezerRecipes::ingredients,
                ItemStack.STREAM_CODEC, SqueezerRecipes::output,
                ByteBufCodecs.INT, SqueezerRecipes::getDuration,
                ByteBufCodecs.INT, SqueezerRecipes::getEnergy,
                FluidStack.STREAM_CODEC, SqueezerRecipes::getFluid,
                SqueezerRecipes::new
        );

        @Override
        public MapCodec<SqueezerRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SqueezerRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}