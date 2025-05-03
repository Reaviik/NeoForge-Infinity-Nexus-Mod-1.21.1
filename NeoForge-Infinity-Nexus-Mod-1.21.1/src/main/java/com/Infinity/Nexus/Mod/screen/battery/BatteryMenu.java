package com.Infinity.Nexus.Mod.screen.battery;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.ComponentSlot;
import com.Infinity.Nexus.Core.slots.InputSlot;
import com.Infinity.Nexus.Core.slots.ResultSlot;
import com.Infinity.Nexus.Core.slots.UpgradeSlot;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.BatteryBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class BatteryMenu extends BaseAbstractContainerMenu {
    public final BatteryBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 7;

    public BatteryMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv,(BatteryBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2), new RestrictedItemStackHandler(slots));
    }

    public BatteryMenu(int pContainerId, Inventory inv, BatteryBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.BATTERY_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new InputSlot(iItemHandler, 0, 26, 11));
        this.addSlot(new ResultSlot(iItemHandler, 1, 26, 47));

        this.addSlot(new UpgradeSlot(iItemHandler, 2, -11, 11));
        this.addSlot(new UpgradeSlot(iItemHandler, 3,  -11,23));
        this.addSlot(new UpgradeSlot(iItemHandler, 4,  -11,35));
        this.addSlot(new UpgradeSlot(iItemHandler, 5,  -11,47));

        this.addSlot(new ComponentSlot(iItemHandler, 6, 8, 29));

        addDataSlots(data);
    }

    public BatteryBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.BATTERY.get());
    }
}