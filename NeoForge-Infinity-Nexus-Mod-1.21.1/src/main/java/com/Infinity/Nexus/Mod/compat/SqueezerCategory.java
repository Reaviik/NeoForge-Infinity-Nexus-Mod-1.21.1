package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.SqueezerBlockEntity;
import com.Infinity.Nexus.Mod.recipe.SqueezerRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SqueezerCategory implements IRecipeCategory<SqueezerRecipes> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "squeezing");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/jei/squeezer_gui.png");

    public static final RecipeType<SqueezerRecipes> SQUEEZER_TYPE = new RecipeType<>(UID, SqueezerRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SqueezerCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 88);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.SQUEEZER.get()));
    }

    @Override
    public RecipeType<SqueezerRecipes> getRecipeType() {
        return SQUEEZER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.squeezer");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(SqueezerRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font, recipe.getEnergy() + " FE /  "+ (recipe.getDuration()/20) + "s", 6, 76, 0xFFFFFF, false);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SqueezerRecipes recipe, IFocusGroup focuses) {
        ItemStack input =  recipe.getIngredients().get(1).getItems()[0];
        input.setCount(recipe.ingredients().get(1).count());

        builder.addSlot(RecipeIngredientRole.CATALYST, 8, 29).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 11).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 47).addItemStack(recipe.getResultItem(null));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 146, 6).setFluidRenderer(SqueezerBlockEntity.getFluidCapacity(), true,6,62).addFluidStack(recipe.getFluid().getFluid(),recipe.getFluid().getAmount());


    }
}
