package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.*;
import com.Infinity.Nexus.Mod.entity.ModEntities;
import com.Infinity.Nexus.Mod.entity.client.AsgreonModel;
import com.Infinity.Nexus.Mod.entity.custom.Asgreon;
import com.Infinity.Nexus.Mod.entity.layers.ModModelLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = InfinityNexusMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.ASGREON_LAYER, AsgreonModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FLARON_LAYER, AsgreonModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ASGREON.get(), Asgreon.createAttributes().build());
        event.put(ModEntities.FLARON.get(), Asgreon.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.ASSEMBLY_BE.get(), AssemblerBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.ASSEMBLY_BE.get(), AssemblerBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.ASSEMBLY_BE.get(), AssemblerBlockEntity::getFluidHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.CRUSHER_BE.get(), CrusherBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.CRUSHER_BE.get(), CrusherBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DEPOT_BE.get(), DepotBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DEPOT_STONE_BE.get(), DepotStoneBlockEntity::getItemHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.FACTORY_BE.get(), FactoryBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.FACTORY_BE.get(), FactoryBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.FERMENTATION_BE.get(), FermentationBarrelBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.FERMENTATION_BE.get(), FermentationBarrelBlockEntity::getFluidHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.GENERATOR_BE.get(), GeneratorBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.GENERATOR_BE.get(), GeneratorBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.INFUSER_BE.get(), InfuserBlockEntity::getItemHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.MATTER_CONDENSER_BE.get(), MatterCondenserBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.MATTER_CONDENSER_BE.get(), MatterCondenserBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.MOBCRUSHER_BE.get(), MobCrusherBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.MOBCRUSHER_BE.get(), MobCrusherBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.MOBCRUSHER_BE.get(), MobCrusherBlockEntity::getFluidHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.PLACER_BE.get(), PlacerBlockEntity::getItemHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.PRESS_BE.get(), PressBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.PRESS_BE.get(), PressBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.RECYCLER_BE.get(), RecyclerBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.RECYCLER_BE.get(), RecyclerBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.SMELTERY_BE.get(), SmelteryBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.SMELTERY_BE.get(), SmelteryBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.SOLAR_BE.get(), SolarBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.SOLAR_BE.get(), SolarBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.SQUEEZER_BE.get(), SqueezerBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.SQUEEZER_BE.get(), SqueezerBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.SQUEEZER_BE.get(), SqueezerBlockEntity::getFluidHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.COMPACTOR_AUTO_BE.get(), CompactorAutoBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.COMPACTOR_AUTO_BE.get(), CompactorAutoBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.TANK_BE.get(), TankBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.TANK_BE.get(), TankBlockEntity::getFluidHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.BATTERY_BE.get(), BatteryBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.BATTERY_BE.get(), BatteryBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.TRASLOCATOR_FLUID_BE.get(), TranslocatorFluidBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.TRASLOCATOR_FLUID_BE.get(), TranslocatorFluidBlockEntity::getFluidHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.TRASLOCATOR_ENERGY_BE.get(), TranslocatorEnergyBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.TRASLOCATOR_ENERGY_BE.get(), TranslocatorEnergyBlockEntity::getEnergyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.TRASLOCATOR_ITEM_BE.get(), TranslocatorItemBlockEntity::getItemHandler);
    }

}
