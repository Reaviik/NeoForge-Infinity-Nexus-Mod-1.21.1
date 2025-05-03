package com.Infinity.Nexus.Mod.screen.assembler;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.screen.BaseAbstractContainerMenu;
import com.Infinity.Nexus.Core.slots.*;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.entity.AssemblerBlockEntity;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;

public class AssemblerMenu extends BaseAbstractContainerMenu {
    public final AssemblerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private static final int slots = 16;

    public AssemblerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, (AssemblerBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2), new RestrictedItemStackHandler(slots));
    }

    public AssemblerMenu(int pContainerId, Inventory inv, AssemblerBlockEntity entity, ContainerData data, RestrictedItemStackHandler iItemHandler) {
        super(ModMenuTypes.ASSEMBLY_MENU.get(), pContainerId, slots);
        checkContainerSize(inv, slots);
        blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new InputSlot(iItemHandler, 0, 58 , 6 ));
        this.addSlot(new InputSlot(iItemHandler, 1, 81 , 6 ));
        this.addSlot(new InputSlot(iItemHandler, 2, 104, 6 ));

        this.addSlot(new InputSlot(iItemHandler, 3, 58 , 29));

        this.addSlot(new ResultSlot(iItemHandler, 8, 81 , 29));

        this.addSlot(new InputSlot(iItemHandler, 4, 104, 29));

        this.addSlot(new InputSlot(iItemHandler, 5, 58 , 52));
        this.addSlot(new InputSlot(iItemHandler, 6, 81 , 52));
        this.addSlot(new InputSlot(iItemHandler, 7, 104, 52));


        this.addSlot(new UpgradeSlot(iItemHandler, 9, -11, 11));
        this.addSlot(new UpgradeSlot(iItemHandler, 10, -11,23));
        this.addSlot(new UpgradeSlot(iItemHandler, 11, -11,35));
        this.addSlot(new UpgradeSlot(iItemHandler, 12, -11,47));

        this.addSlot(new ComponentSlot(iItemHandler, 13, 8, 29));

        this.addSlot(new FluidItemSlot(iItemHandler, 14, 125, 6));

        this.addSlot(new ResultSlot(iItemHandler, 15, 125, 52));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public AssemblerBlockEntity getBlockEntity(){
        return blockEntity;
    }
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 30;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocksAdditions.ASSEMBLY.get());
    }

}