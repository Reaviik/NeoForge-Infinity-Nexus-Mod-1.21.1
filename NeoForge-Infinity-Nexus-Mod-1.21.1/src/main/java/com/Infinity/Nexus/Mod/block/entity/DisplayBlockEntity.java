package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.custom.ItemDisplay;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class DisplayBlockEntity extends BlockEntity {
    protected final ContainerData data;
    float rotation = 0;
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    public ItemStack getRenderStack(int slot){
        return this.itemHandler.getStackInSlot(0).isEmpty() ? ItemStack.EMPTY : this.itemHandler.getStackInSlot(0);
    }
    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);

    public DisplayBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DISPLAY_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return 0;
            }

            @Override
            public void set(int pIndex, int pValue) {

            }

            @Override
            public int getCount() {
                return 0;
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
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        super.saveAdditional(pTag , registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
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


    public float getRotation() {
        return rotation = rotation < 360F ? (float) (rotation + (ConfigUtils.display_rotation_speed_multiplier)) : 0.0F;
    }
    public void setRenderStack(ItemStack itemStack, Player player) {

        if (this.itemHandler.getStackInSlot(0).isEmpty()) {
            if(!player.getMainHandItem().isEmpty()) {
                //Slot Vázio
                //Mão Cheia
                ItemStack stack = itemStack.copy();
                this.itemHandler.setStackInSlot(0, stack);
                level.playSound(null, this.getBlockPos(), SoundEvents.ARMOR_STAND_FALL, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.isCreative()) {
                    //Não Criativo
                    player.getMainHandItem().shrink(stack.getCount());
                }
                player.sendSystemMessage(Component.literal(InfinityNexusMod.message + " Display atualizado!"));
                setBlockModel(this.itemHandler.getStackInSlot(0));
            }
        }else{
            if(!player.getMainHandItem().isEmpty()) {
                //Slot Cheio
                //Mão Cheia
                if(player.getMainHandItem().is(itemHandler.getStackInSlot(0).getItem())){
                    player.sendSystemMessage(Component.literal(InfinityNexusMod.message + "O é igual ao do display!"));
                    return;
                }
                ItemStack stack = itemStack.copy();
                level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), itemHandler.getStackInSlot(0).copy()));
                this.itemHandler.setStackInSlot(0, stack);
                level.playSound(null, this.getBlockPos(), SoundEvents.ARMOR_STAND_FALL, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.isCreative()) {
                    //Não Criativo
                    player.getMainHandItem().shrink(stack.getCount());
                }
                player.sendSystemMessage(Component.literal(InfinityNexusMod.message + "Display atualizado!"));
                setBlockModel(this.itemHandler.getStackInSlot(0));
            }else{
                if(player.isShiftKeyDown()){
                    level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), itemHandler.getStackInSlot(0).copy()));
                    this.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                    player.sendSystemMessage(Component.literal(InfinityNexusMod.message + "Item removido do display!"));
                    setBlockModel(this.itemHandler.getStackInSlot(0));
                }
            }
        }
        this.setChanged();
    }

    private void setBlockModel(ItemStack itemStack) {

        this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(ItemDisplay.LIT, 1), 3);

        if(itemStack.getItem() instanceof BlockItem){
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(ItemDisplay.LIT, 0), 3);

        }else if(itemStack.getItem() instanceof SwordItem) {
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(ItemDisplay.LIT, 6), 3);

        }else if(itemStack.getItem() instanceof DiggerItem) {
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(ItemDisplay.LIT, 7), 3);

        }else{
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(ItemDisplay.LIT, 4), 3);
        }
    }

}