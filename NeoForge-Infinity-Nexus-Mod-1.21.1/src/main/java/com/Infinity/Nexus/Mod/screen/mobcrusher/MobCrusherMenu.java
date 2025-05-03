package com.Infinity.Nexus.Mod.screen.mobcrusher;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.*;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.MobCrusherBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class MobCrusherMenu extends BaseAbstractContainerMenu {
    public final MobCrusherBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 17;

    public MobCrusherMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (MobCrusherBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(12), new RestrictedItemStackHandler(slots));
    }

    public MobCrusherMenu(int pContainerId, Inventory inv, MobCrusherBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.MOB_CRUSHER_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity =  entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new ResultSlot(iItemHandler, 0, 80, 11));
        this.addSlot(new ResultSlot(iItemHandler, 1, 98, 11));
        this.addSlot(new ResultSlot(iItemHandler, 2, 116, 11));
        this.addSlot(new ResultSlot(iItemHandler, 3, 80, 29));
        this.addSlot(new ResultSlot(iItemHandler, 4, 98, 29));
        this.addSlot(new ResultSlot(iItemHandler, 5, 116, 29));
        this.addSlot(new ResultSlot(iItemHandler, 6, 80, 47));
        this.addSlot(new ResultSlot(iItemHandler, 7, 98, 47));
        this.addSlot(new ResultSlot(iItemHandler, 8, 116, 47));

        this.addSlot(new UpgradeSlot(iItemHandler, 9, -11, 11));
        this.addSlot(new UpgradeSlot(iItemHandler, 10, -11,23));
        this.addSlot(new UpgradeSlot(iItemHandler, 11, -11,35));
        this.addSlot(new UpgradeSlot(iItemHandler, 12, -11,47));

        this.addSlot(new ComponentSlot(iItemHandler, 13, 8, 29));

        this.addSlot(new SwordSlot(iItemHandler, 14, 44, 11));
        this.addSlot(new LinkSlot(iItemHandler, 15, 44, 29));
        this.addSlot(new FuelSlot(iItemHandler, 16, 44, 47));
        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public MobCrusherBlockEntity getBlockEntity(){
        return blockEntity;
    }
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 14; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.MOB_CRUSHER.get());
    }
}