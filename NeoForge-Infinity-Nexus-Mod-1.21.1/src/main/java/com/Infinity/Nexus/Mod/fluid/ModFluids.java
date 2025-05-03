package com.Infinity.Nexus.Mod.fluid;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, InfinityNexusMod.MOD_ID);

    public static final Supplier<FlowingFluid> LUBRICANT_SOURCE = FLUIDS.register("lubricant_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.LUBRICANT_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> LUBRICANT_FLOWING = FLUIDS.register("flowing_lubricant_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.LUBRICANT_FLUID_PROPERTIES));

    public static final Supplier<FlowingFluid> ETHANOL_SOURCE = FLUIDS.register("ethanol_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.ETHANOL_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> ETHANOL_FLOWING = FLUIDS.register("flowing_ethanol_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.ETHANOL_FLUID_PROPERTIES));

    public static final Supplier<FlowingFluid> OIL_SOURCE = FLUIDS.register("oil_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.OIL_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> OIL_FLOWING = FLUIDS.register("flowing_oil_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.OIL_FLUID_PROPERTIES));

    public static final Supplier<FlowingFluid> VINEGAR_SOURCE = FLUIDS.register("vinegar_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.VINEGAR_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> VINEGAR_FLOWING = FLUIDS.register("flowing_vinegar_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.VINEGAR_FLUID_PROPERTIES));

    public static final Supplier<FlowingFluid> SUGARCANE_JUICE_SOURCE = FLUIDS.register("sugarcane_juice_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.SUGARCANE_JUICE_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> SUGARCANE_JUICE_FLOWING = FLUIDS.register("flowing_sugarcane_juice_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.SUGARCANE_JUICE_FLUID_PROPERTIES));

    public static final Supplier<FlowingFluid> WINE_SOURCE = FLUIDS.register("wine_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.WINE_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> WINE_FLOWING = FLUIDS.register("flowing_wine_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.WINE_FLUID_PROPERTIES));

    public static final Supplier<FlowingFluid> EXPERIENCE_SOURCE = FLUIDS.register("experience_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.EXPERIENCE_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> EXPERIENCE_FLOWING = FLUIDS.register("flowing_experience_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.EXPERIENCE_FLUID_PROPERTIES));

    public static final Supplier<FlowingFluid> STARLIQUID_SOURCE = FLUIDS.register("starliquid_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.STARLIQUID_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> STARLIQUID_FLOWING = FLUIDS.register("flowing_starliquid_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.STARLIQUID_FLUID_PROPERTIES));

    public static final Supplier<FlowingFluid> POTATO_JUICE_SOURCE = FLUIDS.register("potato_juice_water_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.POTATO_JUICE_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> POTATO_JUICE_FLOWING = FLUIDS.register("flowing_potato_juice_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.POTATO_JUICE_FLUID_PROPERTIES));

    public static final BaseFlowingFluid.Properties LUBRICANT_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidType.LUBRICANT, LUBRICANT_SOURCE, LUBRICANT_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.LUBRICANT)
            .bucket(ModItemsAdditions.BUCKET_LUBRICANT);
    public static final BaseFlowingFluid.Properties ETHANOL_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidType.ETHANOL, ETHANOL_SOURCE, ETHANOL_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.ETHANOL)
            .bucket(ModItemsAdditions.BUCKET_ETHANOL);
    public static final BaseFlowingFluid.Properties OIL_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidType.OIL, OIL_SOURCE, OIL_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.OIL)
            .bucket(ModItemsAdditions.BUCKET_OIL);
    public static final BaseFlowingFluid.Properties VINEGAR_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidType.VINEGAR, VINEGAR_SOURCE, VINEGAR_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.VINEGAR)
            .bucket(ModItemsAdditions.BUCKET_VINEGAR);
    public static final BaseFlowingFluid.Properties SUGARCANE_JUICE_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidType.SUGARCANE_JUICE, SUGARCANE_JUICE_SOURCE, SUGARCANE_JUICE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.SUGARCANE_JUICE)
            .bucket(ModItemsAdditions.BUCKET_SUGARCANE_JUICE);
    public static final BaseFlowingFluid.Properties WINE_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidType.WINE, WINE_SOURCE, WINE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.WINE)
            .bucket(ModItemsAdditions.BUCKET_WINE);
    public static final BaseFlowingFluid.Properties EXPERIENCE_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidType.EXPERIENCE, EXPERIENCE_SOURCE, EXPERIENCE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.EXPERIENCE)
            .bucket(ModItemsAdditions.BUCKET_EXPERIENCE);
    public static final BaseFlowingFluid.Properties STARLIQUID_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidType.STARLIQUID, STARLIQUID_SOURCE, STARLIQUID_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.STARLIQUID)
            .bucket(ModItemsAdditions.BUCKET_STARLIQUID);
    public static final BaseFlowingFluid.Properties POTATO_JUICE_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidType.POTATO_JUICE, POTATO_JUICE_SOURCE, POTATO_JUICE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocksAdditions.POTATO_JUICE)
            .bucket(ModItemsAdditions.BUCKET_POTATO_JUICE);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
