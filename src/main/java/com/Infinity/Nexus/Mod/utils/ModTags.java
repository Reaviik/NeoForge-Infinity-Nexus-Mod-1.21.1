package com.Infinity.Nexus.Mod.utils;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> INCORRECT_FOR_CARBON_TOOL = tag("incorrect_for_carbon_tool");
        public static final TagKey<Block> NEEDS_CARBON_TOOL = tag("needs_carbon_tool");

        public static final TagKey<Block> INCORRECT_FOR_INFINITY_TOOL = tag("incorrect_for_infinity_tool");
        public static final TagKey<Block> NEEDS_INFINITY_TOOL = tag("needs_infinity_tool");

        public static final TagKey<Block> INCORRECT_FOR_IMPERIAL_TOOL = tag("incorrect_for_imperial_tool");
        public static final TagKey<Block> NEEDS_IMPERIAL_TOOL = tag("needs_imperial_tool");

        public static final TagKey<Block> PAXEL_MINEABLE = tag("mineable/paxel_mineable");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS = forgeTag("storage_blocks");
        public static final TagKey<Block> FORGE_ORES = forgeTag("ores");
        public static final TagKey<Block> FORGE_ORES_SINGULAR = forgeTag("ore_rates/singular");
        public static final TagKey<Block> FORGE_ORES_IN_GROUND_STONE = forgeTag("ore_in_ground/stone");
        public static final TagKey<Block> FORGE_ORES_IN_GROUND_DEEPSLATE = forgeTag("ore_in_ground/deepslate");

        public static final TagKey<Block> MINER_STRUCTURE_0 = tag("machine_tier_0");
        public static final TagKey<Block> MINER_STRUCTURE_1 = tag("machine_tier_1");
        public static final TagKey<Block> MINER_STRUCTURE_2 = tag("machine_tier_2");
        public static final TagKey<Block> MINER_STRUCTURE_3 = tag("machine_tier_3");
        public static final TagKey<Block> MINER_STRUCTURE_4 = tag("machine_tier_4");
        public static final TagKey<Block> MINER_STRUCTURE_5 = tag("machine_tier_5");
        public static final TagKey<Block> MINER_STRUCTURE_6 = tag("machine_tier_6");
        public static final TagKey<Block> MINER_STRUCTURE_7 = tag("machine_tier_7");
        public static final TagKey<Block> MINER_STRUCTURE_8 = tag("machine_tier_8");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }

    }
    public static class Items {
        public static final TagKey<Item> PAXELS = forgeTag("paxels");
        public static final TagKey<Item> INFINITY_HELMET = tag("infinity_helmet");
        public static final TagKey<Item> INFINITY_CHESTPLATE = tag("infinity_chestplate");
        public static final TagKey<Item> INFINITY_LEGGINGS = tag("infinity_leggings");
        public static final TagKey<Item> INFINITY_BOOTS = tag("infinity_boots");
        public static final TagKey<Item> FORGE_INGOTS = forgeTag("ingots");
        public static final TagKey<Item> FORGE_NUGGETS = forgeTag("nuggets");
        public static final TagKey<Item> FORGE_RAW = forgeTag("raw_materials");
        public static final TagKey<Item> FORGE_DUSTS = forgeTag("dusts");
        public static final TagKey<Item> FORGE_PLATES = forgeTag("plates");
        public static final TagKey<Item> FORGE_ROD = forgeTag("rods");
        public static final TagKey<Item> FORGE_SCREW = forgeTag("screws");
        public static final TagKey<Item> FORGE_WIRES = forgeTag("wires");

        public static final TagKey<Item> MINER_STRUCTURE_0 = tag("machine_tier_0");
        public static final TagKey<Item> MINER_STRUCTURE_1 = tag("machine_tier_1");
        public static final TagKey<Item> MINER_STRUCTURE_2 = tag("machine_tier_2");
        public static final TagKey<Item> MINER_STRUCTURE_3 = tag("machine_tier_3");
        public static final TagKey<Item> MINER_STRUCTURE_4 = tag("machine_tier_4");
        public static final TagKey<Item> MINER_STRUCTURE_5 = tag("machine_tier_5");
        public static final TagKey<Item> MINER_STRUCTURE_6 = tag("machine_tier_6");
        public static final TagKey<Item> MINER_STRUCTURE_7 = tag("machine_tier_7");
        public static final TagKey<Item> MINER_STRUCTURE_8 = tag("machine_tier_8");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, name));
        }
        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

}
