package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.GetNewAABB;
import com.Infinity.Nexus.Core.utils.GetResourceLocation;
import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Placer;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.screen.placer.PlacerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlacerBlockEntity extends BlockEntity implements MenuProvider {
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 1 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }

    };
    private static final int INPUT_SLOT = 0;
    private static final int COMPONENT_SLOT = 1;

    private int progress = 0;
    private int maxProgress = 20;
    private int blocked = 0;

    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);

    protected final ContainerData data;
    public PlacerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PLACER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> PlacerBlockEntity.this.progress;
                    case 1 -> PlacerBlockEntity.this.maxProgress;
                    case 2 -> PlacerBlockEntity.this.blocked;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> PlacerBlockEntity.this.progress = pValue;
                    case 1 -> PlacerBlockEntity.this.maxProgress = pValue;
                    case 2 -> PlacerBlockEntity.this.blocked = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
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
    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.placer");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PlacerMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("placer.progress", progress);
        pTag.putInt("placer.max_progress", maxProgress);
        pTag.putInt("placer.blocked", blocked);

        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("placer.progress");
        maxProgress = pTag.getInt("placer.max_progress");
        blocked = pTag.getInt("placer.blocked");
    }


    public ItemStack getRenderStack(int slot){
        return itemHandler.getStackInSlot(slot).isEmpty() ? ItemStack.EMPTY : itemHandler.getStackInSlot(slot).getItem().getDefaultInstance();
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        if(pState.getValue(Placer.LIT) != machineLevel){
            pLevel.setBlock(pPos, pState.setValue(Placer.LIT, machineLevel), 3);
        }

        if (isRedstonePowered(pPos)) {
            return;
        }
        increaseProgress();
        if (!hasProgressFinished()) {
            return;
        }
        resetProgress();
        if (!hasRecipe(pPos)) {
            data.set(2, 1);
            return;
        }
        data.set(2, 0);
        craft(pLevel, pPos, pState , machineLevel);
        setChanged(pLevel, pPos, pState);
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 20;
    }

    private void increaseProgress() {
        if (progress < maxProgress) {
            progress++;
        }
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void craft(Level pLevel, BlockPos pPos, BlockState pState, int machineLevel) {
        if(pLevel instanceof ServerLevel level) {
            Direction direction = pState.getValue(Placer.FACING);
            BlockPos placePos = getPlacePos(pPos, direction);
            BlockState toPlace = level.getBlockState(placePos);
            if(!hasEntity(placePos)) {
                ItemStack stack = itemHandler.getStackInSlot(INPUT_SLOT).copy();
                try {
                    IFFakePlayer player = new IFFakePlayer(level);
                    if(!player.placeBlock(this.level, placePos, stack, direction)) {
                        if(level.getBlockState(placePos) != toPlace){
                            ItemStackHandlerUtils.extractItem(INPUT_SLOT, 1, false, itemHandler);
                        }
                        return;
                    }
                    ItemStackHandlerUtils.extractItem(INPUT_SLOT, 1, false, itemHandler);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private boolean hasEntity(BlockPos pPos) {
        return !level.getEntitiesOfClass(Player.class, GetNewAABB.getAABB(pPos)).isEmpty();

    }
    private boolean canPlace(ItemStack stack) {
        return !ConfigUtils.list_of_non_placeable_blocks.stream()
                .map(block -> BuiltInRegistries.ITEM.get(GetResourceLocation.parse(block)))
                .anyMatch(p -> p == stack.getItem());
    }

    private boolean isFree(BlockPos pPos) {
        return level.getBlockState(pPos) == Blocks.AIR.defaultBlockState();
    }

    private BlockPos getPlacePos(BlockPos pPos, Direction pDirection) {
        return switch (pDirection) {
            case DOWN -> pPos.below();
            case UP -> pPos.above();
            case NORTH -> pPos.north();
            case SOUTH -> pPos.south();
            case WEST -> pPos.west();
            case EAST -> pPos.east();
        };
    }

    private boolean hasRecipe(BlockPos pPos) {
        ItemStack stack = itemHandler.getStackInSlot(INPUT_SLOT);
        return itemHandler.getStackInSlot(COMPONENT_SLOT).getCount() > 0
                && stack.getItem() instanceof BlockItem
                && isFree(getPlacePos(pPos, this.getBlockState().getValue(Placer.FACING)))
                && stack.is(stack.getItem().getDefaultInstance().getItem())
                && canPlace(stack);
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }
    private int getMachineLevel(){
        try {
            return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
        }catch (Exception e) {
            return 0;
        }
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

    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }
}