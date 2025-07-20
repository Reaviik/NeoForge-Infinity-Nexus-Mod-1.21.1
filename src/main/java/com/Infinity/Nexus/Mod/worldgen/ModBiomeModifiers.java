package com.Infinity.Nexus.Mod.worldgen;

import com.Infinity.Nexus.Core.utils.GetResourceLocation;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> INFINITY_ORE = registerKey("infinity_ore");
    public static final ResourceKey<BiomeModifier> LEAD_ORE = registerKey("lead_ore");
    public static final ResourceKey<BiomeModifier> ALUMINUM_ORE = registerKey("aluminum_ore");
    public static final ResourceKey<BiomeModifier> NICKEL_ORE = registerKey("nickel_ore");
    public static final ResourceKey<BiomeModifier> ZINC_ORE = registerKey("zinc_ore");
    public static final ResourceKey<BiomeModifier> SILVER_ORE = registerKey("silver_ore");
    public static final ResourceKey<BiomeModifier> TIN_ORE = registerKey("tin_ore");
    public static final ResourceKey<BiomeModifier> URANIUM_ORE = registerKey("uranium_ore");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(INFINITY_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.INFINITY_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(LEAD_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LEAD_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ALUMINUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ALUMINUM_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(NICKEL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NICKEL_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ZINC_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZINC_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(SILVER_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SILVER_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(TIN_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TIN_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(URANIUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.URANIUM_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, name));
    }
}
