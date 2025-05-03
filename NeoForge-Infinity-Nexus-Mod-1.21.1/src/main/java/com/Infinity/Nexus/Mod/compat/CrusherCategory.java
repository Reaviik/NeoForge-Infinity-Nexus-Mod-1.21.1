package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.recipe.CrusherRecipes;
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

public class CrusherCategory implements IRecipeCategory<CrusherRecipes> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "crushing");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/jei/crusher_gui.png");

    public static final RecipeType<CrusherRecipes> CRUSHER_TYPE = new RecipeType<>(UID, CrusherRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CrusherCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 88);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.CRUSHER.get()));
    }

    @Override
    public RecipeType<CrusherRecipes> getRecipeType() {
        return CRUSHER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.crusher");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(CrusherRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font, recipe.getEnergy() + " FE /  "+ (recipe.getDuration()/20) + "s §6Duplicate: " + recipe.canDuplicate() , 6, 76, 0xFFFFFF, false);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrusherRecipes recipe, IFocusGroup focuses) {
        ItemStack input =  recipe.getIngredients().get(1).getItems()[0];
        input.setCount(recipe.getCount());

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 11).addItemStack(input);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 47).addItemStack(recipe.getResultItem(null));

        builder.addSlot(RecipeIngredientRole.CATALYST, 8, 29).addIngredients(recipe.getIngredients().get(0));
    }
}
