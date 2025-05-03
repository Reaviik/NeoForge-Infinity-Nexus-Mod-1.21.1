package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.recipe.FactoryRecipes;
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
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class FactoryCategory implements IRecipeCategory<FactoryRecipes> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "factory");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/jei/factory_gui.png");

    public static final RecipeType<FactoryRecipes> FACTORY_TYPE = new RecipeType<>(UID, FactoryRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public FactoryCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 145);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.FACTORY.get()));
    }

    @Override
    public RecipeType<FactoryRecipes> getRecipeType() {
        return FACTORY_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.factory");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(FactoryRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font, recipe.getEnergy() + " FE /  "+ (recipe.getDuration()/20) + "s", 6, 133, 0xFFFFFF, false);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FactoryRecipes recipe, IFocusGroup focuses) {

        ItemStack output = recipe.getResultItem(null);
        output.setCount(output.getCount());
        NonNullList<Ingredient> inputs = recipe.getIngredients();
        int[] x = {40, 60, 80, 100, 120,  42, 118,   44, 116,   42, 118,    40, 60, 80, 100, 120};
        int[] y = {16, 18, 20, 18,  16,   36, 36,    56, 56,    76, 76,     96, 94, 92, 94, 96};


        for (int i = 1; i <= 16; i++) {
            Ingredient ingredient = inputs.get(i);
            ItemStack ingredientStack = ingredient.getItems()[0].copy();


            builder.addSlot(RecipeIngredientRole.INPUT, x[i-1], y[i-1]).addItemStack(ingredientStack);
        }

        // Adicionar o slot de saída
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 56).addItemStack(output);

        // Adicionar o slot de catalisador
        builder.addSlot(RecipeIngredientRole.CATALYST, 8, 56).addIngredients(inputs.get(0));
    }
}
