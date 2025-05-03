package com.Infinity.Nexus.Mod.worldgen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_INFINITY_ORE = registerKey("overworld_infinity_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_LEAD_ORE = registerKey("overworld_lead_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_ALUMINUM_ORE = registerKey("overworld_aluminum_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_NICKEL_ORE = registerKey("overworld_nickel_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_ZINC_ORE = registerKey("overworld_zinc_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_SILVER_ORE = registerKey("overworld_silver_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_TIN_ORE = registerKey("overworld_tin_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_URANIUM_ORE = registerKey("overworld_uranium_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceabeles = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceabeles = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldInfinityOres = List.of(
                OreConfiguration.target(stoneReplaceabeles, ModBlocksAdditions.INFINITY_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceabeles, ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldLeadOres = List.of(
            OreConfiguration.target(stoneReplaceabeles, ModBlocksAdditions.LEAD_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceabeles, ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldAluminumOres = List.of(
            OreConfiguration.target(stoneReplaceabeles, ModBlocksAdditions.ALUMINUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceabeles, ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldNickelOres = List.of(
            OreConfiguration.target(stoneReplaceabeles, ModBlocksAdditions.NICKEL_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceabeles, ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldZincOres = List.of(
            OreConfiguration.target(stoneReplaceabeles, ModBlocksAdditions.ZINC_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceabeles, ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldSilverOres = List.of(
            OreConfiguration.target(stoneReplaceabeles, ModBlocksAdditions.SILVER_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceabeles, ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldTinOres = List.of(
            OreConfiguration.target(stoneReplaceabeles, ModBlocksAdditions.TIN_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceabeles, ModBlocksAdditions.DEEPSLATE_TIN_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldUraniumOres = List.of(
            OreConfiguration.target(stoneReplaceabeles, ModBlocksAdditions.URANIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceabeles, ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get().defaultBlockState())
        );


        register(context, OVERWORLD_INFINITY_ORE, Feature.ORE, new OreConfiguration(overworldInfinityOres, 4));
        register(context, OVERWORLD_LEAD_ORE, Feature.ORE, new OreConfiguration(overworldLeadOres, 9));
        register(context, OVERWORLD_ALUMINUM_ORE, Feature.ORE, new OreConfiguration(overworldAluminumOres, 9));
        register(context, OVERWORLD_NICKEL_ORE, Feature.ORE, new OreConfiguration(overworldNickelOres, 9));
        register(context, OVERWORLD_ZINC_ORE, Feature.ORE, new OreConfiguration(overworldZincOres, 9));
        register(context, OVERWORLD_SILVER_ORE, Feature.ORE, new OreConfiguration(overworldSilverOres, 9));
        register(context, OVERWORLD_TIN_ORE, Feature.ORE, new OreConfiguration(overworldTinOres, 9));
        register(context, OVERWORLD_URANIUM_ORE, Feature.ORE, new OreConfiguration(overworldUraniumOres, 9));

    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
