package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.entity.ModEntities;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.custom.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItemsAdditions {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(InfinityNexusMod.MOD_ID);

    public static final DeferredItem<Item> INFINIUM_STELLARUM_INGOT = ITEMS.registerSimpleItem("infinium_stellarum_ingot");
    public static final DeferredItem<Item> INFINITY_INGOT = ITEMS.registerSimpleItem("infinity_ingot");
    public static final DeferredItem<Item> LEAD_INGOT = ITEMS.registerSimpleItem("lead_ingot");
    public static final DeferredItem<Item> ALUMINUM_INGOT = ITEMS.registerSimpleItem("aluminum_ingot");
    public static final DeferredItem<Item> NICKEL_INGOT = ITEMS.registerSimpleItem("nickel_ingot");
    public static final DeferredItem<Item> ZINC_INGOT = ITEMS.registerSimpleItem("zinc_ingot");
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerSimpleItem("silver_ingot");
    public static final DeferredItem<Item> TIN_INGOT = ITEMS.registerSimpleItem("tin_ingot");
    public static final DeferredItem<Item> INVAR_INGOT = ITEMS.registerSimpleItem("invar_ingot");
    public static final DeferredItem<Item> URANIUM_INGOT = ITEMS.registerSimpleItem("uranium_ingot");
    public static final DeferredItem<Item> BRASS_INGOT = ITEMS.registerSimpleItem("brass_ingot");
    public static final DeferredItem<Item> BRONZE_INGOT = ITEMS.registerSimpleItem("bronze_ingot");
    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.registerSimpleItem("steel_ingot");
    public static final DeferredItem<Item> GRAPHITE_INGOT = ITEMS.registerSimpleItem("graphite_ingot");
    public static final DeferredItem<Item> IRIDIUM_INGOT = ITEMS.registerSimpleItem("iridium_ingot");


    public static final DeferredItem<Item> INFINITY_NUGGET = ITEMS.registerSimpleItem("infinity_nugget");
    public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.registerSimpleItem("copper_nugget");
    public static final DeferredItem<Item> LEAD_NUGGET = ITEMS.registerSimpleItem("lead_nugget");
    public static final DeferredItem<Item> ALUMINUM_NUGGET = ITEMS.registerSimpleItem("aluminum_nugget");
    public static final DeferredItem<Item> NICKEL_NUGGET = ITEMS.registerSimpleItem("nickel_nugget");
    public static final DeferredItem<Item> ZINC_NUGGET = ITEMS.registerSimpleItem("zinc_nugget");
    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.registerSimpleItem("silver_nugget");
    public static final DeferredItem<Item> TIN_NUGGET = ITEMS.registerSimpleItem("tin_nugget");
    public static final DeferredItem<Item> INVAR_NUGGET = ITEMS.registerSimpleItem("invar_nugget");
    public static final DeferredItem<Item> URANIUM_NUGGET = ITEMS.registerSimpleItem("uranium_nugget");
    public static final DeferredItem<Item> BRASS_NUGGET = ITEMS.registerSimpleItem("brass_nugget");
    public static final DeferredItem<Item> BRONZE_NUGGET = ITEMS.registerSimpleItem("bronze_nugget");
    public static final DeferredItem<Item> STEEL_NUGGET = ITEMS.registerSimpleItem("steel_nugget");

    public static final DeferredItem<Item> INFINITY_DUST = ITEMS.registerSimpleItem("infinity_dust");
    public static final DeferredItem<Item> COPPER_DUST = ITEMS.registerSimpleItem("copper_dust");
    public static final DeferredItem<Item> IRON_DUST = ITEMS.registerSimpleItem("iron_dust");
    public static final DeferredItem<Item> GOLD_DUST = ITEMS.registerSimpleItem("gold_dust");
    public static final DeferredItem<Item> LEAD_DUST = ITEMS.registerSimpleItem("lead_dust");
    public static final DeferredItem<Item> ALUMINUM_DUST = ITEMS.registerSimpleItem("aluminum_dust");
    public static final DeferredItem<Item> NICKEL_DUST = ITEMS.registerSimpleItem("nickel_dust");
    public static final DeferredItem<Item> ZINC_DUST = ITEMS.registerSimpleItem("zinc_dust");
    public static final DeferredItem<Item> SILVER_DUST = ITEMS.registerSimpleItem("silver_dust");
    public static final DeferredItem<Item> TIN_DUST = ITEMS.registerSimpleItem("tin_dust");
    public static final DeferredItem<Item> INVAR_DUST = ITEMS.registerSimpleItem("invar_dust");
    public static final DeferredItem<Item> URANIUM_DUST = ITEMS.registerSimpleItem("uranium_dust");
    public static final DeferredItem<Item> BRASS_DUST = ITEMS.registerSimpleItem("brass_dust");
    public static final DeferredItem<Item> BRONZE_DUST = ITEMS.registerSimpleItem("bronze_dust");
    public static final DeferredItem<Item> STEEL_DUST = ITEMS.registerSimpleItem("steel_dust");
    public static final DeferredItem<Item> GRAPHITE_DUST = ITEMS.registerSimpleItem("graphite_dust");
    public static final DeferredItem<Item> DIAMOND_DUST = ITEMS.registerSimpleItem("diamond_dust");

    public static final DeferredItem<Item> RAW_INFINITY = ITEMS.registerSimpleItem("raw_infinity");
    public static final DeferredItem<Item> RAW_LEAD = ITEMS.registerSimpleItem("raw_lead");
    public static final DeferredItem<Item> RAW_ALUMINUM = ITEMS.registerSimpleItem("raw_aluminum");
    public static final DeferredItem<Item> RAW_NICKEL = ITEMS.registerSimpleItem("raw_nickel");
    public static final DeferredItem<Item> RAW_ZINC = ITEMS.registerSimpleItem("raw_zinc");
    public static final DeferredItem<Item> RAW_SILVER = ITEMS.registerSimpleItem("raw_silver");
    public static final DeferredItem<Item> RAW_TIN = ITEMS.registerSimpleItem("raw_tin");
    public static final DeferredItem<Item> RAW_URANIUM = ITEMS.registerSimpleItem("raw_uranium");

    public static final DeferredItem<Item> INFINITY_SINGULARITY = ITEMS.registerSimpleItem("infinity_singularity");
    public static final DeferredItem<Item> PORTAL_ACTIVATOR = ITEMS.registerSimpleItem("catalyst");

    public static final DeferredItem<Item> STARCH = ITEMS.registerSimpleItem("starch");
    public static final DeferredItem<Item> STRAINER = ITEMS.registerSimpleItem("strainer");
    public static final DeferredItem<Item> PLASTIC_GOO = ITEMS.registerSimpleItem("plastic_goo");
    public static final DeferredItem<Item> GLYCERIN = ITEMS.registerSimpleItem("glycerin");
    public static final DeferredItem<Item> SLICED_APPLE = ITEMS.registerSimpleItem("sliced_apple");
    public static final DeferredItem<Item> STAR_FRAGMENT = ITEMS.registerSimpleItem("star_fragment");

    public static final DeferredItem<Item> ITEM_DISLOCATOR = ITEMS.register("item_dislocator", () -> new ItemDislocator(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CARBON_HELMET = ITEMS.register("carbon_helmet", () -> new CarbonArmorItem(ModArmorMaterials.CARBON, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CARBON_CHESTPLATE = ITEMS.register("carbon_chestplate", () -> new CarbonArmorItem(ModArmorMaterials.CARBON, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CARBON_LEGGINGS = ITEMS.register("carbon_leggings", () -> new CarbonArmorItem(ModArmorMaterials.CARBON, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CARBON_BOOTS = ITEMS.register("carbon_boots", () -> new CarbonArmorItem(ModArmorMaterials.CARBON, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<SwordItem> CARBON_SWORD = ITEMS.register("carbon_sword",
            () -> new ModSword(ModToolTiers.CARBON,
                    new Item.Properties().stacksTo(1).fireResistant(),
                    Component.literal(""),
                    new MobEffectInstance[]{}));
    public static final DeferredItem<Item> CARBON_PICKAXE = ITEMS.register("carbon_pickaxe", () -> new PickaxeItem(ModToolTiers.CARBON, new Item.Properties().fireResistant().attributes(PickaxeItem.createAttributes(ModToolTiers.CARBON, ModToolTiers.CARBON.getAttackDamageBonus()-10, ModToolTiers.CARBON.getSpeed()))));
    public static final DeferredItem<Item> CARBON_SHOVEL = ITEMS.register("carbon_shovel", () -> new ShovelItem(ModToolTiers.CARBON, new Item.Properties().fireResistant().attributes(ShovelItem.createAttributes(ModToolTiers.CARBON, ModToolTiers.CARBON.getAttackDamageBonus()-12, ModToolTiers.CARBON.getSpeed()))));
    public static final DeferredItem<Item> CARBON_AXE = ITEMS.register("carbon_axe", () -> new AxeItem(ModToolTiers.CARBON, new Item.Properties().fireResistant().attributes(AxeItem.createAttributes(ModToolTiers.CARBON, ModToolTiers.CARBON.getAttackDamageBonus()-7, ModToolTiers.CARBON.getSpeed()))));
    public static final DeferredItem<Item> CARBON_HOE = ITEMS.register("carbon_hoe", () -> new HoeItem(ModToolTiers.CARBON,  new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(ModToolTiers.CARBON, ModToolTiers.CARBON.getAttackDamageBonus()-13, ModToolTiers.CARBON.getSpeed()))));
    public static final DeferredItem<Item> CARBON_BOW = ITEMS.register("carbon_bow", () -> new ModBow(ModToolTiers.CARBON, new Item.Properties().durability(1500).fireResistant(), 20));

    public static final DeferredItem<SwordItem> INFINITY_SWORD = ITEMS.register("infinity_sword",
            () -> new ModSword(ModToolTiers.INFINITY,
                    new Item.Properties().stacksTo(1).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)),
                    Component.translatable("tooltip.infinity_nexus_mod.infinity_sword"),
                    new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 200, 2),
                            new MobEffectInstance(MobEffects.WITHER, 200, 2)
            }));
    public static final DeferredItem<SwordItem> INFINITY_3D_SWORD = ITEMS.register("infinity_3d_sword",
            () -> new ModSword(ModToolTiers.INFINITY,
                    new Item.Properties().stacksTo(1).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)),
                    Component.translatable("tooltip.infinity_nexus_mod.infinity_3d_sword"),
                    new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 200, 2),
                            new MobEffectInstance(MobEffects.WITHER, 200, 2)
                    }));
    public static final DeferredItem<Item> INFINITY_HAMMER = ITEMS.register("infinity_hammer", () -> new HammerItem(ModToolTiers.INFINITY,  new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(PickaxeItem.createAttributes(ModToolTiers.INFINITY, ModToolTiers.INFINITY.getAttackDamageBonus()-10, ModToolTiers.INFINITY.getSpeed()))));
    public static final DeferredItem<Item> INFINITY_PAXEL = ITEMS.register("infinity_paxel", () -> new PaxelItem(ModToolTiers.INFINITY,  new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(PickaxeItem.createAttributes(ModToolTiers.INFINITY, ModToolTiers.INFINITY.getAttackDamageBonus()-5, ModToolTiers.INFINITY.getSpeed())), Component.translatable("tooltip.infinity_nexus_mod.infinity_paxel"), true));
    public static final DeferredItem<Item> INFINITY_PICKAXE = ITEMS.register("infinity_pickaxe", () -> new PickaxeItem(ModToolTiers.INFINITY, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(PickaxeItem.createAttributes(ModToolTiers.INFINITY, ModToolTiers.INFINITY.getAttackDamageBonus()-10, ModToolTiers.INFINITY.getSpeed()))));
    public static final DeferredItem<Item> INFINITY_SHOVEL = ITEMS.register("infinity_shovel", () -> new ShovelItem(ModToolTiers.INFINITY, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(ShovelItem.createAttributes(ModToolTiers.INFINITY, ModToolTiers.INFINITY.getAttackDamageBonus()-12, ModToolTiers.INFINITY.getSpeed()))));
    public static final DeferredItem<Item> INFINITY_AXE = ITEMS.register("infinity_axe", () -> new AxeItem(ModToolTiers.INFINITY, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(AxeItem.createAttributes(ModToolTiers.INFINITY, ModToolTiers.INFINITY.getAttackDamageBonus()-7, ModToolTiers.INFINITY.getSpeed()))));
    public static final DeferredItem<Item> INFINITY_HOE = ITEMS.register("infinity_hoe", () -> new HoeItem(ModToolTiers.INFINITY, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(HoeItem.createAttributes(ModToolTiers.INFINITY, ModToolTiers.INFINITY.getAttackDamageBonus()-13, ModToolTiers.INFINITY.getSpeed()))));
    public static final DeferredItem<Item> INFINITY_BOW = ITEMS.register("infinity_bow", () -> new ModBow(ModToolTiers.INFINITY, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)), 50));

    public static final DeferredItem<Item> INFINITY_HELMET = ITEMS.register("infinity_helmet", () -> new InfinityArmorItem(ModArmorMaterials.INFINITY, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true))));
    public static final DeferredItem<Item> INFINITY_CHESTPLATE = ITEMS.register("infinity_chestplate", () -> new InfinityArmorItem(ModArmorMaterials.INFINITY, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true))));
    public static final DeferredItem<Item> INFINITY_LEGGINGS = ITEMS.register("infinity_leggings", () -> new InfinityArmorItem(ModArmorMaterials.INFINITY, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true))));
    public static final DeferredItem<Item> INFINITY_BOOTS = ITEMS.register("infinity_boots", () -> new InfinityArmorItem(ModArmorMaterials.INFINITY, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true))));

    public static final DeferredItem<SwordItem> IMPERIAL_INFINITY_SWORD = ITEMS.register("imperial_infinity_sword",
            () -> new ModSword(ModToolTiers.IMPERIAL,
                    new Item.Properties().stacksTo(1).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)),
                    Component.translatable("tooltip.infinity_nexus_mod.imperial_infinity_sword"),
                    new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 200, 3),
                            new MobEffectInstance(MobEffects.WITHER, 200, 3),
                            new MobEffectInstance(MobEffects.POISON, 200, 3)
                    }));
    public static final DeferredItem<SwordItem> IMPERIAL_INFINITY_3D_SWORD = ITEMS.register("imperial_infinity_3d_sword",
            () -> new ModSword(ModToolTiers.IMPERIAL,
                    new Item.Properties().stacksTo(1).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)),
                    Component.translatable("tooltip.infinity_nexus_mod.imperial_infinity_3d_sword"),
                    new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 200, 3),
                            new MobEffectInstance(MobEffects.WITHER, 200, 3),
                            new MobEffectInstance(MobEffects.POISON, 200, 3)
                    }));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_HAMMER = ITEMS.register("imperial_infinity_hammer", () -> new HammerItem(ModToolTiers.IMPERIAL, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(PickaxeItem.createAttributes(ModToolTiers.IMPERIAL, ModToolTiers.IMPERIAL.getAttackDamageBonus()-10, ModToolTiers.IMPERIAL.getSpeed()))));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_PAXEL = ITEMS.register("imperial_infinity_paxel", () -> new PaxelItem(ModToolTiers.IMPERIAL,  new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(PickaxeItem.createAttributes(ModToolTiers.IMPERIAL, ModToolTiers.IMPERIAL.getAttackDamageBonus()-5, ModToolTiers.IMPERIAL.getSpeed())), Component.translatable("tooltip.infinity_nexus_mod.infinity_paxel"), true));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_PICKAXE = ITEMS.register("imperial_infinity_pickaxe", () -> new PickaxeItem(ModToolTiers.IMPERIAL,  new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(PickaxeItem.createAttributes(ModToolTiers.IMPERIAL, ModToolTiers.IMPERIAL.getAttackDamageBonus()-10, ModToolTiers.IMPERIAL.getSpeed()))));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_SHOVEL = ITEMS.register("imperial_infinity_shovel", () -> new ShovelItem(ModToolTiers.IMPERIAL, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(ShovelItem.createAttributes(ModToolTiers.IMPERIAL, ModToolTiers.IMPERIAL.getAttackDamageBonus()-12, ModToolTiers.IMPERIAL.getSpeed()))));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_AXE = ITEMS.register("imperial_infinity_axe", () -> new AxeItem(ModToolTiers.IMPERIAL, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(AxeItem.createAttributes(ModToolTiers.IMPERIAL, ModToolTiers.IMPERIAL.getAttackDamageBonus()-7, ModToolTiers.IMPERIAL.getSpeed()))));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_HOE = ITEMS.register("imperial_infinity_hoe", () -> new HoeItem(ModToolTiers.IMPERIAL,  new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).attributes(HoeItem.createAttributes(ModToolTiers.IMPERIAL, ModToolTiers.IMPERIAL.getAttackDamageBonus()-13, ModToolTiers.IMPERIAL.getSpeed()))));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_BOW = ITEMS.register("imperial_infinity_bow", () -> new ModBow(ModToolTiers.IMPERIAL, new Item.Properties().fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true)),60));

    public static final DeferredItem<Item> IMPERIAL_INFINITY_HELMET = ITEMS.register("imperial_infinity_helmet", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true))));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_CHESTPLATE = ITEMS.register("imperial_infinity_chestplate", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true))));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_LEGGINGS = ITEMS.register("imperial_infinity_leggings", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true))));
    public static final DeferredItem<Item> IMPERIAL_INFINITY_BOOTS = ITEMS.register("imperial_infinity_boots", () -> new ImperialInfinityArmorItem(ModArmorMaterials.IMPERIAL, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().component(DataComponents.UNBREAKABLE, new Unbreakable(true))));

    public static final DeferredItem<Item> BUCKET_LUBRICANT = ITEMS.register("bucket_lubricant", () -> new BucketItem(ModFluids.LUBRICANT_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final DeferredItem<Item> BUCKET_ETHANOL = ITEMS.register("bucket_ethanol", () -> new BucketItem(ModFluids.ETHANOL_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final DeferredItem<Item> BUCKET_OIL = ITEMS.register("bucket_oil", () -> new BucketItem(ModFluids.OIL_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final DeferredItem<Item> BUCKET_VINEGAR = ITEMS.register("bucket_vinegar", () -> new BucketItem(ModFluids.VINEGAR_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final DeferredItem<Item> BUCKET_SUGARCANE_JUICE = ITEMS.register("bucket_sugarcane_juice", () -> new BucketItem(ModFluids.SUGARCANE_JUICE_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final DeferredItem<Item> BUCKET_WINE = ITEMS.register("bucket_wine", () -> new BucketItem(ModFluids.WINE_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final DeferredItem<Item> BUCKET_EXPERIENCE = ITEMS.register("bucket_experience", () -> new BucketItem(ModFluids.EXPERIENCE_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final DeferredItem<Item> BUCKET_STARLIQUID = ITEMS.register("bucket_starliquid", () -> new BucketItem(ModFluids.STARLIQUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));
    public static final DeferredItem<Item> BUCKET_POTATO_JUICE = ITEMS.register("bucket_potato_juice", () -> new BucketItem(ModFluids.POTATO_JUICE_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) ));

    public static final DeferredItem<Item> ALCOHOL_BOTTLE = ITEMS.register("alcohol_bottle", () -> new AlcoholBottle(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final DeferredItem<Item> SUGARCANE_JUICE_BOTTLE = ITEMS.register("sugarcane_juice_bottle", () -> new BottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final DeferredItem<Item> VINEGAR_BOTTLE = ITEMS.register("vinegar_bottle", () -> new BottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final DeferredItem<Item> WINE_BOTTLE = ITEMS.register("wine_bottle", () -> new BottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> HAMMER_RANGE_UPGRADE = ITEMS.register("hammer_range_upgrade", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> BASIC_CIRCUIT = ITEMS.register("basic_circuit", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final DeferredItem<Item> ADVANCED_CIRCUIT = ITEMS.register("advanced_circuit", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));

    public static final DeferredItem<Item> TERRAIN_MARKER = ITEMS.register("terrain_marker", () -> new Item(new Item.Properties().stacksTo(1).component(DataComponents.UNBREAKABLE, new Unbreakable(true))));
    public static final DeferredItem<Item> SOLAR_PANE = ITEMS.register("solar_pane", () -> new SolarUpgrade(new Item.Properties().rarity(Rarity.COMMON),8));
    public static final DeferredItem<Item> SOLAR_PANE_ADVANCED = ITEMS.register("solar_pane_advanced", () -> new SolarUpgrade(new Item.Properties().rarity(Rarity.UNCOMMON), 73));
    public static final DeferredItem<Item> SOLAR_PANE_ULTIMATE = ITEMS.register("solar_pane_ultimate", () -> new SolarUpgrade(new Item.Properties().rarity(Rarity.RARE), 648));
    public static final DeferredItem<Item> SOLAR_PANE_QUANTUM = ITEMS.register("solar_pane_quantum", () -> new SolarUpgrade(new Item.Properties().rarity(Rarity.EPIC), 5832));
    public static final DeferredItem<Item> SOLAR_PANE_PHOTONIC = ITEMS.register("solar_pane_photonic", () -> new SolarUpgrade(new Item.Properties().rarity(Rarity.EPIC), 52488));

    public static final DeferredItem<Item> KNIFE = ITEMS.register("knife", () -> new Knife(new Item.Properties().stacksTo(1).durability(500)));
    public static final DeferredItem<Item> TRANSLOCATOR_LINK = ITEMS.register("translocator_link", () -> new TranslocatorLink(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ASGREON_SPAWN_EGG = ITEMS.register("asgreon_spawn_egg", () -> new DeferredSpawnEggItem( ModEntities.ASGREON, 0x7e9680, 0xc5d1c5,new Item.Properties()));
    public static final DeferredItem<Item> FLARON_SPAWN_EGG = ITEMS.register("flaron_spawn_egg", () -> new DeferredSpawnEggItem( ModEntities.FLARON, 0x7e9680, 0xc5d1c5,new Item.Properties()));

    public static final DeferredItem<Item> FRACTAL = ITEMS.register("fractal", FractalArmorItem::new);
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}