package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.recipe.MatterCondenserRecipes;
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
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MatterCondenserCategory implements IRecipeCategory<MatterCondenserRecipes> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "matter_condenser");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/jei/matter_condenser_gui.png");

    public static final RecipeType<MatterCondenserRecipes> MATTER_CONDENSER_TYPE = new RecipeType<>(UID, MatterCondenserRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MatterCondenserCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 88);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.MATTER_CONDENSER.get()));
    }

    @Override
    public RecipeType<MatterCondenserRecipes> getRecipeType() {
        return MATTER_CONDENSER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.matter_condenser");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(MatterCondenserRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font, recipe.getEnergy() + " FE", 6, 76, 0xFFFFFF, false);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MatterCondenserRecipes recipe, IFocusGroup focuses) {
        ItemStack catalyst =  recipe.getIngredients().get(0).getItems()[0];
        ItemStack input = recipe.getIngredients().get(1).getItems()[0];
        String name = input.getItem().getDefaultInstance().getHoverName().getString();
        input.set(DataComponents.ITEM_NAME, Component.literal(name).append(" [Optional]"));

        builder.addSlot(RecipeIngredientRole.CATALYST, 8, 29).addItemStack(catalyst);
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 52).addItemStack(input);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 7).addItemStack(recipe.getResultItem(null));
    }
}