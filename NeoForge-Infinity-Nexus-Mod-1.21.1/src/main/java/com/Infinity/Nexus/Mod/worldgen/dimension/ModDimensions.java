package com.Infinity.Nexus.Mod.worldgen.dimension;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class ModDimensions {
    public static final ResourceKey<LevelStem> EXPLORAR_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "explorar"));
    public static final ResourceKey<Level> EXPLORAR_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "explorar"));
    public static final ResourceKey<DimensionType> EXPLORAR_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "explorar_type"));
}
