package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PressHandler {

    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 2 -> true; //Output
            case 3,4,5,6 -> direction == Direction.UP; //Upgrade and Component
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 0,1 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack); //Input
            case 3,4,5,6 -> ModUtils.isUpgrade(stack); //Upgrade
            case 7 -> ModUtils.isComponent(stack); //Component
            default -> false;
        };
    }
}
