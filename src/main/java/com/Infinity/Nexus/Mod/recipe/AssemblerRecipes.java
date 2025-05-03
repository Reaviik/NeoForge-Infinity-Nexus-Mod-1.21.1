package com.Infinity.Nexus.Mod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
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

public record AssemblerRecipes(NonNullList<Ingredient> inputItems, ItemStack output, int duration, int energy)
        implements Recipe<MultipleMachinesRecipeInput> {


    @Override
    public boolean matches(MultipleMachinesRecipeInput container, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        int matches = 0;
        List<Integer> matchedSlots = new ArrayList<>();

        for (int ingredientIdx = 0; ingredientIdx < inputItems.size(); ingredientIdx++) {
            Ingredient ingredient = inputItems.get(ingredientIdx);

            for (int slot = 0; slot < 9; slot++) {
                if (matchedSlots.contains(slot)) continue;

                if (ingredient.test(container.getItem(slot))) {
                    matchedSlots.add(slot);
                    matches++;
                    break;
                }
            }
        }

        return matches >= 9;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }
    @Override
    public ItemStack assemble(MultipleMachinesRecipeInput container, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ASSEMBLY_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ASSEMBLY_RECIPE_TYPE.get();
    }

    public int getEnergy() {
        return energy;
    }

    public int getDuration() {
        return duration;
    }
    public static class Serializer implements RecipeSerializer<AssemblerRecipes> {
        public static final MapCodec<AssemblerRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(
                        list -> {
                            if (list.size() > 9) {
                                return DataResult.error(() -> "Too many ingredients, max 9");
                            }
                            NonNullList<Ingredient> nl = NonNullList.withSize(9, Ingredient.EMPTY);
                            for (int i = 0; i < list.size(); i++) {
                                nl.set(i, list.get(i));
                            }
                            return DataResult.success(nl);
                        },
                        nl -> DataResult.success(nl.stream().filter(ing -> !ing.isEmpty()).toList())
                ).forGetter(AssemblerRecipes::inputItems),
                ItemStack.CODEC.fieldOf("output").forGetter(AssemblerRecipes::output),
                Codec.INT.fieldOf("duration").forGetter(AssemblerRecipes::duration),
                Codec.INT.fieldOf("energy").forGetter(AssemblerRecipes::energy)
        ).apply(inst, AssemblerRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AssemblerRecipes> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC, 9),
                AssemblerRecipes::inputItems,
                ItemStack.STREAM_CODEC,
                AssemblerRecipes::output,
                ByteBufCodecs.INT,
                AssemblerRecipes::duration,
                ByteBufCodecs.INT,
                AssemblerRecipes::energy,
                AssemblerRecipes::new
        );

        @Override
        public MapCodec<AssemblerRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AssemblerRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}