package com.Infinity.Nexus.Mod.screen.smeltery;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.ComponentSlot;
import com.Infinity.Nexus.Core.slots.InputSlot;
import com.Infinity.Nexus.Core.slots.ResultSlot;
import com.Infinity.Nexus.Core.slots.UpgradeSlot;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.SmelteryBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class SmelteryMenu extends BaseAbstractContainerMenu {
    public final SmelteryBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 9;

    public SmelteryMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (SmelteryBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2), new RestrictedItemStackHandler(slots));
    }

    public SmelteryMenu(int pContainerId, Inventory inv, SmelteryBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.SMELTERY_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new InputSlot(iItemHandler, 0, 57, 13));
        this.addSlot(new InputSlot(iItemHandler, 1, 80, 6));
        this.addSlot(new InputSlot(iItemHandler, 2, 103, 13));

        this.addSlot(new ResultSlot(iItemHandler, 3, 80, 47));

        this.addSlot(new UpgradeSlot(iItemHandler, 4, -11, 11));
        this.addSlot(new UpgradeSlot(iItemHandler, 5,  -11,23));
        this.addSlot(new UpgradeSlot(iItemHandler, 6,  -11,35));
        this.addSlot(new UpgradeSlot(iItemHandler, 7,  -11,47));

        this.addSlot(new ComponentSlot(iItemHandler, 8, 8, 29));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public SmelteryBlockEntity getBlockEntity(){
        return blockEntity;
    }
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 29; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.SMELTERY.get());
    }
}