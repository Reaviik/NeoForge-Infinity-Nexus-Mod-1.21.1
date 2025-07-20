package com.Infinity.Nexus.Mod.datagen.loot;

import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootTablesAdditions extends BlockLootSubProvider {


    public ModBlockLootTablesAdditions(HolderLookup.Provider pProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pProvider);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get());
        dropSelf(ModBlocksAdditions.INFINITY_BLOCK.get());
        dropSelf(ModBlocksAdditions.RAW_INFINITY_BLOCK.get());
        dropSelf(ModBlocksAdditions.INFINITY_ORE.get());
        dropSelf(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get());

        dropSelf(ModBlocksAdditions.LEAD_BLOCK.get());
        dropSelf(ModBlocksAdditions.RAW_LEAD_BLOCK.get());
        dropSelf(ModBlocksAdditions.LEAD_ORE.get());
        dropSelf(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get());

        dropSelf(ModBlocksAdditions.ALUMINUM_BLOCK.get());
        dropSelf(ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get());
        dropSelf(ModBlocksAdditions.ALUMINUM_ORE.get());
        dropSelf(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get());

        dropSelf(ModBlocksAdditions.TIN_BLOCK.get());
        dropSelf(ModBlocksAdditions.RAW_TIN_BLOCK.get());
        dropSelf(ModBlocksAdditions.TIN_ORE.get());
        dropSelf(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get());

        dropSelf(ModBlocksAdditions.NICKEL_BLOCK.get());
        dropSelf(ModBlocksAdditions.RAW_NICKEL_BLOCK.get());
        dropSelf(ModBlocksAdditions.NICKEL_ORE.get());
        dropSelf(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get());

        dropSelf(ModBlocksAdditions.ZINC_BLOCK.get());
        dropSelf(ModBlocksAdditions.RAW_ZINC_BLOCK.get());
        dropSelf(ModBlocksAdditions.ZINC_ORE.get());
        dropSelf(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get());

        dropSelf(ModBlocksAdditions.SILVER_BLOCK.get());
        dropSelf(ModBlocksAdditions.RAW_SILVER_BLOCK.get());
        dropSelf(ModBlocksAdditions.SILVER_ORE.get());
        dropSelf(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get());

        dropSelf(ModBlocksAdditions.URANIUM_BLOCK.get());
        dropSelf(ModBlocksAdditions.RAW_URANIUM_BLOCK.get());
        dropSelf(ModBlocksAdditions.URANIUM_ORE.get());
        dropSelf(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get());

        dropSelf(ModBlocksAdditions.BRASS_BLOCK.get());
        dropSelf(ModBlocksAdditions.BRONZE_BLOCK.get());
        dropSelf(ModBlocksAdditions.STEEL_BLOCK.get());

        dropSelf(ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get());
        dropSelf(ModBlocksAdditions.EXPLORAR_PORTAL.get());
        dropSelf(ModBlocksAdditions.VOXEL_BLOCK.get());
        dropSelf(ModBlocksAdditions.ASPHALT.get());

        dropSelf(ModBlocksAdditions.MOB_CRUSHER.get());
        dropSelf(ModBlocksAdditions.PRESS.get());
        dropSelf(ModBlocksAdditions.CRUSHER.get());
        dropSelf(ModBlocksAdditions.ASSEMBLY.get());
        dropSelf(ModBlocksAdditions.FACTORY.get());
        dropSelf(ModBlocksAdditions.INFUSER.get());
        dropSelf(ModBlocksAdditions.SQUEEZER.get());
        dropSelf(ModBlocksAdditions.SMELTERY.get());
        dropSelf(ModBlocksAdditions.GENERATOR.get());
        dropSelf(ModBlocksAdditions.FERMENTATION_BARREL.get());
        dropSelf(ModBlocksAdditions.RECYCLER.get());
        dropSelf(ModBlocksAdditions.MATTER_CONDENSER.get());
        dropSelf(ModBlocksAdditions.PLACER.get());
        dropSelf(ModBlocksAdditions.DISPLAY.get());
        dropSelf(ModBlocksAdditions.DEPOT.get());
        dropSelf(ModBlocksAdditions.DEPOT_STONE.get());
        dropSelf(ModBlocksAdditions.COMPACTOR.get());
        dropSelf(ModBlocksAdditions.COMPACTOR_AUTO.get());
        dropSelf(ModBlocksAdditions.TRANSLOCATOR_ITEM.get());
        dropSelf(ModBlocksAdditions.TRANSLOCATOR_ENERGY.get());
        dropSelf(ModBlocksAdditions.TRANSLOCATOR_FLUID.get());

        dropSelf(ModBlocksAdditions.TECH_PEDESTAL.get());
        dropSelf(ModBlocksAdditions.EXPLORATION_PEDESTAL.get());
        dropSelf(ModBlocksAdditions.RESOURCE_PEDESTAL.get());
        dropSelf(ModBlocksAdditions.MAGIC_PEDESTAL.get());
        dropSelf(ModBlocksAdditions.CREATIVITY_PEDESTAL.get());
        dropSelf(ModBlocksAdditions.DECOR_PEDESTAL.get());

        dropSelf(ModBlocksAdditions.CATWALK.get());
        dropSelf(ModBlocksAdditions.CATWALK_2.get());
        dropSelf(ModBlocksAdditions.CATWALK_3.get());
        dropSelf(ModBlocksAdditions.CATWALK_4.get());
        dropSelf(ModBlocksAdditions.CATWALK_5.get());
        dropSelf(ModBlocksAdditions.CATWALK_6.get());
        dropSelf(ModBlocksAdditions.CATWALK_7.get());
        dropSelf(ModBlocksAdditions.CATWALK_8.get());
        dropSelf(ModBlocksAdditions.CATWALK_9.get());
        dropSelf(ModBlocksAdditions.CATWALK_10.get());
        dropSelf(ModBlocksAdditions.CATWALK_11.get());
        dropSelf(ModBlocksAdditions.CATWALK_12.get());
        dropSelf(ModBlocksAdditions.CATWALK_13.get());
        dropSelf(ModBlocksAdditions.CATWALK_14.get());
        dropSelf(ModBlocksAdditions.CATWALK_15.get());
        dropSelf(ModBlocksAdditions.CATWALK_16.get());
        dropSelf(ModBlocksAdditions.CATWALK_17.get());
        dropSelf(ModBlocksAdditions.CATWALK_18.get());

        dropSelf(ModBlocksProgression.SILVER_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.TIN_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.NICKEL_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.BRONZE_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.BRASS_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.LEAD_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.ALUMINUM_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.PLASTIC_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.GLASS_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.STEEL_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get());
        dropSelf(ModBlocksProgression.INFINITY_MACHINE_CASING.get());


        add(ModBlocksAdditions.INFINITY_ORE.get(), block -> createOreDrop( ModBlocksAdditions.INFINITY_ORE.get(), ModItemsAdditions.RAW_INFINITY.get()));
        add(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get(), ModItemsAdditions.RAW_INFINITY.get()));

        add(ModBlocksAdditions.LEAD_ORE.get(), block -> createOreDrop( ModBlocksAdditions.LEAD_ORE.get(), ModItemsAdditions.RAW_LEAD.get()));
        add(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get(), ModItemsAdditions.RAW_LEAD.get()));

        add(ModBlocksAdditions.ALUMINUM_ORE.get(), block -> createOreDrop( ModBlocksAdditions.ALUMINUM_ORE.get(), ModItemsAdditions.RAW_ALUMINUM.get()));
        add(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get(), ModItemsAdditions.RAW_ALUMINUM.get()));

        add(ModBlocksAdditions.TIN_ORE.get(), block -> createOreDrop( ModBlocksAdditions.TIN_ORE.get(), ModItemsAdditions.RAW_TIN.get()));
        add(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_TIN_ORE.get(), ModItemsAdditions.RAW_TIN.get()));

        add(ModBlocksAdditions.ZINC_ORE.get(), block -> createOreDrop( ModBlocksAdditions.ZINC_ORE.get(), ModItemsAdditions.RAW_ZINC.get()));
        add(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get(), ModItemsAdditions.RAW_ZINC.get()));

        add(ModBlocksAdditions.NICKEL_ORE.get(), block -> createOreDrop( ModBlocksAdditions.NICKEL_ORE.get(), ModItemsAdditions.RAW_NICKEL.get()));
        add(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get(), ModItemsAdditions.RAW_NICKEL.get()));

        add(ModBlocksAdditions.SILVER_ORE.get(), block -> createOreDrop( ModBlocksAdditions.SILVER_ORE.get(), ModItemsAdditions.RAW_SILVER.get()));
        add(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get(), ModItemsAdditions.RAW_SILVER.get()));

        add(ModBlocksAdditions.URANIUM_ORE.get(), block -> createOreDrop( ModBlocksAdditions.URANIUM_ORE.get(), ModItemsAdditions.RAW_URANIUM.get()));
        add(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get(), ModItemsAdditions.RAW_URANIUM.get()));

        dropSelf(ModBlocksAdditions.ENTITY_CENTRALIZER.get());
        dropSelf(ModBlocksAdditions.ENTITY_DISPLAY.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        Stream<Block> additions = ModBlocksAdditions.BLOCKS.getEntries().stream().map(Holder::value);
        Stream<Block> progression = ModBlocksProgression.BLOCKS.getEntries().stream().map(Holder::value);


        return Stream.concat(additions, progression)::iterator;
    }
}
