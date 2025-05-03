package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MechanicalUserHandler {

    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 1 -> direction == Direction.UP; //Upgrade and Component
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 0 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack); //Input
            case 1 -> ModUtils.isComponent(stack);
            default -> false;
        };
    }
}
