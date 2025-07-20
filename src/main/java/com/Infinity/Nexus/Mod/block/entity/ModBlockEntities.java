package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.pedestals.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public  static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, InfinityNexusMod.MOD_ID);

    public static final Supplier<BlockEntityType<MobCrusherBlockEntity>> MOBCRUSHER_BE =
            BLOCK_ENTITY.register("mob_crusher_block_entity", () ->
                    BlockEntityType.Builder.of(MobCrusherBlockEntity::new, ModBlocksAdditions.MOB_CRUSHER.get()).build(null));
    public static final Supplier<BlockEntityType<CrusherBlockEntity>> CRUSHER_BE =
            BLOCK_ENTITY.register("crusher_block_entity", () ->
                    BlockEntityType.Builder.of(CrusherBlockEntity::new, ModBlocksAdditions.CRUSHER.get()).build(null));

    public static final Supplier<BlockEntityType<PressBlockEntity>> PRESS_BE =
            BLOCK_ENTITY.register("press_block_entity", () ->
                    BlockEntityType.Builder.of(PressBlockEntity::new, ModBlocksAdditions.PRESS.get()).build(null));

    public static final Supplier<BlockEntityType<AssemblerBlockEntity>> ASSEMBLY_BE =
            BLOCK_ENTITY.register("assembly_block_entity", () ->
                    BlockEntityType.Builder.of(AssemblerBlockEntity::new, ModBlocksAdditions.ASSEMBLY.get()).build(null));

    public static final Supplier<BlockEntityType<FactoryBlockEntity>> FACTORY_BE =
            BLOCK_ENTITY.register("factory_block_entity", () ->
                    BlockEntityType.Builder.of(FactoryBlockEntity::new, ModBlocksAdditions.FACTORY.get()).build(null));

    public static final Supplier<BlockEntityType<SqueezerBlockEntity>> SQUEEZER_BE =
            BLOCK_ENTITY.register("squeezer_block_entity", () ->
                    BlockEntityType.Builder.of(SqueezerBlockEntity::new, ModBlocksAdditions.SQUEEZER.get()).build(null));

    public static final Supplier<BlockEntityType<SmelteryBlockEntity>> SMELTERY_BE =
            BLOCK_ENTITY.register("smeltery_block_entity", () ->
                    BlockEntityType.Builder.of(SmelteryBlockEntity::new, ModBlocksAdditions.SMELTERY.get()).build(null));

    public static final Supplier<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BE =
            BLOCK_ENTITY.register("generator_block_entity", () ->
                    BlockEntityType.Builder.of(GeneratorBlockEntity::new, ModBlocksAdditions.GENERATOR.get()).build(null));

    public static final Supplier<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BE =
            BLOCK_ENTITY.register("fermentation_barrel_block_entity", () ->
                    BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new, ModBlocksAdditions.FERMENTATION_BARREL.get()).build(null));
    public static final Supplier<BlockEntityType<RecyclerBlockEntity>> RECYCLER_BE =
            BLOCK_ENTITY.register("recycler_block_entity", () ->
                    BlockEntityType.Builder.of(RecyclerBlockEntity::new, ModBlocksAdditions.RECYCLER.get()).build(null));
    public static final Supplier<BlockEntityType<MatterCondenserBlockEntity>> MATTER_CONDENSER_BE =
            BLOCK_ENTITY.register("matter_condenser_block_entity", () ->
                    BlockEntityType.Builder.of(MatterCondenserBlockEntity::new, ModBlocksAdditions.MATTER_CONDENSER.get()).build(null));
    public static final Supplier<BlockEntityType<SolarBlockEntity>> SOLAR_BE =
            BLOCK_ENTITY.register("solar_block_entity", () ->
                    BlockEntityType.Builder.of(SolarBlockEntity::new, ModBlocksAdditions.SOLAR.get()).build(null));
    public static final Supplier<BlockEntityType<DisplayBlockEntity>> DISPLAY_BE =
            BLOCK_ENTITY.register("display_block_entity", () ->
                    BlockEntityType.Builder.of(DisplayBlockEntity::new, ModBlocksAdditions.DISPLAY.get()).build(null));
    public static final Supplier<BlockEntityType<PlacerBlockEntity>> PLACER_BE =
            BLOCK_ENTITY.register("placer_block_entity", () ->
                    BlockEntityType.Builder.of(PlacerBlockEntity::new, ModBlocksAdditions.PLACER.get()).build(null));
    public static final Supplier<BlockEntityType<InfuserBlockEntity>> INFUSER_BE =
            BLOCK_ENTITY.register("infuser_block_entity", () ->
                    BlockEntityType.Builder.of(InfuserBlockEntity::new, ModBlocksAdditions.INFUSER.get()).build(null));
    public static final Supplier<BlockEntityType<TechPedestalBlockEntity>> TECH_PEDESTAL_BE =
            BLOCK_ENTITY.register("tech_pedestal_block_entity", () ->
                    BlockEntityType.Builder.of(TechPedestalBlockEntity::new, ModBlocksAdditions.TECH_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<MagicPedestalBlockEntity>> MAGIC_PEDESTAL_BE =
            BLOCK_ENTITY.register("magic_pedestal_block_entity", () ->
                    BlockEntityType.Builder.of(MagicPedestalBlockEntity::new, ModBlocksAdditions.MAGIC_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<ExplorationPedestalBlockEntity>> EXPLORATION_PEDESTAL_BE =
            BLOCK_ENTITY.register("exploration_pedestal_block_entity", () ->
                    BlockEntityType.Builder.of(ExplorationPedestalBlockEntity::new, ModBlocksAdditions.EXPLORATION_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<DecorPedestalBlockEntity>> DECOR_PEDESTAL_BE =
            BLOCK_ENTITY.register("decor_pedestal_block_entity", () ->
                    BlockEntityType.Builder.of(DecorPedestalBlockEntity::new, ModBlocksAdditions.DECOR_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<ResourcePedestalBlockEntity>> RESOURCE_PEDESTAL_BE =
            BLOCK_ENTITY.register("resource_pedestal_block_entity", () ->
                    BlockEntityType.Builder.of(ResourcePedestalBlockEntity::new, ModBlocksAdditions.RESOURCE_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<CreativityPedestalBlockEntity>> CREATIVITY_PEDESTAL_BE =
            BLOCK_ENTITY.register("creativity_pedestal_block_entity", () ->
                    BlockEntityType.Builder.of(CreativityPedestalBlockEntity::new, ModBlocksAdditions.CREATIVITY_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<DepotBlockEntity>> DEPOT_BE =
            BLOCK_ENTITY.register("depot_block_entity", () ->
                    BlockEntityType.Builder.of(DepotBlockEntity::new, ModBlocksAdditions.DEPOT.get()).build(null));

    public static final Supplier<BlockEntityType<DepotStoneBlockEntity>> DEPOT_STONE_BE =
            BLOCK_ENTITY.register("depot_stone_block_entity", () ->
                    BlockEntityType.Builder.of(DepotStoneBlockEntity::new, ModBlocksAdditions.DEPOT_STONE.get()).build(null));

    public static final Supplier<BlockEntityType<TankBlockEntity>> TANK_BE =
            BLOCK_ENTITY.register("tank_block_entity", () ->
                    BlockEntityType.Builder.of(TankBlockEntity::new, ModBlocksAdditions.TANK.get()).build(null));

    public static final Supplier<BlockEntityType<BatteryBlockEntity>> BATTERY_BE =
            BLOCK_ENTITY.register("battery_block_entity", () ->
                    BlockEntityType.Builder.of(BatteryBlockEntity::new, ModBlocksAdditions.BATTERY.get()).build(null));

    public static final Supplier<BlockEntityType<CompactorBlockEntity>> COMPACTOR_BE =
            BLOCK_ENTITY.register("compactor_block_entity", () ->
                    BlockEntityType.Builder.of(CompactorBlockEntity::new, ModBlocksAdditions.COMPACTOR.get()).build(null));

    public static final Supplier<BlockEntityType<CompactorAutoBlockEntity>> COMPACTOR_AUTO_BE =
            BLOCK_ENTITY.register("compactor_auto_block_entity", () ->
                    BlockEntityType.Builder.of(CompactorAutoBlockEntity::new, ModBlocksAdditions.COMPACTOR_AUTO.get()).build(null));

    public static final Supplier<BlockEntityType<TranslocatorItemBlockEntity>> TRASLOCATOR_ITEM_BE =
            BLOCK_ENTITY.register("traslocator_item_block_entity", () ->
                    BlockEntityType.Builder.of(TranslocatorItemBlockEntity::new, ModBlocksAdditions.TRANSLOCATOR_ITEM.get()).build(null));

    public static final Supplier<BlockEntityType<TranslocatorEnergyBlockEntity>> TRASLOCATOR_ENERGY_BE =
            BLOCK_ENTITY.register("traslocator_energy_block_entity", () ->
                    BlockEntityType.Builder.of(TranslocatorEnergyBlockEntity::new, ModBlocksAdditions.TRANSLOCATOR_ENERGY.get()).build(null));

    public static final Supplier<BlockEntityType<TranslocatorFluidBlockEntity>> TRASLOCATOR_FLUID_BE =
            BLOCK_ENTITY.register("traslocator_fluid_block_entity", () ->
                    BlockEntityType.Builder.of(TranslocatorFluidBlockEntity::new, ModBlocksAdditions.TRANSLOCATOR_FLUID.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY.register(eventBus);
    }
}
