package com.Infinity.Nexus.Mod.screen.condenser;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.ComponentSlot;
import com.Infinity.Nexus.Core.slots.InputSlot;
import com.Infinity.Nexus.Core.slots.ResultSlot;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.MatterCondenserBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class CondenserMenu extends BaseAbstractContainerMenu {
    public final MatterCondenserBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 3;

    public CondenserMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (MatterCondenserBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4), new RestrictedItemStackHandler(slots));
    }

    public CondenserMenu(int pContainerId, Inventory inv, MatterCondenserBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.MATTER_CONDENSER_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new InputSlot(iItemHandler, 0, 80, 6));
        this.addSlot(new ResultSlot(iItemHandler, 1, 80, 52));

        this.addSlot(new ComponentSlot(iItemHandler, 2, 8, 29));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public MatterCondenserBlockEntity getBlockEntity(){
        return blockEntity;
    }
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 62;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.MATTER_CONDENSER.get());
    }
}