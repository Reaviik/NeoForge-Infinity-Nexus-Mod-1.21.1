package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Core.utils.GetResourceLocation;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    //Carbon
    public static final Holder<ArmorMaterial> CARBON = register("carbon",
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 8);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 4);
                map.put(ArmorItem.Type.BODY, 21);
            }), 16, 2f, 0.1f, ModItemsProgression.CARBON_PLATE);

    // Infinity
    public static final Holder<ArmorMaterial> INFINITY = register("infinity",
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 15);
                map.put(ArmorItem.Type.CHESTPLATE, 25);
                map.put(ArmorItem.Type.LEGGINGS, 20);
                map.put(ArmorItem.Type.BOOTS, 13);
                map.put(ArmorItem.Type.BODY, 73);
            }), ModToolTiers.INFINITY.getEnchantmentValue(), 4f, 8f, ModItemsAdditions.INFINITY_INGOT);

    // Imperial Infinity
    public static final Holder<ArmorMaterial> IMPERIAL = register("imperial_infinity",
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 0);
                map.put(ArmorItem.Type.CHESTPLATE, 0);
                map.put(ArmorItem.Type.LEGGINGS, 0);
                map.put(ArmorItem.Type.BOOTS, 0);
                map.put(ArmorItem.Type.BODY, 1000000);
            }), ModToolTiers.INFINITY.getEnchantmentValue(), 4f, 8f, ModItemsAdditions.INFINIUM_STELLARUM_INGOT);

    // Método de registro (comum para todos os materiais)
    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                  int enchantability, float toughness, float knockbackResistance,
                                                  Supplier<Item> ingredientItem) {
        ResourceLocation location = GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, name);
        Holder<SoundEvent> equipSound = SoundEvents.ARMOR_EQUIP_NETHERITE;
        Supplier<Ingredient> ingredient = () -> Ingredient.of(ingredientItem.get());
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(typeProtection, enchantability, equipSound, ingredient, layers, toughness, knockbackResistance));
    }

    public static void register(IEventBus eventBus) {

    }
}