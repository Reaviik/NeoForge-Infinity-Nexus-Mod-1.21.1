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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

public record FermentationBarrelRecipes(@Nonnull List<SizedIngredient> inputItems, FluidStack inputFluidStack, ItemStack output, int duration)
        implements Recipe<MultipleMachinesRecipeInputWithFluid> {

    @Override
    public boolean matches(MultipleMachinesRecipeInputWithFluid multipleMachinesRecipeInputWithFluid, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        boolean isSameFluid = multipleMachinesRecipeInputWithFluid.getFluid(0).getFluid() == inputFluidStack.getFluid();
        boolean fluidAmount = multipleMachinesRecipeInputWithFluid.getFluid(0).getAmount() >= inputFluidStack.getAmount();
        if(!isSameFluid && !fluidAmount) {
            return false;
        }
        return inputItems.get(0).test(multipleMachinesRecipeInputWithFluid.getItem(0));
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        inputItems.forEach(i -> list.add(i.ingredient()));
        return list;
    }

    @Override
    public ItemStack assemble(MultipleMachinesRecipeInputWithFluid multipleMachinesRecipeInputWithFluid, HolderLookup.Provider provider) {
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
        return ModRecipes.FERMENTATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FERMENTATION_RECIPE_TYPE.get();
    }

    public FluidStack getInputFluidStack() {
        return inputFluidStack;
    }

    public int getDuration() {
        return duration;
    }

    public int getInputCount() {
        return inputItems.getFirst().count();
    }

    public static class Serializer implements RecipeSerializer<FermentationBarrelRecipes> {

        public static final MapCodec<FermentationBarrelRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(FermentationBarrelRecipes::inputItems),
                FluidStack.CODEC.fieldOf("fluidInput").forGetter(FermentationBarrelRecipes::getInputFluidStack),
                ItemStack.CODEC.fieldOf("output").forGetter(FermentationBarrelRecipes::output),
                Codec.INT.fieldOf("duration").forGetter(FermentationBarrelRecipes::duration)
        ).apply(inst, FermentationBarrelRecipes::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FermentationBarrelRecipes> STREAM_CODEC = StreamCodec.composite(
                SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), FermentationBarrelRecipes::inputItems,
                FluidStack.STREAM_CODEC, FermentationBarrelRecipes::getInputFluidStack,
                ItemStack.STREAM_CODEC, FermentationBarrelRecipes::output,
                ByteBufCodecs.INT, FermentationBarrelRecipes::duration,
                FermentationBarrelRecipes::new
        );

        @Override
        public MapCodec<FermentationBarrelRecipes> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FermentationBarrelRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}