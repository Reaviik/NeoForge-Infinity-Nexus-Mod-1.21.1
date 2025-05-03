package com.Infinity.Nexus.Mod.fluid;

import com.Infinity.Nexus.Core.fluid.BaseFluidType;
import com.Infinity.Nexus.Core.utils.GetResourceLocation;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class ModFluidType {

    public static final ResourceLocation WATER_STILL_RL = GetResourceLocation.withPath("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = GetResourceLocation.withPath("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = GetResourceLocation.withPath("block/water_overlay");
    public static final ResourceLocation STARLIQUID_STILL_RL = GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID,"block/starliquid_source");
    public static final ResourceLocation STARLIQUID_FLOWING_RL = GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID,"block/starliquid_flowing");
    public static final ResourceLocation STARLIQUID_OVERLAY_RL = GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID,"block/starliquid_overlay");


    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, InfinityNexusMod.MOD_ID);


    public static final Supplier<FluidType> LUBRICANT = registryFluidType("lubricant",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA1FFBD26,
            new Vector3f(108f /  255f, 168f /  255f, 212f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(9).density(15)));

    public static final Supplier<FluidType> ETHANOL = registryFluidType("ethanol",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA17AE2FF,
            new Vector3f(224f /  255f, 56f /  255f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));

    public static final Supplier<FluidType> OIL = registryFluidType("oil",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA11E1E1E,
            new Vector3f(224f /  255f, 56f /  255f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(1).density(15)));

    public static final Supplier<FluidType> VINEGAR = registryFluidType("vinegar",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA17F006E,
            new Vector3f(224f /  255f, 56f /  255f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));

    public static final Supplier<FluidType> SUGARCANE_JUICE = registryFluidType("sugarcane_juice",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA1714700,
            new Vector3f(224f /  255f, 56f /  255f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));

    public static final Supplier<FluidType> WINE = registryFluidType("wine",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA14800FF,
            new Vector3f(224f /  255f, 56f /  255f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));

    public static final Supplier<FluidType> EXPERIENCE = registryFluidType("experience",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA13DCE00,
            new Vector3f(224f /  255f, 56f /  255f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).viscosity(3).density(15)));

    public static final Supplier<FluidType> STARLIQUID = registryFluidType("starliquid",
            new BaseFluidType(STARLIQUID_STILL_RL, STARLIQUID_FLOWING_RL, STARLIQUID_OVERLAY_RL, 0xA1FFFFFF,
            new Vector3f(224f /  255f, 56f /  255f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).density(1024).viscosity(1024)));

    public static final Supplier<FluidType> POTATO_JUICE = registryFluidType("potato_juice",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA1683D20,
            new Vector3f(224f /  255f, 56f /  255f, 208f / 255f),
            FluidType.Properties.create().lightLevel(0).density(1024).viscosity(1024)));


    public static Supplier<FluidType> registryFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name,()-> fluidType);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
