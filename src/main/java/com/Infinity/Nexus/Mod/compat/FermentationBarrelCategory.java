package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.FermentationBarrelBlockEntity;
import com.Infinity.Nexus.Mod.recipe.FermentationBarrelRecipes;
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

public class FermentationBarrelCategory implements IRecipeCategory<FermentationBarrelRecipes> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "fermentation");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/jei/fermentation_barrel_gui.png");

    public static final RecipeType<FermentationBarrelRecipes> FERMENTATION_BARREL_TYPE = new RecipeType<>(UID, FermentationBarrelRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public FermentationBarrelCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 88);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.FERMENTATION_BARREL.get()));
    }

    @Override
    public RecipeType<FermentationBarrelRecipes> getRecipeType() {
        return FERMENTATION_BARREL_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.fermentation_barrel");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(FermentationBarrelRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font,(recipe.getDuration()/20) + "s", 6, 76, 0xFFFFFF, false);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FermentationBarrelRecipes recipe, IFocusGroup focuses) {
        ItemStack input =  recipe.getIngredients().get(0).getItems()[0];
        input.setCount(recipe.getInputCount());
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 6).setFluidRenderer(FermentationBarrelBlockEntity.getInputFluidCapacity(), true,16,62).addFluidStack(recipe.getInputFluidStack().getFluid(),recipe.getInputFluidStack().getAmount());
        builder.addSlot(RecipeIngredientRole.CATALYST, 116, 6).addItemStack(input);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 52).addItemStack(recipe.getResultItem(null));

    }
}