package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Core.utils.GetResourceLocation;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.recipe.InfuserRecipes;
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
import net.minecraft.world.level.block.Block;

public class InfuserCategory implements IRecipeCategory<InfuserRecipes> {

    public static final ResourceLocation UID = GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, "infuser");
    public static final ResourceLocation TEXTURE = GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/jei/infuser_gui.png");

    public static final RecipeType<InfuserRecipes> INFUSER_TYPE = new RecipeType<>(UID, InfuserRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public InfuserCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 88);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.INFUSER.get()));
    }

    @Override
    public RecipeType<InfuserRecipes> getRecipeType() {
        return INFUSER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.infuser");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }


    @Override
    public void draw(InfuserRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font, "200 Tick's /  "+ "20s", 6, 76, 0xFFFFFF, false);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfuserRecipes recipe, IFocusGroup focuses) {
        ItemStack input =  recipe.getIngredients().get(0).getItems()[0];
        input.setCount(recipe.getCount());

        ItemStack output = recipe.getResultItem(null);
        output.setCount(output.getCount());

        builder.addSlot(RecipeIngredientRole.INPUT, 81, 29).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 143, 29).addItemStack(output);

        Object[][] pedestalMapping = {
                {ModBlocksAdditions.TECH_PEDESTAL.get(), 59, 45},   // Índice 1
                {ModBlocksAdditions.RESOURCE_PEDESTAL.get(), 59, 13}, // Índice 2
                {ModBlocksAdditions.MAGIC_PEDESTAL.get(), 81, 5},    // Índice 3
                {ModBlocksAdditions.DECOR_PEDESTAL.get(), 103, 13},  // Índice 4
                {ModBlocksAdditions.CREATIVITY_PEDESTAL.get(), 103, 45}, // Índice 5
                {ModBlocksAdditions.EXPLORATION_PEDESTAL.get(), 81, 53}  // Índice 6
        };

        // Adicionar slots apenas para os pedestais presentes na receita
        for (int pedestalIndex : recipe.getPedestals()) {
            if (pedestalIndex >= 1 && pedestalIndex <= pedestalMapping.length) {
                Object[] pedestalData = pedestalMapping[pedestalIndex - 1];
                builder.addSlot(RecipeIngredientRole.CATALYST, (int) pedestalData[1], (int) pedestalData[2])
                        .addItemStack(new ItemStack((Block) pedestalData[0]));
            }
        }
    }

}
