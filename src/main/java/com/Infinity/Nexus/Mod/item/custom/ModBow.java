package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModBow extends BowItem {
    private final int damage;
    protected final Tier tier;
    public ModBow(Tier tier, Properties properties, int damage) {
        super(properties.stacksTo(1));
        this.damage = damage;
        this.tier = tier;
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        arrow.setBaseDamage(damage);
        return arrow;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§7When in any hand"));
        tooltipComponents.add(Component.translatable("§2"+this.damage+" Attack Damage"));
    }

    @Override
    public int getEnchantmentValue() {
        return super.getEnchantmentValue();
    }

    public Tier getTier() {
        return tier;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }
}


