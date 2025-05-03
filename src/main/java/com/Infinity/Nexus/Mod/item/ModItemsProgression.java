package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.custom.Orb;
import com.Infinity.Nexus.Mod.item.custom.orbs.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItemsProgression {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(InfinityNexusMod.MOD_ID);
    //Casts
    public static final DeferredItem<Item> GOLD_WIRE_CAST = ITEMS.registerItem("gold_wire_cast", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> GOLD_SCREW_CAST = ITEMS.registerItem("gold_screw_cast", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> GOLD_SHEET_CAST = ITEMS.registerItem("gold_sheet_cast", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> GOLD_ROD_CAST = ITEMS.registerItem("gold_rod_cast", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> GOLD_INGOT_CAST = ITEMS.registerItem("gold_ingot_cast", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> INFINITY_MESH_CAST = ITEMS.registerItem("infinity_mesh_cast", Item::new, new Item.Properties().rarity(Rarity.COMMON));

    //Models
    public static final DeferredItem<Item> RAW_WIRE_CLAY_MODEL = ITEMS.registerItem("raw_wire_clay_model", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> RAW_SCREW_CLAY_MODEL = ITEMS.registerItem("raw_screw_clay_model", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> RAW_SHEET_CLAY_MODEL = ITEMS.registerItem("raw_sheet_clay_model", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> RAW_ROD_CLAY_MODEL = ITEMS.registerItem("raw_rod_clay_model", Item::new, new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<Item> WIRE_CLAY_MODEL = ITEMS.registerItem("wire_clay_model", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> SCREW_CLAY_MODEL = ITEMS.registerItem("screw_clay_model", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> SHEET_CLAY_MODEL = ITEMS.registerItem("sheet_clay_model", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> ROD_CLAY_MODEL = ITEMS.registerItem("rod_clay_model", Item::new, new Item.Properties().rarity(Rarity.COMMON));

    //Wires
    public static final DeferredItem<Item> COPPER_WIRE = ITEMS.registerItem("copper_wire", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> LEAD_WIRE = ITEMS.registerItem("lead_wire", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> TIN_WIRE = ITEMS.registerItem("tin_wire", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> IRON_WIRE = ITEMS.registerItem("iron_wire", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GOLD_WIRE = ITEMS.registerItem("gold_wire", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> SILVER_WIRE = ITEMS.registerItem("silver_wire", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> NICKEL_WIRE = ITEMS.registerItem("nickel_wire", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> ZINC_WIRE = ITEMS.registerItem("zinc_wire", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> BRASS_WIRE = ITEMS.registerItem("brass_wire", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> BRONZE_WIRE = ITEMS.registerItem("bronze_wire", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> ALUMINUM_WIRE = ITEMS.registerItem("aluminum_wire", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> STEEL_WIRE = ITEMS.registerItem("steel_wire", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> INDUSTRIAL_WIRE = ITEMS.registerItem("industrial_wire", Item::new, new Item.Properties().rarity(Rarity.EPIC));
    public static final DeferredItem<Item> INFINITY_WIRE = ITEMS.registerItem("infinity_wire", Item::new, new Item.Properties().rarity(Rarity.EPIC));

    //Screws
    public static final DeferredItem<Item> COPPER_SCREW = ITEMS.registerItem("copper_screw", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> LEAD_SCREW = ITEMS.registerItem("lead_screw", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> TIN_SCREW = ITEMS.registerItem("tin_screw", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> IRON_SCREW = ITEMS.registerItem("iron_screw", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GOLD_SCREW = ITEMS.registerItem("gold_screw", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> SILVER_SCREW = ITEMS.registerItem("silver_screw", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> NICKEL_SCREW = ITEMS.registerItem("nickel_screw", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> ZINC_SCREW = ITEMS.registerItem("zinc_screw", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> BRASS_SCREW = ITEMS.registerItem("brass_screw", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> BRONZE_SCREW = ITEMS.registerItem("bronze_screw", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> ALUMINUM_SCREW = ITEMS.registerItem("aluminum_screw", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> STEEL_SCREW = ITEMS.registerItem("steel_screw", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> INDUSTRIAL_SCREW = ITEMS.registerItem("industrial_screw", Item::new, new Item.Properties().rarity(Rarity.EPIC));
    public static final DeferredItem<Item> INFINITY_SCREW = ITEMS.registerItem("infinity_screw", Item::new, new Item.Properties().rarity(Rarity.EPIC));

    //Rods
    public static final DeferredItem<Item> COPPER_ROD = ITEMS.registerItem("copper_rod", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> LEAD_ROD = ITEMS.registerItem("lead_rod", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> TIN_ROD = ITEMS.registerItem("tin_rod", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> IRON_ROD = ITEMS.registerItem("iron_rod", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GOLD_ROD = ITEMS.registerItem("gold_rod", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> SILVER_ROD = ITEMS.registerItem("silver_rod", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> NICKEL_ROD = ITEMS.registerItem("nickel_rod", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> ZINC_ROD = ITEMS.registerItem("zinc_rod", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> BRASS_ROD = ITEMS.registerItem("brass_rod", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> BRONZE_ROD = ITEMS.registerItem("bronze_rod", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> ALUMINUM_ROD = ITEMS.registerItem("aluminum_rod", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> STEEL_ROD = ITEMS.registerItem("steel_rod", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> INDUSTRIAL_ROD = ITEMS.registerItem("industrial_rod", Item::new, new Item.Properties().rarity(Rarity.EPIC));
    public static final DeferredItem<Item> INFINITY_ROD = ITEMS.registerItem("infinity_rod", Item::new, new Item.Properties().rarity(Rarity.EPIC));

    //Sheets
    public static final DeferredItem<Item> COPPER_SHEET = ITEMS.registerItem("copper_sheet", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> LEAD_SHEET = ITEMS.registerItem("lead_sheet", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> TIN_SHEET = ITEMS.registerItem("tin_sheet", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> IRON_SHEET = ITEMS.registerItem("iron_sheet", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GOLD_SHEET = ITEMS.registerItem("gold_sheet", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> SILVER_SHEET = ITEMS.registerItem("silver_sheet", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> NICKEL_SHEET = ITEMS.registerItem("nickel_sheet", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> ZINC_SHEET = ITEMS.registerItem("zinc_sheet", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> BRASS_SHEET = ITEMS.registerItem("brass_sheet", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> BRONZE_SHEET = ITEMS.registerItem("bronze_sheet", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> ALUMINUM_SHEET = ITEMS.registerItem("aluminum_sheet", Item::new, new Item.Properties().rarity(Rarity.COMMON));
    public static final DeferredItem<Item> STEEL_SHEET = ITEMS.registerItem("steel_sheet", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> INDUSTRIAL_SHEET = ITEMS.registerItem("industrial_sheet", Item::new, new Item.Properties().rarity(Rarity.EPIC));
    public static final DeferredItem<Item> INFINITY_SHEET = ITEMS.registerItem("infinity_sheet", Item::new, new Item.Properties().rarity(Rarity.EPIC));
    public static final DeferredItem<Item> IRIDIUM_MESH = ITEMS.registerItem("iridium_mesh", Item::new, new Item.Properties().rarity(Rarity.EPIC));


    public static final DeferredItem<Item> BIO_MASS = ITEMS.registerItem("bio_mass", Item::new, new Item.Properties());
    public static final DeferredItem<Item> SOLAR_CORE = ITEMS.registerItem("solar_core", Item::new, new Item.Properties());
    public static final DeferredItem<Item> ADVANCED_SOLAR_CORE = ITEMS.registerItem("advanced_solar_core", Item::new, new Item.Properties());
    public static final DeferredItem<Item> QUANTUM_SOLAR_CORE = ITEMS.registerItem("quantum_solar_core", Item::new, new Item.Properties());
    public static final DeferredItem<Item> RESIDUAL_MATTER = ITEMS.registerItem("residual_matter", Item::new, new Item.Properties());
    public static final DeferredItem<Item> UNSTABLE_MATTER = ITEMS.registerItem("unstable_matter", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> STABLE_MATTER = ITEMS.registerItem("stable_matter", Item::new, new Item.Properties().rarity(Rarity.EPIC));
    public static final DeferredItem<Item> IRIDIUM = ITEMS.registerItem("iridium", Item::new, new Item.Properties());
    public static final DeferredItem<Item> PLASTIC = ITEMS.registerItem("plastic", Item::new, new Item.Properties());
    public static final DeferredItem<Item> CARBON_PLATE = ITEMS.registerItem("carbon_plate", Item::new, new Item.Properties());

    public static final DeferredItem<Item> VOXEL_TOP = ITEMS.register("voxel_t", () -> new Orb(new Item.Properties(), 0));
    public static final DeferredItem<Item> VOXEL_DOW = ITEMS.register("voxel_d", () -> new Orb(new Item.Properties(), 0));
    public static final DeferredItem<Item> VOXEL_NORTH = ITEMS.register("voxel_n", () -> new Orb(new Item.Properties(), 0));
    public static final DeferredItem<Item> VOXEL_SOUTH = ITEMS.register("voxel_s", () -> new Resource(new Item.Properties(), 0, 5));
    public static final DeferredItem<Item> VOXEL_WEST = ITEMS.register("voxel_w", () -> new Orb(new Item.Properties(), 0));
    public static final DeferredItem<Item> VOXEL_EAST = ITEMS.register("voxel_e", () -> new Orb(new Item.Properties(), 0));

    public static final DeferredItem<Item> VOXEL_TOP_BASIC = ITEMS.register("voxel_t_basic", () -> new Orb(new Item.Properties(), 1));
    public static final DeferredItem<Item> VOXEL_DOW_BASIC = ITEMS.register("voxel_d_basic", () -> new Orb(new Item.Properties(), 1));
    public static final DeferredItem<Item> VOXEL_NORTH_BASIC = ITEMS.register("voxel_n_basic", () -> new Orb(new Item.Properties(), 1));
    public static final DeferredItem<Item> VOXEL_SOUTH_BASIC = ITEMS.register("voxel_s_basic", () -> new Resource(new Item.Properties(), 1, 10));
    public static final DeferredItem<Item> VOXEL_WEST_BASIC = ITEMS.register("voxel_w_basic", () -> new Orb(new Item.Properties(), 1));
    public static final DeferredItem<Item> VOXEL_EAST_BASIC = ITEMS.register("voxel_e_basic", () -> new Orb(new Item.Properties(), 1));

    public static final DeferredItem<Item> VOXEL_TOP_ADVANCED = ITEMS.register("voxel_t_advanced", () -> new Orb(new Item.Properties(), 2));
    public static final DeferredItem<Item> VOXEL_DOW_ADVANCED = ITEMS.register("voxel_d_advanced", () -> new Orb(new Item.Properties(), 2));
    public static final DeferredItem<Item> VOXEL_NORTH_ADVANCED = ITEMS.register("voxel_n_advanced", () -> new Orb(new Item.Properties(), 2));
    public static final DeferredItem<Item> VOXEL_SOUTH_ADVANCED = ITEMS.register("voxel_s_advanced", () -> new Resource(new Item.Properties(), 2, 15));
    public static final DeferredItem<Item> VOXEL_WEST_ADVANCED = ITEMS.register("voxel_w_advanced", () -> new Orb(new Item.Properties(), 2));
    public static final DeferredItem<Item> VOXEL_EAST_ADVANCED = ITEMS.register("voxel_e_advanced", () -> new Orb(new Item.Properties(), 2));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}