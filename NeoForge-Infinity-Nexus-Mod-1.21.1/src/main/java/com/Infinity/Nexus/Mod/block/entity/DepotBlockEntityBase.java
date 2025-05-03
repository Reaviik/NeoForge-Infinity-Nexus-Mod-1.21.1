package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DepotBlockEntityBase extends BlockEntity {
    int timer = 0;
    int maxTimer = 20;
    int count = 1;
    final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);

    protected final ContainerData data;

    public DepotBlockEntityBase(@NotNull BlockEntityType<? extends DepotBlockEntityBase> blockEntityType, BlockPos pPos, BlockState pBlockState) {
        super(blockEntityType, pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return count;
            }

            @Override
            public void set(int pIndex, int pValue) {
            }

            @Override
            public int getCount() {
                return count;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = Lazy.of(() -> itemHandler);
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.putInt("count", this.count);
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        this.count = pTag.getInt("count");
        super.loadAdditional(pTag, registries);

        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        if(canPlace(pPos)){
            place();
            setChanged(pLevel, pPos, pState);
        }
    }

    protected void place() {
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            ItemStack stack2 = stack.copy();
            stack2.setCount(1);
            ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, stack2);
            entity.setDeltaMovement(Vec3.ZERO);
            entity.setUnlimitedLifetime();
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
            itemHandler.getStackInSlot(0).shrink(1);
        }
    }

    protected boolean hasEntity(BlockPos pPos) {
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(pPos));

        if (!entities.isEmpty()) {
            entities.forEach(entity -> {
               entity.setPickUpDelay(10);
            });
        }
        return entities.isEmpty();
    }

    boolean hasProgressFinished() {
        if (timer < maxTimer) {
            timer++;
        }else{
            timer = 0;
        }
        return timer >= maxTimer;
    }

    protected boolean canPlace(BlockPos pPos) {
        return hasEntity(pPos);
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }


    public void setStack(ItemStack itemStack, Player player) {
        if(this.itemHandler.getStackInSlot(0).isEmpty()) {
            this.itemHandler.setStackInSlot(0, itemStack.copy());
            player.getMainHandItem().shrink(player.getMainHandItem().getCount());
        }
    }
    public void setCount(Player player) {
        this.count =  this.count < 64 ? (this.count + 1) : 1;
        player.sendSystemMessage(Component.literal(InfinityNexusMod.message + "Quantidade alterada para: " + this.count));
    }
}