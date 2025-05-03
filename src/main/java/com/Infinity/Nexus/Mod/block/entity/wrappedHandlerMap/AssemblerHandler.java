package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.utils.FluidUtils;
import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AssemblerHandler {
    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 8 -> true; //Output
            case 9,10,11,12,13 -> direction == Direction.UP; //Upgrade an Component
            case 15 -> true; //Fluid Item
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 0,1,2,3,4,5,6,7 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack); //Input
            case 9,10,11,12 -> ModUtils.isUpgrade(stack); //Upgrade
            case 13 -> ModUtils.isComponent(stack); //Component
            case 14-> FluidUtils.isFluidHandlerItem(stack);
            default -> false;
        };
    }
}
