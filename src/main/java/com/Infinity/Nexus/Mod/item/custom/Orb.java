package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class Orb extends Item {
    int stage = 0;
    public Orb(Properties pProperties, int stage) {
        super(pProperties);
        this.stage = stage;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.orb_stage").append(Component.literal(" " + stage)));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
