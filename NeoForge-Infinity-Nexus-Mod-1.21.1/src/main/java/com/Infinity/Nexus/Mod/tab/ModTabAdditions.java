package com.Infinity.Nexus.Mod.tab;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTabAdditions {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfinityNexusMod.MOD_ID);
    public static final Supplier<CreativeModeTab> INFINITY_TAB_ADDITIONS = CREATIVE_MODE_TABS.register("infinity_nexus_mod_addition",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.infinity_nexus_mod_addition"))
                    .icon(() -> new ItemStack(ModBlocksAdditions.ASSEMBLY))
                    .displayItems((pParameters, pOutput) -> {
                        //-------------------------//-------------------------//
                        //machines
                        pOutput.accept(ModBlocksAdditions.CRUSHER);
                        pOutput.accept(ModBlocksAdditions.PRESS);
                        pOutput.accept(ModBlocksAdditions.ASSEMBLY);
                        pOutput.accept(ModBlocksAdditions.SQUEEZER);
                        pOutput.accept(ModBlocksAdditions.SMELTERY);
                        pOutput.accept(ModBlocksAdditions.RECYCLER);
                        pOutput.accept(ModBlocksAdditions.GENERATOR);
                        pOutput.accept(ModBlocksAdditions.MATTER_CONDENSER);
                        pOutput.accept(ModBlocksAdditions.MOB_CRUSHER);
                        pOutput.accept(ModBlocksAdditions.PLACER);
                        pOutput.accept(ModBlocksAdditions.INFUSER);
                        pOutput.accept(ModBlocksAdditions.FACTORY);
                        pOutput.accept(ModBlocksAdditions.COMPACTOR);
                        pOutput.accept(ModBlocksAdditions.SOLAR);
                        pOutput.accept(ModBlocksAdditions.FERMENTATION_BARREL);
                        pOutput.accept(ModBlocksAdditions.DISPLAY);
                        pOutput.accept(ModBlocksAdditions.ENTITY_DISPLAY);
                        pOutput.accept(ModBlocksAdditions.ENTITY_CENTRALIZER);
                        pOutput.accept(ModBlocksAdditions.TANK);
                        pOutput.accept(ModBlocksAdditions.BATTERY);
                        pOutput.accept(ModBlocksAdditions.TRANSLOCATOR_ITEM);
                        pOutput.accept(ModBlocksAdditions.TRANSLOCATOR_ENERGY);
                        pOutput.accept(ModBlocksAdditions.TRANSLOCATOR_FLUID);
                        pOutput.accept(ModItemsAdditions.TRANSLOCATOR_LINK);
                        pOutput.accept(ModBlocksAdditions.DEPOT);
                        pOutput.accept(ModBlocksAdditions.DEPOT_STONE);
                        //-------------------------//-------------------------//
                        //Pedestals
                        pOutput.accept(ModBlocksAdditions.TECH_PEDESTAL);
                        pOutput.accept(ModBlocksAdditions.EXPLORATION_PEDESTAL);
                        pOutput.accept(ModBlocksAdditions.RESOURCE_PEDESTAL);
                        pOutput.accept(ModBlocksAdditions.MAGIC_PEDESTAL);
                        pOutput.accept(ModBlocksAdditions.CREATIVITY_PEDESTAL);
                        pOutput.accept(ModBlocksAdditions.DECOR_PEDESTAL);
                        //-------------------------//-------------------------//
                        //Solar
                        pOutput.accept(ModItemsAdditions.SOLAR_PANE);
                        pOutput.accept(ModItemsAdditions.SOLAR_PANE_ADVANCED);
                        pOutput.accept(ModItemsAdditions.SOLAR_PANE_ULTIMATE);
                        pOutput.accept(ModItemsAdditions.SOLAR_PANE_QUANTUM);
                        pOutput.accept(ModItemsAdditions.SOLAR_PANE_PHOTONIC);
                        //-------------------------//-------------------------//
                        //infinity armor
                        pOutput.accept(ModItemsAdditions.INFINITY_HELMET);
                        pOutput.accept(ModItemsAdditions.INFINITY_CHESTPLATE);
                        pOutput.accept(ModItemsAdditions.INFINITY_LEGGINGS);
                        pOutput.accept(ModItemsAdditions.INFINITY_BOOTS);
                        //infinity tools
                        pOutput.accept(ModItemsAdditions.INFINITY_SWORD);
                        pOutput.accept(ModItemsAdditions.INFINITY_PAXEL);
                        pOutput.accept(ModItemsAdditions.INFINITY_HAMMER);
                        pOutput.accept(ModItemsAdditions.INFINITY_PICKAXE);
                        pOutput.accept(ModItemsAdditions.INFINITY_AXE);
                        pOutput.accept(ModItemsAdditions.INFINITY_SHOVEL);
                        pOutput.accept(ModItemsAdditions.INFINITY_HOE);
                        pOutput.accept(ModItemsAdditions.INFINITY_BOW);
                        //imperial armor
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_HELMET);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_BOOTS);
                        //imperial tools
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_SWORD);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_HAMMER);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_AXE);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_HOE);
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_BOW);
                        //carbon armor
                        pOutput.accept(ModItemsAdditions.CARBON_HELMET);
                        pOutput.accept(ModItemsAdditions.CARBON_CHESTPLATE);
                        pOutput.accept(ModItemsAdditions.CARBON_LEGGINGS);
                        pOutput.accept(ModItemsAdditions.CARBON_BOOTS);
                        //carbon tools
                        pOutput.accept(ModItemsAdditions.CARBON_SWORD);
                        pOutput.accept(ModItemsAdditions.CARBON_PICKAXE);
                        pOutput.accept(ModItemsAdditions.CARBON_AXE);
                        pOutput.accept(ModItemsAdditions.CARBON_SHOVEL);
                        pOutput.accept(ModItemsAdditions.CARBON_HOE);
                        pOutput.accept(ModItemsAdditions.CARBON_BOW);
                        //3D
                        pOutput.accept(ModItemsAdditions.IMPERIAL_INFINITY_3D_SWORD);
                        pOutput.accept(ModItemsAdditions.INFINITY_3D_SWORD);
                        //-------------------------//-------------------------//
                        //Catwalk
                        pOutput.accept(ModBlocksAdditions.CATWALK);
                        pOutput.accept(ModBlocksAdditions.CATWALK_2);
                        pOutput.accept(ModBlocksAdditions.CATWALK_3);
                        pOutput.accept(ModBlocksAdditions.CATWALK_4);
                        pOutput.accept(ModBlocksAdditions.CATWALK_5);
                        pOutput.accept(ModBlocksAdditions.CATWALK_6);
                        pOutput.accept(ModBlocksAdditions.CATWALK_7);
                        pOutput.accept(ModBlocksAdditions.CATWALK_8);
                        pOutput.accept(ModBlocksAdditions.CATWALK_9);
                        pOutput.accept(ModBlocksAdditions.CATWALK_10);
                        pOutput.accept(ModBlocksAdditions.CATWALK_11);
                        pOutput.accept(ModBlocksAdditions.CATWALK_12);
                        pOutput.accept(ModBlocksAdditions.CATWALK_13);
                        pOutput.accept(ModBlocksAdditions.CATWALK_16);
                        pOutput.accept(ModBlocksAdditions.CATWALK_17);
                        pOutput.accept(ModBlocksAdditions.CATWALK_18);
                        pOutput.accept(ModBlocksAdditions.CATWALK_14);
                        pOutput.accept(ModBlocksAdditions.CATWALK_15);
                        //-------------------------//-------------------------//
                        //ingots
                        pOutput.accept(ModItemsAdditions.SILVER_INGOT);
                        pOutput.accept(ModItemsAdditions.TIN_INGOT);
                        pOutput.accept(ModItemsAdditions.LEAD_INGOT);
                        pOutput.accept(ModItemsAdditions.NICKEL_INGOT);
                        pOutput.accept(ModItemsAdditions.ZINC_INGOT);
                        pOutput.accept(ModItemsAdditions.BRASS_INGOT);
                        pOutput.accept(ModItemsAdditions.BRONZE_INGOT);
                        pOutput.accept(ModItemsAdditions.STEEL_INGOT);
                        pOutput.accept(ModItemsAdditions.GRAPHITE_INGOT);
                        pOutput.accept(ModItemsAdditions.ALUMINUM_INGOT);
                        pOutput.accept(ModItemsAdditions.URANIUM_INGOT);
                        pOutput.accept(ModItemsAdditions.INFINITY_INGOT);
                        pOutput.accept(ModItemsAdditions.INFINIUM_STELLARUM_INGOT);
                        pOutput.accept(ModItemsAdditions.IRIDIUM_INGOT);
                        //nuggets
                        pOutput.accept(ModItemsAdditions.COPPER_NUGGET);
                        pOutput.accept(ModItemsAdditions.SILVER_NUGGET);
                        pOutput.accept(ModItemsAdditions.TIN_NUGGET);
                        pOutput.accept(ModItemsAdditions.LEAD_NUGGET);
                        pOutput.accept(ModItemsAdditions.NICKEL_NUGGET);
                        pOutput.accept(ModItemsAdditions.ZINC_NUGGET);
                        pOutput.accept(ModItemsAdditions.BRASS_NUGGET);
                        pOutput.accept(ModItemsAdditions.BRONZE_NUGGET);
                        pOutput.accept(ModItemsAdditions.STEEL_NUGGET);
                        pOutput.accept(ModItemsAdditions.ALUMINUM_NUGGET);
                        pOutput.accept(ModItemsAdditions.URANIUM_NUGGET);
                        pOutput.accept(ModItemsAdditions.INFINITY_NUGGET);
                        //dust
                        pOutput.accept(ModItemsAdditions.COPPER_DUST);
                        pOutput.accept(ModItemsAdditions.IRON_DUST);
                        pOutput.accept(ModItemsAdditions.GOLD_DUST);
                        pOutput.accept(ModItemsAdditions.SILVER_DUST);
                        pOutput.accept(ModItemsAdditions.TIN_DUST);
                        pOutput.accept(ModItemsAdditions.LEAD_DUST);
                        pOutput.accept(ModItemsAdditions.NICKEL_DUST);
                        pOutput.accept(ModItemsAdditions.ZINC_DUST);
                        pOutput.accept(ModItemsAdditions.BRASS_DUST);
                        pOutput.accept(ModItemsAdditions.BRONZE_DUST);
                        pOutput.accept(ModItemsAdditions.STEEL_DUST);
                        pOutput.accept(ModItemsAdditions.GRAPHITE_DUST);
                        pOutput.accept(ModItemsAdditions.DIAMOND_DUST);
                        pOutput.accept(ModItemsAdditions.ALUMINUM_DUST);
                        pOutput.accept(ModItemsAdditions.URANIUM_DUST);
                        pOutput.accept(ModItemsAdditions.INFINITY_DUST);
                        //blocks
                        pOutput.accept(ModBlocksAdditions.SILVER_BLOCK);
                        pOutput.accept(ModBlocksAdditions.TIN_BLOCK);
                        pOutput.accept(ModBlocksAdditions.LEAD_BLOCK);
                        pOutput.accept(ModBlocksAdditions.NICKEL_BLOCK);
                        pOutput.accept(ModBlocksAdditions.ZINC_BLOCK);
                        pOutput.accept(ModBlocksAdditions.BRASS_BLOCK);
                        pOutput.accept(ModBlocksAdditions.BRONZE_BLOCK);
                        pOutput.accept(ModBlocksAdditions.STEEL_BLOCK);
                        pOutput.accept(ModBlocksAdditions.ALUMINUM_BLOCK);
                        pOutput.accept(ModBlocksAdditions.URANIUM_BLOCK);
                        pOutput.accept(ModBlocksAdditions.INFINITY_BLOCK);
                        pOutput.accept(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK);
                        //raw
                        pOutput.accept(ModBlocksAdditions.RAW_SILVER_BLOCK);
                        pOutput.accept(ModBlocksAdditions.RAW_TIN_BLOCK);
                        pOutput.accept(ModBlocksAdditions.RAW_LEAD_BLOCK);
                        pOutput.accept(ModBlocksAdditions.RAW_NICKEL_BLOCK);
                        pOutput.accept(ModBlocksAdditions.RAW_ZINC_BLOCK);
                        pOutput.accept(ModBlocksAdditions.RAW_ALUMINUM_BLOCK);
                        pOutput.accept(ModBlocksAdditions.RAW_URANIUM_BLOCK);
                        pOutput.accept(ModBlocksAdditions.RAW_INFINITY_BLOCK);

                        pOutput.accept(ModItemsAdditions.RAW_SILVER);
                        pOutput.accept(ModItemsAdditions.RAW_TIN);
                        pOutput.accept(ModItemsAdditions.RAW_LEAD);
                        pOutput.accept(ModItemsAdditions.RAW_NICKEL);
                        pOutput.accept(ModItemsAdditions.RAW_ZINC);
                        pOutput.accept(ModItemsAdditions.RAW_ALUMINUM);
                        pOutput.accept(ModItemsAdditions.RAW_URANIUM);
                        pOutput.accept(ModItemsAdditions.RAW_INFINITY);

                        //ores
                        pOutput.accept(ModBlocksAdditions.SILVER_ORE);
                        pOutput.accept(ModBlocksAdditions.TIN_ORE);
                        pOutput.accept(ModBlocksAdditions.LEAD_ORE);
                        pOutput.accept(ModBlocksAdditions.NICKEL_ORE);
                        pOutput.accept(ModBlocksAdditions.ZINC_ORE);
                        pOutput.accept(ModBlocksAdditions.ALUMINUM_ORE);
                        pOutput.accept(ModBlocksAdditions.URANIUM_ORE);
                        pOutput.accept(ModBlocksAdditions.INFINITY_ORE);
                        //deepslates ores
                        pOutput.accept(ModBlocksAdditions.DEEPSLATE_SILVER_ORE);
                        pOutput.accept(ModBlocksAdditions.DEEPSLATE_TIN_ORE);
                        pOutput.accept(ModBlocksAdditions.DEEPSLATE_LEAD_ORE);
                        pOutput.accept(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE);
                        pOutput.accept(ModBlocksAdditions.DEEPSLATE_ZINC_ORE);
                        pOutput.accept(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE);
                        pOutput.accept(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE);
                        pOutput.accept(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE);

                        pOutput.accept(ModItemsAdditions.INFINITY_SINGULARITY);
                        pOutput.accept(ModItemsAdditions.PORTAL_ACTIVATOR);
                        pOutput.accept(ModBlocksAdditions.ASPHALT);
                        pOutput.accept(ModItemsAdditions.ITEM_DISLOCATOR);
                        pOutput.accept(ModBlocksAdditions.EXPLORAR_PORTAL_FRAME);

                        pOutput.accept(ModItemsAdditions.BUCKET_LUBRICANT);
                        pOutput.accept(ModItemsAdditions.BUCKET_POTATO_JUICE);
                        pOutput.accept(ModItemsAdditions.ALCOHOL_BOTTLE);
                        pOutput.accept(ModItemsAdditions.WINE_BOTTLE);
                        pOutput.accept(ModItemsAdditions.VINEGAR_BOTTLE);
                        pOutput.accept(ModItemsAdditions.SUGARCANE_JUICE_BOTTLE);
                        pOutput.accept(ModItemsAdditions.STARCH);
                        pOutput.accept(ModItemsAdditions.STRAINER);
                        pOutput.accept(ModItemsAdditions.PLASTIC_GOO);
                        pOutput.accept(ModItemsAdditions.GLYCERIN);
                        pOutput.accept(ModItemsAdditions.SLICED_APPLE);
                        pOutput.accept(ModItemsAdditions.KNIFE);

                        pOutput.accept(ModItemsAdditions.BASIC_CIRCUIT);
                        pOutput.accept(ModItemsAdditions.ADVANCED_CIRCUIT);

                        pOutput.accept(ModItemsAdditions.TERRAIN_MARKER);
                        pOutput.accept(ModItemsAdditions.HAMMER_RANGE_UPGRADE);

                        pOutput.accept(ModItemsAdditions.ASGREON_SPAWN_EGG);
                        pOutput.accept(ModItemsAdditions.FLARON_SPAWN_EGG);

                        pOutput.accept(ModItemsAdditions.STAR_FRAGMENT);
                        pOutput.accept(ModItemsProgression.RESIDUAL_MATTER);
                        pOutput.accept(ModItemsProgression.SOLAR_CORE);
                        pOutput.accept(ModItemsProgression.ADVANCED_SOLAR_CORE);
                        pOutput.accept(ModItemsProgression.QUANTUM_SOLAR_CORE);
                        pOutput.accept(ModItemsProgression.BIO_MASS);
                        pOutput.accept(ModItemsProgression.UNSTABLE_MATTER);
                        pOutput.accept(ModItemsProgression.STABLE_MATTER);
                        pOutput.accept(ModItemsProgression.IRIDIUM);
                        pOutput.accept(ModItemsProgression.PLASTIC);
                        pOutput.accept(ModItemsProgression.CARBON_PLATE);

                        //-------------------------//-------------------------//
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
