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
import java.util.ArrayList;
import java.util.List;

public record InfuserRecipes(@Nonnull List<SizedIngredient> ingredients, ItemStack output, int[] pedestals)
        implements Recipe<SingleMachinesRecipeInput> {

    @Override
    public boolean matches(SingleMachinesRecipeInput singleMachinesRecipeInput, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        return ingredients.get(0).test(singleMachinesRecipeInput.getItem(0));
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

    public int[] getPedestals() {
        return pedestals;
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
        return ModRecipes.INFUSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.INFUSER_RECIPE_TYPE.get();
    }

    public int getCount() {
        return ingredients.get(0).count();
    }

    public static class Serializer implements RecipeSerializer<InfuserRecipes> {

        public static final MapCodec<InfuserRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(InfuserRecipes::ingredients),
                ItemStack.CODEC.fieldOf("output").forGetter(InfuserRecipes::output),
                Codec.INT.listOf().fieldOf("pedestals").forGetter(o -> {
                    return java.util.Arrays.stream(o.pedestals).boxed().toList();
                })
        ).apply(inst, (ingredients, output, pedestals) ->
                new InfuserRecipes(ingredients, output,  pedestals.stream().mapToInt(i -> i).toArray())
        ));


        public static final StreamCodec<RegistryFriendlyByteBuf, InfuserRecipes> STREAM_CODEC = StreamCodec.composite(
                SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), InfuserRecipes::ingredients,
                ItemStack.STREAM_CODEC, InfuserRecipes::output,
                ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.VAR_INT).map(
                        list -> list.stream().mapToInt(i -> i).toArray(),
                        array -> new ArrayList<>(java.util.Arrays.stream(array).boxed().toList())), InfuserRecipes::pedestals,
                InfuserRecipes::new
        );

        @Override
        public MapCodec<InfuserRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, InfuserRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}