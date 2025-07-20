package com.Infinity.Nexus.Mod.screen.compactor;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.*;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.CompactorAutoBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class CompactorMenu extends BaseAbstractContainerMenu {
    public final CompactorAutoBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 29;

    public CompactorMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (CompactorAutoBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2), new RestrictedItemStackHandler(slots));
    }

    public CompactorMenu(int pContainerId, Inventory inv, CompactorAutoBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.COMPACTOR_AUTO_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new SingleItemSlot(iItemHandler, 0, 11, 1 ));
        this.addSlot(new SingleItemSlot(iItemHandler, 1, 28, 1 ));
        this.addSlot(new SingleItemSlot(iItemHandler, 2, 45, 1 ));
        this.addSlot(new SingleItemSlot(iItemHandler, 3, 11, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 4, 28, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 5, 45, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 6, 11, 35));
        this.addSlot(new SingleItemSlot(iItemHandler, 7, 28, 35));
        this.addSlot(new SingleItemSlot(iItemHandler, 8, 45, 35));

        this.addSlot(new SingleItemSlot(iItemHandler, 9, 63, 1));
        this.addSlot(new SingleItemSlot(iItemHandler, 10, 80, 1));
        this.addSlot(new SingleItemSlot(iItemHandler, 11, 97, 1));
        this.addSlot(new SingleItemSlot(iItemHandler, 12, 63, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 13, 80, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 14, 97, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 15, 63, 35));
        this.addSlot(new SingleItemSlot(iItemHandler, 16, 80, 35));
        this.addSlot(new SingleItemSlot(iItemHandler, 17, 97, 35));

        this.addSlot(new SingleItemSlot(iItemHandler, 18, 115, 1));
        this.addSlot(new SingleItemSlot(iItemHandler, 19, 132, 1));
        this.addSlot(new SingleItemSlot(iItemHandler, 20, 149, 1));
        this.addSlot(new SingleItemSlot(iItemHandler, 21, 115, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 22, 132, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 23, 149, 18));
        this.addSlot(new SingleItemSlot(iItemHandler, 24, 115, 35));
        this.addSlot(new SingleItemSlot(iItemHandler, 25, 132, 35));
        this.addSlot(new SingleItemSlot(iItemHandler, 26, 149, 35));

        this.addSlot(new ResultSlot(iItemHandler, 27, 149, 53));

        this.addSlot(new ComponentSlot(iItemHandler, 28, 97, 54));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public CompactorAutoBlockEntity getBlockEntity(){
        return blockEntity;
    }
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 32;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.COMPACTOR_AUTO.get());
    }

}