package com.Infinity.Nexus.Mod.worldgen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> INFINITY_ORE = registerKey("infinity_ore_placed");
    public static final ResourceKey<PlacedFeature> LEAD_ORE = registerKey("lead_ore_placed");
    public static final ResourceKey<PlacedFeature> ALUMINUM_ORE = registerKey("aluminum_ore_placed");
    public static final ResourceKey<PlacedFeature> NICKEL_ORE = registerKey("nickel_ore_placed");
    public static final ResourceKey<PlacedFeature> ZINC_ORE = registerKey("zinc_ore_placed");
    public static final ResourceKey<PlacedFeature> SILVER_ORE = registerKey("silver_ore_placed");
    public static final ResourceKey<PlacedFeature> TIN_ORE = registerKey("tin_ore_placed");
    public static final ResourceKey<PlacedFeature> URANIUM_ORE = registerKey("uranium_ore_placed");


    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, INFINITY_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_INFINITY_ORE),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, LEAD_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_LEAD_ORE),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, ALUMINUM_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ALUMINUM_ORE),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, NICKEL_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_NICKEL_ORE),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, ZINC_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ZINC_ORE),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, SILVER_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_SILVER_ORE),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, TIN_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_TIN_ORE),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, URANIUM_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_URANIUM_ORE),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
