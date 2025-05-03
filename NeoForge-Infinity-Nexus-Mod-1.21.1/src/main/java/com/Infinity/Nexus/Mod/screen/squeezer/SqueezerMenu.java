package com.Infinity.Nexus.Mod.screen.squeezer;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.*;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.SqueezerBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class SqueezerMenu extends BaseAbstractContainerMenu {
    public final SqueezerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 9;

    public SqueezerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (SqueezerBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3), new RestrictedItemStackHandler(slots));
    }

    public SqueezerMenu(int pContainerId, Inventory inv, SqueezerBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.SQUEEZER_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new InputSlot(iItemHandler, 0, 80, 11));
        this.addSlot(new ResultSlot(iItemHandler, 1, 80, 47));

        this.addSlot(new FluidItemSlot(iItemHandler, 2, 125, 6));

        this.addSlot(new ResultSlot(iItemHandler, 3, 125, 52));

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
    public SqueezerBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 18;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.SQUEEZER.get());
    }
}