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


public record CrusherRecipes(int energy, int duration, boolean canDuplicate, @Nonnull List<SizedIngredient> ingredients, ItemStack output)
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
        return ModRecipes.CRUSHER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CRUSHER_RECIPE_TYPE.get();
    }

    public int getEnergy() {
        return energy;
    }

    public int getDuration() {
        return duration;
    }

    public boolean canDuplicate() {
        return canDuplicate;
    }

    public int getCount() {
        return ingredients.get(1).count();
    }

    public static class Serializer implements RecipeSerializer<CrusherRecipes> {
        public static final MapCodec<CrusherRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.INT.fieldOf("energy").forGetter(CrusherRecipes::getEnergy),
                Codec.INT.fieldOf("duration").forGetter(CrusherRecipes::getDuration),
                Codec.BOOL.fieldOf("can_duplicate").forGetter(CrusherRecipes::canDuplicate),
                SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(CrusherRecipes::ingredients),
                ItemStack.CODEC.fieldOf("output").forGetter(CrusherRecipes::output)
        ).apply(inst, CrusherRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrusherRecipes> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, CrusherRecipes::getEnergy,
                ByteBufCodecs.INT, CrusherRecipes::getDuration,
                ByteBufCodecs.BOOL, CrusherRecipes::canDuplicate,
                SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), CrusherRecipes::ingredients,
                ItemStack.STREAM_CODEC, CrusherRecipes::output,
                CrusherRecipes::new
        );

        @Override
        public MapCodec<CrusherRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrusherRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}