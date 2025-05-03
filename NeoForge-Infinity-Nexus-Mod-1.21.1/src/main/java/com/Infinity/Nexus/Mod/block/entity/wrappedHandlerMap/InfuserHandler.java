package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InfuserHandler {
    public static boolean extract(int slot, Direction direction) {
        return slot == 1;
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return slot == 0;
    }
}
