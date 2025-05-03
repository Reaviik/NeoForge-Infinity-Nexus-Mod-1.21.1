package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FactoryHandler {
    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 16 -> true; //Output
            case 17,18,19,20,21 -> direction == Direction.UP; //Upgrade and Component
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack); //Input
            case 16 -> false; //Output
            case 17,18,19,20 -> ModUtils.isUpgrade(stack); //Upgrade
            case 21 -> ModUtils.isComponent(stack); //Component
            default -> false;
        };
    }
}
