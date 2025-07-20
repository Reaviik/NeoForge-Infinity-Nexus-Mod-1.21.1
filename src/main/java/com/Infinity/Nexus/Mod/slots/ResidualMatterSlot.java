package com.Infinity.Nexus.Mod.slots;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.slots.RestrictiveSlot;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.item.custom.SolarUpgrade;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class ResidualMatterSlot extends RestrictiveSlot {

    public ResidualMatterSlot(RestrictedItemStackHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition, 64);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return stack.is(ModItemsProgression.RESIDUAL_MATTER.get());
    }
}