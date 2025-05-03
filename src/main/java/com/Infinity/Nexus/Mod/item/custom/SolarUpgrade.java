package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SolarUpgrade extends Item {
    private final int energy;
    public SolarUpgrade(Properties pProperties, int energy) {
        super(pProperties);
        this.energy = energy;
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.solar_upgrade").append(Component.literal(" " + energy +" FE/t")));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
