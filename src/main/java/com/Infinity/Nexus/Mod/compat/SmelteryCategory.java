package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.recipe.SmelteryRecipes;
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
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class SmelteryCategory implements IRecipeCategory<SmelteryRecipes> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "melting");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/jei/smeltery_gui.png");

    public static final RecipeType<SmelteryRecipes> SMELTERY_TYPE = new RecipeType<>(UID, SmelteryRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SmelteryCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 88);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.SMELTERY.get()));
    }

    @Override
    public RecipeType<SmelteryRecipes> getRecipeType() {
        return SMELTERY_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.smeltery");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(SmelteryRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font, recipe.getEnergy() + " FE /  "+ (recipe.getDuration()/20) + "s", 6, 76, 0xFFFFFF, false);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SmelteryRecipes recipe, IFocusGroup focuses) {
        List<Ingredient> ingredients = recipe.getIngredients();

        builder.addSlot(RecipeIngredientRole.CATALYST, 8, 29)
                .addIngredients(ingredients.get(0));

        int[][] inputCoords = new int[][] {
                {57, 13},
                {80, 6},
                {103, 13}
        };

        for (int i = 1; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            ItemStack[] stacks = ingredient.getItems();

            if (stacks.length == 0) continue;

            ItemStack stack = stacks[0].copy();
            stack.setCount(recipe.getInputCount(i));

            if (i - 1 < inputCoords.length) {
                int x = inputCoords[i - 1][0];
                int y = inputCoords[i - 1][1];
                builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                        .addItemStack(stack);
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 47)
                .addItemStack(recipe.getResultItem(null));
    }

}
