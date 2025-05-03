package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RecyclerHandler {

    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 1 -> true; //Output
            case 2,3,4,5,6 -> direction == Direction.UP; //Upgrade and Component
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 0 -> true; //Input
            case 2,3,4,5 -> ModUtils.isUpgrade(stack); //Upgrade
            case 6 -> ModUtils.isComponent(stack); //Component
            default -> false;
        };
    }
}
