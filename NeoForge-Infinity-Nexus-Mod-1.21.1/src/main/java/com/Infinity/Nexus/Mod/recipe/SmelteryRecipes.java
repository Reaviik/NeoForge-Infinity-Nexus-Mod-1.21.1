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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

public record SmelteryRecipes(@Nonnull List<SizedIngredient> inputItems, ItemStack output, int duration, int energy)
            implements Recipe<MultipleMachinesRecipeInput> {

    @Override
    public boolean matches(MultipleMachinesRecipeInput inputs, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        for(int i = 0; i < inputItems.size(); i++) {
            if(!inputItems.get(i).test(inputs.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        inputItems.forEach(i -> list.add(i.ingredient()));
        return list;
    }

    public int getInputCount(int index) {
        return inputItems.get(index).count();
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
        return ModRecipes.SMELTRERY_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SMELTRERY_RECIPE_TYPE.get();
    }

    public int getEnergy() {
        return energy;
    }

    public int getDuration() {
        return duration;
    }


    public static class Serializer implements RecipeSerializer<SmelteryRecipes> {

        public static final MapCodec<SmelteryRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(SmelteryRecipes::inputItems),
                ItemStack.CODEC.fieldOf("output").forGetter(SmelteryRecipes::output),
                Codec.INT.fieldOf("duration").forGetter(SmelteryRecipes::getDuration),
                Codec.INT.fieldOf("energy").forGetter(SmelteryRecipes::getEnergy)
        ).apply(inst, SmelteryRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmelteryRecipes> STREAM_CODEC = StreamCodec.composite(
                SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), SmelteryRecipes::inputItems,
                ItemStack.STREAM_CODEC, SmelteryRecipes::output,
                ByteBufCodecs.INT, SmelteryRecipes::getDuration,
                ByteBufCodecs.INT, SmelteryRecipes::getEnergy,
                SmelteryRecipes::new
        );

        @Override
        public MapCodec<SmelteryRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmelteryRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}