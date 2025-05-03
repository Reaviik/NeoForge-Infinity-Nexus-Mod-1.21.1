package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, InfinityNexusMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        blockWithItem(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK);

        blockWithItem(ModBlocksAdditions.INFINITY_BLOCK);
        blockWithItem(ModBlocksAdditions.INFINITY_ORE);
        blockWithItem(ModBlocksAdditions.RAW_INFINITY_BLOCK);
        blockWithItem(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE);

        blockWithItem(ModBlocksAdditions.LEAD_BLOCK);
        blockWithItem(ModBlocksAdditions.LEAD_ORE);
        blockWithItem(ModBlocksAdditions.DEEPSLATE_LEAD_ORE);
        blockWithItem(ModBlocksAdditions.RAW_LEAD_BLOCK);

        blockWithItem(ModBlocksAdditions.TIN_BLOCK);
        blockWithItem(ModBlocksAdditions.TIN_ORE);
        blockWithItem(ModBlocksAdditions.DEEPSLATE_TIN_ORE);
        blockWithItem(ModBlocksAdditions.RAW_TIN_BLOCK);

        blockWithItem(ModBlocksAdditions.SILVER_BLOCK);
        blockWithItem(ModBlocksAdditions.SILVER_ORE);
        blockWithItem(ModBlocksAdditions.DEEPSLATE_SILVER_ORE);
        blockWithItem(ModBlocksAdditions.RAW_SILVER_BLOCK);

        blockWithItem(ModBlocksAdditions.NICKEL_BLOCK);
        blockWithItem(ModBlocksAdditions.NICKEL_ORE);
        blockWithItem(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE);
        blockWithItem(ModBlocksAdditions.RAW_NICKEL_BLOCK);

        blockWithItem(ModBlocksAdditions.ZINC_BLOCK);
        blockWithItem(ModBlocksAdditions.ZINC_ORE);
        blockWithItem(ModBlocksAdditions.DEEPSLATE_ZINC_ORE);
        blockWithItem(ModBlocksAdditions.RAW_ZINC_BLOCK);

        blockWithItem(ModBlocksAdditions.ALUMINUM_BLOCK);
        blockWithItem(ModBlocksAdditions.ALUMINUM_ORE);
        blockWithItem(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE);
        blockWithItem(ModBlocksAdditions.RAW_ALUMINUM_BLOCK);

        blockWithItem(ModBlocksAdditions.URANIUM_BLOCK);
        blockWithItem(ModBlocksAdditions.URANIUM_ORE);
        blockWithItem(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE);
        blockWithItem(ModBlocksAdditions.RAW_URANIUM_BLOCK);

        blockWithItem(ModBlocksAdditions.BRASS_BLOCK);
        blockWithItem(ModBlocksAdditions.BRONZE_BLOCK);
        blockWithItem(ModBlocksAdditions.STEEL_BLOCK);

        blockWithItem(ModBlocksAdditions.ASPHALT);



    }

    private void blockWithItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
