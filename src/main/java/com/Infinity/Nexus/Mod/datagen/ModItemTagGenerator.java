package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> feature,
                               CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, feature, completableFuture, InfinityNexusMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(ModTags.Items.FORGE_INGOTS)
                .add(ModItemsAdditions.SILVER_INGOT.get())
                .add(ModItemsAdditions.TIN_INGOT.get())
                .add(ModItemsAdditions.LEAD_INGOT.get())
                .add(ModItemsAdditions.NICKEL_INGOT.get())
                .add(ModItemsAdditions.ZINC_INGOT.get())
                .add(ModItemsAdditions.BRASS_INGOT.get())
                .add(ModItemsAdditions.BRONZE_INGOT.get())
                .add(ModItemsAdditions.STEEL_INGOT.get())
                .add(ModItemsAdditions.ALUMINUM_INGOT.get())
                .add(ModItemsAdditions.URANIUM_INGOT.get())
                .add(ModItemsAdditions.INFINITY_INGOT.get());

        this.tag(ModTags.Items.FORGE_RAW)
                .add(ModItemsAdditions.RAW_SILVER.get())
                .add(ModItemsAdditions.RAW_TIN.get())
                .add(ModItemsAdditions.RAW_LEAD.get())
                .add(ModItemsAdditions.RAW_NICKEL.get())
                .add(ModItemsAdditions.RAW_ZINC.get())
                .add(ModItemsAdditions.RAW_ALUMINUM.get())
                .add(ModItemsAdditions.RAW_URANIUM.get())
                .add(ModItemsAdditions.RAW_INFINITY.get());

        this.tag(ModTags.Items.FORGE_DUSTS)
                .add(ModItemsAdditions.COPPER_DUST.get())
                .add(ModItemsAdditions.IRON_DUST.get())
                .add(ModItemsAdditions.GOLD_DUST.get())
                .add(ModItemsAdditions.SILVER_DUST.get())
                .add(ModItemsAdditions.TIN_DUST.get())
                .add(ModItemsAdditions.LEAD_DUST.get())
                .add(ModItemsAdditions.NICKEL_DUST.get())
                .add(ModItemsAdditions.ZINC_DUST.get())
                .add(ModItemsAdditions.BRASS_DUST.get())
                .add(ModItemsAdditions.BRONZE_DUST.get())
                .add(ModItemsAdditions.STEEL_DUST.get())
                .add(ModItemsAdditions.ALUMINUM_DUST.get())
                .add(ModItemsAdditions.URANIUM_DUST.get())
                .add(ModItemsAdditions.INFINITY_DUST.get());

        this.tag(ModTags.Items.FORGE_NUGGETS)
                .add(ModItemsAdditions.COPPER_NUGGET.get())
                .add(ModItemsAdditions.SILVER_NUGGET.get())
                .add(ModItemsAdditions.TIN_NUGGET.get())
                .add(ModItemsAdditions.LEAD_NUGGET.get())
                .add(ModItemsAdditions.NICKEL_NUGGET.get())
                .add(ModItemsAdditions.ZINC_NUGGET.get())
                .add(ModItemsAdditions.BRASS_NUGGET.get())
                .add(ModItemsAdditions.BRONZE_NUGGET.get())
                .add(ModItemsAdditions.STEEL_NUGGET.get())
                .add(ModItemsAdditions.ALUMINUM_NUGGET.get())
                .add(ModItemsAdditions.URANIUM_NUGGET.get())
                .add(ModItemsAdditions.INFINITY_NUGGET.get());

        this.tag(ModTags.Items.FORGE_PLATES)
                .add(ModItemsProgression.COPPER_SHEET.get())
                .add(ModItemsProgression.IRON_SHEET.get())
                .add(ModItemsProgression.GOLD_SHEET.get())
                .add(ModItemsProgression.SILVER_SHEET.get())
                .add(ModItemsProgression.TIN_SHEET.get())
                .add(ModItemsProgression.LEAD_SHEET.get())
                .add(ModItemsProgression.NICKEL_SHEET.get())
                .add(ModItemsProgression.ZINC_SHEET.get())
                .add(ModItemsProgression.BRASS_SHEET.get())
                .add(ModItemsProgression.BRONZE_SHEET.get())
                .add(ModItemsProgression.STEEL_SHEET.get())
                .add(ModItemsProgression.ALUMINUM_SHEET.get())
                .add(ModItemsProgression.INDUSTRIAL_SHEET.get())
                .add(ModItemsProgression.INFINITY_SHEET.get());

        this.tag(ModTags.Items.FORGE_ROD)
                .add(ModItemsProgression.COPPER_ROD.get())
                .add(ModItemsProgression.IRON_ROD.get())
                .add(ModItemsProgression.GOLD_ROD.get())
                .add(ModItemsProgression.SILVER_ROD.get())
                .add(ModItemsProgression.TIN_ROD.get())
                .add(ModItemsProgression.LEAD_ROD.get())
                .add(ModItemsProgression.NICKEL_ROD.get())
                .add(ModItemsProgression.ZINC_ROD.get())
                .add(ModItemsProgression.BRASS_ROD.get())
                .add(ModItemsProgression.BRONZE_ROD.get())
                .add(ModItemsProgression.STEEL_ROD.get())
                .add(ModItemsProgression.ALUMINUM_ROD.get())
                .add(ModItemsProgression.INDUSTRIAL_ROD.get())
                .add(ModItemsProgression.INFINITY_ROD.get());

        this.tag(ModTags.Items.FORGE_SCREW)
                .add(ModItemsProgression.COPPER_SCREW.get())
                .add(ModItemsProgression.IRON_SCREW.get())
                .add(ModItemsProgression.GOLD_SCREW.get())
                .add(ModItemsProgression.SILVER_SCREW.get())
                .add(ModItemsProgression.TIN_SCREW.get())
                .add(ModItemsProgression.LEAD_SCREW.get())
                .add(ModItemsProgression.NICKEL_SCREW.get())
                .add(ModItemsProgression.ZINC_SCREW.get())
                .add(ModItemsProgression.BRASS_SCREW.get())
                .add(ModItemsProgression.BRONZE_SCREW.get())
                .add(ModItemsProgression.STEEL_SCREW.get())
                .add(ModItemsProgression.ALUMINUM_SCREW.get())
                .add(ModItemsProgression.INDUSTRIAL_SCREW.get())
                .add(ModItemsProgression.INFINITY_SCREW.get());

        this.tag(ModTags.Items.FORGE_WIRES)
                .add(ModItemsProgression.COPPER_WIRE.get())
                .add(ModItemsProgression.IRON_WIRE.get())
                .add(ModItemsProgression.GOLD_WIRE.get())
                .add(ModItemsProgression.SILVER_WIRE.get())
                .add(ModItemsProgression.TIN_WIRE.get())
                .add(ModItemsProgression.LEAD_WIRE.get())
                .add(ModItemsProgression.NICKEL_WIRE.get())
                .add(ModItemsProgression.ZINC_WIRE.get())
                .add(ModItemsProgression.BRASS_WIRE.get())
                .add(ModItemsProgression.BRONZE_WIRE.get())
                .add(ModItemsProgression.STEEL_WIRE.get())
                .add(ModItemsProgression.ALUMINUM_WIRE.get())
                .add(ModItemsProgression.INDUSTRIAL_WIRE.get())
                .add(ModItemsProgression.INFINITY_WIRE.get());

        this.tag(ModTags.Items.PAXELS)
                .add(ModItemsAdditions.INFINITY_PAXEL.get())
                .add(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL.get());
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
