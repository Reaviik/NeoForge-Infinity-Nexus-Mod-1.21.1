package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.recipe.*;
import com.Infinity.Nexus.Mod.screen.assembler.AssemblerScreen;
import com.Infinity.Nexus.Mod.screen.compactor.CompactorScreen;
import com.Infinity.Nexus.Mod.screen.condenser.CondenserScreen;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherScreen;
import com.Infinity.Nexus.Mod.screen.factory.FactoryScreen;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelScreen;
import com.Infinity.Nexus.Mod.screen.press.PressScreen;
import com.Infinity.Nexus.Mod.screen.smeltery.SmelteryScreen;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        //-----------------------------------------Registry--------------------------------------------------//
        registration.addRecipeCategories(new CrusherCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PressCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AssemblerCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FactoryCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SqueezerCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SmelteryCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MatterCondenserCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FermentationBarrelCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new InfuserCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CompactorCategory(registration.getJeiHelpers().getGuiHelper()));

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        //-----------------------------------------Registry--------------------------------------------------//
        List<CrusherRecipes> crusherRecipes = recipeManager.getAllRecipesFor(ModRecipes.CRUSHER_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<PressRecipes> pressRecipes = recipeManager.getAllRecipesFor(ModRecipes.PRESS_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<AssemblerRecipes> assemblyRecipes = recipeManager.getAllRecipesFor(ModRecipes.ASSEMBLY_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<FactoryRecipes> factoryRecipes = recipeManager.getAllRecipesFor(ModRecipes.FACTORY_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<SqueezerRecipes> squeezerRecipes = recipeManager.getAllRecipesFor(ModRecipes.SQUEEZER_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<SmelteryRecipes> smelteryRecipes = recipeManager.getAllRecipesFor(ModRecipes.SMELTRERY_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<MatterCondenserRecipes> matterCondenserRecipes = recipeManager.getAllRecipesFor(ModRecipes.CONDENSER_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<FermentationBarrelRecipes> fermentationBarrelRecipes = recipeManager.getAllRecipesFor(ModRecipes.FERMENTATION_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<InfuserRecipes> infuserRecipes = recipeManager.getAllRecipesFor(ModRecipes.INFUSER_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<CompactorRecipes> compactorRecipes = recipeManager.getAllRecipesFor(ModRecipes.COMPACTOR_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();

        try {
        registration.addRecipes(CrusherCategory.CRUSHER_TYPE, crusherRecipes);
        System.out.println("Registry: " + crusherRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.crusher"));
        registration.addRecipes(PressCategory.PRESS_TYPE, pressRecipes);
        System.out.println("Registry: " + pressRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.press"));
        registration.addRecipes(AssemblerCategory.ASSEMBLY_TYPE, assemblyRecipes);
        System.out.println("Registry: " + assemblyRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.assembler"));
        registration.addRecipes(FactoryCategory.FACTORY_TYPE, factoryRecipes);
        System.out.println("Registry: " + factoryRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.factory"));
        registration.addRecipes(SqueezerCategory.SQUEEZER_TYPE, squeezerRecipes);
        System.out.println("Registry: " + squeezerRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.squeezer"));
        registration.addRecipes(SmelteryCategory.SMELTERY_TYPE, smelteryRecipes);
        System.out.println("Registry: " + smelteryRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.smeltery"));
        registration.addRecipes(MatterCondenserCategory.MATTER_CONDENSER_TYPE, matterCondenserRecipes);
        System.out.println("Registry: " + matterCondenserRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.matter_condenser"));
        registration.addRecipes(FermentationBarrelCategory.FERMENTATION_BARREL_TYPE, fermentationBarrelRecipes);
        System.out.println("Registry: " + fermentationBarrelRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.fermentation_barrel"));
        registration.addRecipes(InfuserCategory.INFUSER_TYPE, infuserRecipes);
        System.out.println("Registry: " + infuserRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.infuser"));
        registration.addRecipes(CompactorCategory.COMPACTOR_TYPE, compactorRecipes);
        System.out.println("Registry: " + compactorRecipes.size() +" "+ Component.translatable("block.infinity_nexus_mod.compactor"));
        }catch (Exception ignored){
        }
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.CRUSHER.get()), Component.translatable("infinity_nexus_mod.jei_information"));
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.PRESS.get()), Component.translatable("infinity_nexus_mod.jei_information"));
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.ASSEMBLY.get()), Component.translatable("infinity_nexus_mod.jei_information"));
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.FACTORY.get()), Component.translatable("infinity_nexus_mod.jei_information"));
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.SQUEEZER.get()), Component.translatable("infinity_nexus_mod.jei_information"));
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.SMELTERY.get()), Component.translatable("infinity_nexus_mod.jei_information"));
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.GENERATOR.get()), Component.translatable("infinity_nexus_mod.jei_information"));
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.MOB_CRUSHER.get()), Component.translatable("infinity_nexus_mod.jei_information"));
        registration.addItemStackInfo(new ItemStack(ModBlocksAdditions.MATTER_CONDENSER.get()), Component.translatable("infinity_nexus_mod.jei_information"));

        registration.addItemStackInfo(new ItemStack(ModItemsProgression.RESIDUAL_MATTER.get()), Component.literal("Este item é obtido ao destruir items no Reciclador."));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        //TODO
        IModPlugin.super.registerRecipeTransferHandlers(registration);
    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocksAdditions.CRUSHER.get().asItem().getDefaultInstance(), CrusherCategory.CRUSHER_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.PRESS.get().asItem().getDefaultInstance(), PressCategory.PRESS_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.ASSEMBLY.get().asItem().getDefaultInstance(), AssemblerCategory.ASSEMBLY_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.FACTORY.get().asItem().getDefaultInstance(), FactoryCategory.FACTORY_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.SQUEEZER.get().asItem().getDefaultInstance(), SqueezerCategory.SQUEEZER_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.SMELTERY.get().asItem().getDefaultInstance(), SmelteryCategory.SMELTERY_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.MATTER_CONDENSER.get().asItem().getDefaultInstance(), MatterCondenserCategory.MATTER_CONDENSER_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.FERMENTATION_BARREL.get().asItem().getDefaultInstance(), FermentationBarrelCategory.FERMENTATION_BARREL_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.INFUSER.get().asItem().getDefaultInstance(), InfuserCategory.INFUSER_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.COMPACTOR.get().asItem().getDefaultInstance(), CompactorCategory.COMPACTOR_TYPE);
        registration.addRecipeCatalyst(ModBlocksAdditions.COMPACTOR_AUTO.get().asItem().getDefaultInstance(), CompactorCategory.COMPACTOR_TYPE);
    }
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //-----------------------------------------Registry--------------------------------------------------//
       registration.addRecipeClickArea(CrusherScreen.class,162, -10,8,9, CrusherCategory.CRUSHER_TYPE);
       registration.addRecipeClickArea(PressScreen.class,162, -10,8,9, PressCategory.PRESS_TYPE);
       registration.addRecipeClickArea(AssemblerScreen.class,162, -10,8,9, AssemblerCategory.ASSEMBLY_TYPE);
       registration.addRecipeClickArea(FactoryScreen.class,162, -38,8,9, FactoryCategory.FACTORY_TYPE);
       registration.addRecipeClickArea(SqueezerScreen.class,162, -10,8,9, SqueezerCategory.SQUEEZER_TYPE);
       registration.addRecipeClickArea(SmelteryScreen.class,162, -10,8,9, SmelteryCategory.SMELTERY_TYPE);
       registration.addRecipeClickArea(CondenserScreen.class,162, -10,8,9, MatterCondenserCategory.MATTER_CONDENSER_TYPE);
       registration.addRecipeClickArea(FermentationBarrelScreen.class,162, -10,8,9, FermentationBarrelCategory.FERMENTATION_BARREL_TYPE);
       registration.addRecipeClickArea(CompactorScreen.class,162, -10,8,9, CompactorCategory.COMPACTOR_TYPE);
    }

}
