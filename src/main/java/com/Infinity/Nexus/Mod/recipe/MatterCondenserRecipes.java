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

public record MatterCondenserRecipes(@Nonnull List<SizedIngredient> ingredients, ItemStack output, int energy)
            implements Recipe<SingleMachinesRecipeInput> {


    @Override
    public boolean matches(SingleMachinesRecipeInput singleMachinesRecipeInput, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        return ingredients.get(0).test(singleMachinesRecipeInput.getItem(2));
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        ingredients.forEach(i -> list.add(i.ingredient()));
        return list;
    }

    @Override
    public ItemStack assemble(SingleMachinesRecipeInput singleMachinesRecipeInput, HolderLookup.Provider provider) {
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

    public int getEnergy() {
        return energy;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CONDENSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CONDENSER_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MatterCondenserRecipes> {

        public static final MapCodec<MatterCondenserRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(MatterCondenserRecipes::ingredients),
                ItemStack.CODEC.fieldOf("output").forGetter(MatterCondenserRecipes::output),
                Codec.INT.fieldOf("energy").forGetter(MatterCondenserRecipes::getEnergy)
        ).apply(inst, MatterCondenserRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MatterCondenserRecipes> STREAM_CODEC = StreamCodec.composite(
                SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), MatterCondenserRecipes::ingredients,
                ItemStack.STREAM_CODEC, MatterCondenserRecipes::output,
                ByteBufCodecs.INT, MatterCondenserRecipes::getEnergy,
                MatterCondenserRecipes::new
        );

        @Override
        public MapCodec<MatterCondenserRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MatterCondenserRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}