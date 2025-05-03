package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MatterCondenserHandler {
    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 1 -> true; //Upgrade and Component
            case 2 -> direction == Direction.UP; //Upgrade and Component
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 0 -> stack.is(ModItemsProgression.RESIDUAL_MATTER.get()); //Matter
            case 2 -> ModUtils.isComponent(stack); //Component
            default -> false;
        };
    }
}
