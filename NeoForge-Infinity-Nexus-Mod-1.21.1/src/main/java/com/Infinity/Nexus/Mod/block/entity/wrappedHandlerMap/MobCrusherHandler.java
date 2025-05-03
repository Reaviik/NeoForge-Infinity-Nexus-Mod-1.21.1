package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

public class MobCrusherHandler {
    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 0,1,2,3,4,5,6,7,8 -> true;
            case 9,10,11,12,13 -> direction == Direction.UP;
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 9,10,11,12 -> ModUtils.isUpgrade(stack);
            case 13 -> ModUtils.isComponent(stack);
            case 14 -> stack.getItem() instanceof SwordItem;
            case 15 -> stack.is(ModItems.LINKING_TOOL.get().asItem());
            case 16 -> stack.getBurnTime(null) > 0;
            default -> false;
        };
    }
}
