package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, InfinityNexusMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItemsAdditions.INFINITY_INGOT.get());
        basicItem(ModItemsAdditions.LEAD_INGOT.get());
        basicItem(ModItemsAdditions.ALUMINUM_INGOT.get());
        basicItem(ModItemsAdditions.NICKEL_INGOT.get());
        basicItem(ModItemsAdditions.ZINC_INGOT.get());
        basicItem(ModItemsAdditions.SILVER_INGOT.get());
        basicItem(ModItemsAdditions.TIN_INGOT.get());
        basicItem(ModItemsAdditions.INVAR_INGOT.get());
        basicItem(ModItemsAdditions.URANIUM_INGOT.get());
        basicItem(ModItemsAdditions.BRASS_INGOT.get());
        basicItem(ModItemsAdditions.BRONZE_INGOT.get());
        basicItem(ModItemsAdditions.STEEL_INGOT.get());
        basicItem(ModItemsAdditions.GRAPHITE_INGOT.get());
        basicItem(ModItemsAdditions.IRIDIUM_INGOT.get());

        basicItem(ModItemsAdditions.INFINITY_NUGGET.get());
        basicItem(ModItemsAdditions.COPPER_NUGGET.get());
        basicItem(ModItemsAdditions.LEAD_NUGGET.get());
        basicItem(ModItemsAdditions.ALUMINUM_NUGGET.get());
        basicItem(ModItemsAdditions.NICKEL_NUGGET.get());
        basicItem(ModItemsAdditions.ZINC_NUGGET.get());
        basicItem(ModItemsAdditions.SILVER_NUGGET.get());
        basicItem(ModItemsAdditions.TIN_NUGGET.get());
        basicItem(ModItemsAdditions.INVAR_NUGGET.get());
        basicItem(ModItemsAdditions.URANIUM_NUGGET.get());
        basicItem(ModItemsAdditions.BRASS_NUGGET.get());
        basicItem(ModItemsAdditions.BRONZE_NUGGET.get());
        basicItem(ModItemsAdditions.STEEL_NUGGET.get());

        basicItem(ModItemsAdditions.INFINIUM_STELLARUM_INGOT.get());
        basicItem(ModItemsAdditions.INFINITY_DUST.get());
        basicItem(ModItemsAdditions.IRON_DUST.get());
        basicItem(ModItemsAdditions.COPPER_DUST.get());
        basicItem(ModItemsAdditions.GOLD_DUST.get());
        basicItem(ModItemsAdditions.LEAD_DUST.get());
        basicItem(ModItemsAdditions.ALUMINUM_DUST.get());
        basicItem(ModItemsAdditions.NICKEL_DUST.get());
        basicItem(ModItemsAdditions.ZINC_DUST.get());
        basicItem(ModItemsAdditions.SILVER_DUST.get());
        basicItem(ModItemsAdditions.TIN_DUST.get());
        basicItem(ModItemsAdditions.INVAR_DUST.get());
        basicItem(ModItemsAdditions.URANIUM_DUST.get());
        basicItem(ModItemsAdditions.BRASS_DUST.get());
        basicItem(ModItemsAdditions.BRONZE_DUST.get());
        basicItem(ModItemsAdditions.STEEL_DUST.get());
        basicItem(ModItemsAdditions.GRAPHITE_DUST.get());
        basicItem(ModItemsAdditions.DIAMOND_DUST.get());

        basicItem(ModItemsAdditions.RAW_INFINITY.get());
        basicItem(ModItemsAdditions.RAW_LEAD.get());
        basicItem(ModItemsAdditions.RAW_ALUMINUM.get());
        basicItem(ModItemsAdditions.RAW_NICKEL.get());
        basicItem(ModItemsAdditions.RAW_ZINC.get());
        basicItem(ModItemsAdditions.RAW_SILVER.get());
        basicItem(ModItemsAdditions.RAW_TIN.get());
        basicItem(ModItemsAdditions.RAW_URANIUM.get());
        basicItem(ModItemsAdditions.RAW_ALUMINUM.get());
        basicItem(ModItemsAdditions.RAW_NICKEL.get());
        basicItem(ModItemsAdditions.RAW_SILVER.get());
        basicItem(ModItemsAdditions.RAW_URANIUM.get());

        basicItem(ModItemsAdditions.INFINITY_SINGULARITY.get());
        basicItem(ModItemsAdditions.ITEM_DISLOCATOR.get());
        basicItem(ModItemsAdditions.PORTAL_ACTIVATOR.get());
        handheldItem(ModItemsAdditions.INFINITY_SWORD.get());
        simpletools(ModItemsAdditions.INFINITY_HAMMER);
        simpletools(ModItemsAdditions.INFINITY_PAXEL);
        simpletools(ModItemsAdditions.INFINITY_PICKAXE);
        simpletools(ModItemsAdditions.INFINITY_AXE);
        simpletools(ModItemsAdditions.INFINITY_SHOVEL);
        simpletools(ModItemsAdditions.INFINITY_HOE);
        basicItem(ModItemsAdditions.INFINITY_HELMET.get());
        basicItem(ModItemsAdditions.INFINITY_CHESTPLATE.get());
        basicItem(ModItemsAdditions.INFINITY_LEGGINGS.get());
        basicItem(ModItemsAdditions.INFINITY_BOOTS.get());
        basicItem(ModItemsAdditions.CARBON_HELMET.get());
        basicItem(ModItemsAdditions.CARBON_CHESTPLATE.get());
        basicItem(ModItemsAdditions.CARBON_LEGGINGS.get());
        basicItem(ModItemsAdditions.CARBON_BOOTS.get());
        handheldItem(ModItemsAdditions.CARBON_SWORD.get());
        simpletools(ModItemsAdditions.CARBON_PICKAXE);
        simpletools(ModItemsAdditions.CARBON_AXE);
        simpletools(ModItemsAdditions.CARBON_SHOVEL);
        simpletools(ModItemsAdditions.CARBON_HOE);
        handheldItem(ModItemsAdditions.IMPERIAL_INFINITY_SWORD.get());
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_HAMMER);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_AXE);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_HOE);
        basicItem(ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get());
        basicItem(ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get());
        basicItem(ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get());
        basicItem(ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get());

        basicItem(ModItemsAdditions.BASIC_CIRCUIT.get());
        basicItem(ModItemsAdditions.ADVANCED_CIRCUIT.get());
        basicItem(ModItemsAdditions.STARCH.get());
        basicItem(ModItemsAdditions.STRAINER.get());
        basicItem(ModItemsAdditions.PLASTIC_GOO.get());
        basicItem(ModItemsAdditions.GLYCERIN.get());
        basicItem(ModItemsAdditions.SLICED_APPLE.get());
        simpletools(ModItemsAdditions.KNIFE);
        simpletools(ModItemsAdditions.TRANSLOCATOR_LINK);

        basicItem(ModItemsAdditions.BUCKET_LUBRICANT.get());
        basicItem(ModItemsAdditions.BUCKET_ETHANOL.get());
        basicItem(ModItemsAdditions.BUCKET_OIL.get());
        basicItem(ModItemsAdditions.BUCKET_VINEGAR.get());
        basicItem(ModItemsAdditions.BUCKET_SUGARCANE_JUICE.get());
        basicItem(ModItemsAdditions.BUCKET_WINE.get());
        basicItem(ModItemsAdditions.BUCKET_EXPERIENCE.get());
        basicItem(ModItemsAdditions.BUCKET_STARLIQUID.get());
        basicItem(ModItemsAdditions.BUCKET_POTATO_JUICE.get());

        basicItem(ModItemsAdditions.ALCOHOL_BOTTLE.get());
        basicItem(ModItemsAdditions.VINEGAR_BOTTLE.get());
        basicItem(ModItemsAdditions.SUGARCANE_JUICE_BOTTLE.get());
        basicItem(ModItemsAdditions.WINE_BOTTLE.get());

        basicItem(ModItemsProgression.GOLD_ROD_CAST.get());
        basicItem(ModItemsProgression.GOLD_SCREW_CAST.get());
        basicItem(ModItemsProgression.GOLD_SHEET_CAST.get());
        basicItem(ModItemsProgression.GOLD_WIRE_CAST.get());
        basicItem(ModItemsProgression.GOLD_INGOT_CAST.get());
        basicItem(ModItemsProgression.INFINITY_MESH_CAST.get());
        basicItem(ModItemsProgression.RAW_ROD_CLAY_MODEL.get());
        basicItem(ModItemsProgression.RAW_SCREW_CLAY_MODEL.get());
        basicItem(ModItemsProgression.RAW_SHEET_CLAY_MODEL.get());
        basicItem(ModItemsProgression.RAW_WIRE_CLAY_MODEL.get());
        basicItem(ModItemsProgression.ROD_CLAY_MODEL.get());
        basicItem(ModItemsProgression.SCREW_CLAY_MODEL.get());
        basicItem(ModItemsProgression.SHEET_CLAY_MODEL.get());
        basicItem(ModItemsProgression.WIRE_CLAY_MODEL.get());

        basicItem(ModItemsProgression.COPPER_WIRE.get());
        basicItem(ModItemsProgression.LEAD_WIRE.get());
        basicItem(ModItemsProgression.TIN_WIRE.get());
        basicItem(ModItemsProgression.IRON_WIRE.get());
        basicItem(ModItemsProgression.GOLD_WIRE.get());
        basicItem(ModItemsProgression.SILVER_WIRE.get());
        basicItem(ModItemsProgression.NICKEL_WIRE.get());
        basicItem(ModItemsProgression.ZINC_WIRE.get());
        basicItem(ModItemsProgression.BRASS_WIRE.get());
        basicItem(ModItemsProgression.BRONZE_WIRE.get());
        basicItem(ModItemsProgression.STEEL_WIRE.get());
        basicItem(ModItemsProgression.ALUMINUM_WIRE.get());
        basicItem(ModItemsProgression.INDUSTRIAL_WIRE.get());
        basicItem(ModItemsProgression.INFINITY_WIRE.get());

        basicItem(ModItemsProgression.COPPER_SCREW.get());
        basicItem(ModItemsProgression.LEAD_SCREW.get());
        basicItem(ModItemsProgression.TIN_SCREW.get());
        basicItem(ModItemsProgression.IRON_SCREW.get());
        basicItem(ModItemsProgression.GOLD_SCREW.get());
        basicItem(ModItemsProgression.SILVER_SCREW.get());
        basicItem(ModItemsProgression.NICKEL_SCREW.get());
        basicItem(ModItemsProgression.ZINC_SCREW.get());
        basicItem(ModItemsProgression.BRASS_SCREW.get());
        basicItem(ModItemsProgression.BRONZE_SCREW.get());
        basicItem(ModItemsProgression.STEEL_SCREW.get());
        basicItem(ModItemsProgression.ALUMINUM_SCREW.get());
        basicItem(ModItemsProgression.INDUSTRIAL_SCREW.get());
        basicItem(ModItemsProgression.INFINITY_SCREW.get());

        basicItem(ModItemsProgression.COPPER_ROD.get());
        basicItem(ModItemsProgression.LEAD_ROD.get());
        basicItem(ModItemsProgression.TIN_ROD.get());
        basicItem(ModItemsProgression.IRON_ROD.get());
        basicItem(ModItemsProgression.GOLD_ROD.get());
        basicItem(ModItemsProgression.SILVER_ROD.get());
        basicItem(ModItemsProgression.NICKEL_ROD.get());
        basicItem(ModItemsProgression.ZINC_ROD.get());
        basicItem(ModItemsProgression.BRASS_ROD.get());
        basicItem(ModItemsProgression.BRONZE_ROD.get());
        basicItem(ModItemsProgression.STEEL_ROD.get());
        basicItem(ModItemsProgression.ALUMINUM_ROD.get());
        basicItem(ModItemsProgression.INDUSTRIAL_ROD.get());
        basicItem(ModItemsProgression.INFINITY_ROD.get());

        basicItem(ModItemsProgression.COPPER_SHEET.get());
        basicItem(ModItemsProgression.LEAD_SHEET.get());
        basicItem(ModItemsProgression.TIN_SHEET.get());
        basicItem(ModItemsProgression.IRON_SHEET.get());
        basicItem(ModItemsProgression.GOLD_SHEET.get());
        basicItem(ModItemsProgression.SILVER_SHEET.get());
        basicItem(ModItemsProgression.NICKEL_SHEET.get());
        basicItem(ModItemsProgression.ZINC_SHEET.get());
        basicItem(ModItemsProgression.BRASS_SHEET.get());
        basicItem(ModItemsProgression.BRONZE_SHEET.get());
        basicItem(ModItemsProgression.STEEL_SHEET.get());
        basicItem(ModItemsProgression.ALUMINUM_SHEET.get());
        basicItem(ModItemsProgression.INDUSTRIAL_SHEET.get());
        basicItem(ModItemsProgression.INFINITY_SHEET.get());
        basicItem(ModItemsProgression.IRIDIUM_MESH.get());
        basicItem(ModItemsProgression.CARBON_PLATE.get());

        basicItem(ModItemsProgression.BIO_MASS.get());
        basicItem(ModItemsProgression.SOLAR_CORE.get());
        basicItem(ModItemsProgression.ADVANCED_SOLAR_CORE.get());
        basicItem(ModItemsProgression.QUANTUM_SOLAR_CORE.get());
        basicItem(ModItemsProgression.RESIDUAL_MATTER.get());
        basicItem(ModItemsProgression.UNSTABLE_MATTER.get());
        basicItem(ModItemsProgression.STABLE_MATTER.get());
        basicItem(ModItemsProgression.IRIDIUM.get());
        basicItem(ModItemsProgression.PLASTIC.get());

        basicItem(ModItemsAdditions.TERRAIN_MARKER.get());
        basicItem(ModItemsAdditions.HAMMER_RANGE_UPGRADE.get());
        basicItem(ModItemsAdditions.STAR_FRAGMENT.get());

        basicItem(ModItemsAdditions.FRACTAL.get());

        withExistingParent(ModItemsAdditions.ASGREON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItemsAdditions.FLARON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

    }

    private ItemModelBuilder simpletools(DeferredItem<Item> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
