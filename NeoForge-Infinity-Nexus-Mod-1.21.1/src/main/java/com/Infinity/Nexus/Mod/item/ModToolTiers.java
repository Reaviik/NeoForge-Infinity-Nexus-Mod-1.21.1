package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.utils.ModTags;
import com.google.common.base.Suppliers;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum ModToolTiers implements Tier {
    /*
    public static final Tier CARBON = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_CARBON_TOOL,
            1500, 8.0F, 0F, 30,
            () -> Ingredient.of(ModItemsProgression.CARBON_PLATE.get()));

    public static final Tier INFINITY = new SimpleTier(
             ModTags.Blocks.INCORRECT_FOR_INFINITY_TOOL,
            0, 30.0F, 0F, 70,
            () -> Ingredient.of(ModItemsAdditions.INFINITY_INGOT.get()));

    public static final Tier IMPERIAL = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_IMPERIAL_TOOL,
            0, 35, -1, 80,
            () -> Ingredient.of(ModItemsAdditions.INFINITY_INGOT.get()));
}
*/
    CARBON (ModTags.Blocks.INCORRECT_FOR_CARBON_TOOL, 1500, 8.0F, 0F, 30, () -> Ingredient.of(ModItemsProgression.CARBON_PLATE.get())),
    INFINITY (ModTags.Blocks.INCORRECT_FOR_INFINITY_TOOL, 0, 30.0F, 0F, 70, () -> Ingredient.of(ModItemsAdditions.INFINITY_INGOT.get())),
    IMPERIAL (ModTags.Blocks.INCORRECT_FOR_IMPERIAL_TOOL, 0, 35, -1, 80, () -> Ingredient.of(ModItemsAdditions.INFINIUM_STELLARUM_INGOT.get()));

    private final TagKey<Block> incorrectForDrops;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModToolTiers(TagKey<Block> incorrect, int maxUses, float efficiency, float damage, int enchant, Supplier<Ingredient> repairMaterial) {
        this.incorrectForDrops = incorrect;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = damage;
        this.enchantability = enchant;
        this.repairMaterial = Suppliers.memoize(repairMaterial::get);
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectForDrops;
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}