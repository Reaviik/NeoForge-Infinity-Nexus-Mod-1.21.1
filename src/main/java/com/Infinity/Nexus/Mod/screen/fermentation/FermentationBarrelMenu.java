package com.Infinity.Nexus.Mod.screen.fermentation;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.FluidItemSlot;
import com.Infinity.Nexus.Core.slots.InputSlot;
import com.Infinity.Nexus.Core.slots.ResultSlot;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.FermentationBarrelBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class FermentationBarrelMenu extends BaseAbstractContainerMenu {
    public final FermentationBarrelBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 4;

    public FermentationBarrelMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (FermentationBarrelBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2), new RestrictedItemStackHandler(slots));
    }

    public FermentationBarrelMenu(int pContainerId, Inventory inv, FermentationBarrelBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.FERMENTATION_BARREL_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new FluidItemSlot(iItemHandler, 0, 44, 6));
        this.addSlot(new ResultSlot(iItemHandler, 1, 44, 52));


        this.addSlot(new InputSlot(iItemHandler, 2, 116, 6));
        this.addSlot(new ResultSlot(iItemHandler, 3, 116, 52));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public FermentationBarrelBlockEntity getBlockEntity(){
        return blockEntity;
    }
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 27; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.FERMENTATION_BARREL.get());
    }

}