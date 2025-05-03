package com.Infinity.Nexus.Mod.screen.factory;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.ComponentSlot;
import com.Infinity.Nexus.Core.slots.ResultSlot;
import com.Infinity.Nexus.Core.slots.SingleItemSlot;
import com.Infinity.Nexus.Core.slots.UpgradeSlot;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.FactoryBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;

public class FactoryMenu extends BaseAbstractContainerMenu {
    public final FactoryBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 22;

    public FactoryMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (FactoryBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2), new RestrictedItemStackHandler(slots));
    }

    public FactoryMenu(int pContainerId, Inventory inv, FactoryBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.FACTORY_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new SingleItemSlot(iItemHandler, 0, 40 , -12));
        this.addSlot(new SingleItemSlot(iItemHandler, 1, 60 , -10));
        this.addSlot(new SingleItemSlot(iItemHandler, 2, 80 , -8));
        this.addSlot(new SingleItemSlot(iItemHandler, 3, 100 , -10));
        this.addSlot(new SingleItemSlot(iItemHandler, 4, 120 , -12));

        this.addSlot(new SingleItemSlot(iItemHandler, 5, 42, 8));
        this.addSlot(new SingleItemSlot(iItemHandler, 6, 118 , 8));

        this.addSlot(new SingleItemSlot(iItemHandler, 7, 44 , 28));

        this.addSlot(new ResultSlot(iItemHandler, 16, 80, 28));

        this.addSlot(new SingleItemSlot(iItemHandler, 8, 116, 28));

        this.addSlot(new SingleItemSlot(iItemHandler, 9, 42, 48));
        this.addSlot(new SingleItemSlot(iItemHandler, 10, 118, 48));

        this.addSlot(new SingleItemSlot(iItemHandler, 11,  40,68));
        this.addSlot(new SingleItemSlot(iItemHandler, 12,  60,66));
        this.addSlot(new SingleItemSlot(iItemHandler, 13,  80,64));
        this.addSlot(new SingleItemSlot(iItemHandler, 14, 100,66));
        this.addSlot(new SingleItemSlot(iItemHandler, 15, 120,68));

        this.addSlot(new UpgradeSlot(iItemHandler, 17, -11, 11));
        this.addSlot(new UpgradeSlot(iItemHandler, 18, -11,23));
        this.addSlot(new UpgradeSlot(iItemHandler, 19, -11,35));
        this.addSlot(new UpgradeSlot(iItemHandler, 20, -11,47));


        this.addSlot(new ComponentSlot(iItemHandler, 21, 8, 28));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public FactoryBlockEntity getBlockEntity(){
        return blockEntity;
    }
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 117; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.FACTORY.get());
    }

    @Override
    public void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 111 + i * 18));
            }
        }
    }
    @Override
    public void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 169));
        }
    }
}