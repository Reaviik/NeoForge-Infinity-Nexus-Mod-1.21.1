package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.SendParticlesPath;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.custom.TranslocatorItem;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TranslocatorBlockEntityBase extends BlockEntity {
    protected static final int INPUT_SLOT = 0;
    protected static final int UPGRADE_SLOT = 1;

    protected int progress = 0;
    protected int maxProgress = ConfigUtils.translocator_delay;
    protected int mode = 0;
    protected int step = 0;
    protected String[] filter;
    protected int filterIndex;

    protected final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> false;
                case 1 -> stack.getItem() == ModItems.PUSHER_UPGRADE.get();
                default -> super.isItemValid(slot, stack);
            };
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if(slot == UPGRADE_SLOT){
                return ItemStack.EMPTY;
            }
            return super.extractItem(slot, amount, simulate);
        }
    };

    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);
    protected final ContainerData data;

    public TranslocatorBlockEntityBase(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> TranslocatorBlockEntityBase.this.progress;
                    case 1 -> TranslocatorBlockEntityBase.this.maxProgress;
                    case 2 -> TranslocatorBlockEntityBase.this.mode;
                    case 3 -> TranslocatorBlockEntityBase.this.step;
                    case 4 -> TranslocatorBlockEntityBase.this.filterIndex;
                    default -> 0;
                };
            }
            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> TranslocatorBlockEntityBase.this.progress = pValue;
                    case 1 -> TranslocatorBlockEntityBase.this.maxProgress = pValue;
                    case 2 -> TranslocatorBlockEntityBase.this.mode = pValue;
                    case 3 -> TranslocatorBlockEntityBase.this.step = pValue;
                    case 4 -> TranslocatorBlockEntityBase.this.filterIndex = pValue;
                }
            }

            @Override
            public int getCount() {
                return 5;
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

    public static int getInputSlot() {
        return INPUT_SLOT;
    }
    public static int getUpgradeSlot() {
        return UPGRADE_SLOT;
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
        pTag.putInt("translocator.progress", progress);
        pTag.putInt("translocator.max_progress", maxProgress);
        pTag.putInt("translocator.mode", mode);
        pTag.putInt("translocator.step", step);
        pTag.putString("translocator.filter", filter == null ? "" : String.join(";", filter));
        pTag.putInt("translocator.filterIndex", filterIndex);

        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("translocator.progress");
        maxProgress = pTag.getInt("translocator.max_progress");
        mode = pTag.getInt("translocator.mode");
        step = pTag.getInt("translocator.step");
        filter = pTag.getString("translocator.filter").isEmpty() ? new String[0] : pTag.getString("translocator.filter").split(";");
        filterIndex = pTag.getInt("translocator.filterIndex");
    }

    protected void tick(Level pLevel, BlockPos pPos, BlockState pState) {
    }

    protected boolean isFiltered(ItemStack stack) {
        if(filter == null || filter.length == 0) {
            return true;
        }
        String itemName = stack.getItem().builtInRegistryHolder().key().location().toString();
        for (String s : filter) {
            if (itemName.equals(s.trim())) {
                return true;
            }
        }
        return false;
    }


    protected void upgradeStep() {
        ItemStack upgradeStack = this.itemHandler.getStackInSlot(UPGRADE_SLOT);
        if (!upgradeStack.isEmpty() && upgradeStack.has(ModDataComponents.TRANSLOCATOR_COORDS.get())) {
            List<BlockPos> positions = upgradeStack.get(ModDataComponents.TRANSLOCATOR_COORDS.get());
            if (!positions.isEmpty()) {
                step = (step + 1) % positions.size();
            } else {
                step = 0;
            }
        } else {
            step = 0;
        }
    }

    protected boolean canLink(BlockPos pos) {
        if(this.getLevel().isLoaded(pos)){
            return (int) Math.sqrt(this.getBlockPos().distSqr(pos)) < ConfigUtils.translocator_range_limit;
        }
        return false;
    }
    protected void resetProgress() {
        progress = 0;
        maxProgress = ConfigUtils.translocator_delay;
    }

    protected void increaseProgress() {
        if (progress < maxProgress) {
            progress++;
        }
    }

    protected boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    protected BlockPos getDestination() {
        BlockPos target = getCurrentTarget();
        if (target != null && canLink(target)) {
            return target;
        }
        return getBlockPos();
    }

    protected BlockPos getCurrentTarget() {
        ItemStack upgradeStack = this.itemHandler.getStackInSlot(UPGRADE_SLOT);
        if (!upgradeStack.isEmpty() && upgradeStack.has(ModDataComponents.TRANSLOCATOR_COORDS.get())) {
            List<BlockPos> positions = upgradeStack.get(ModDataComponents.TRANSLOCATOR_COORDS.get());
            if (!positions.isEmpty() && step < positions.size()) {
                return positions.get(step);
            }
        }
        return null;
    }

    protected BlockEntity getInventoryPos(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction direction = pState.getValue(TranslocatorItem.FACING);
        return pLevel.getBlockEntity(pPos.relative(direction.getOpposite()));
    }

    protected boolean canSend() {
        return getCurrentTarget() != null;
    }


    //TODO OVERRIDE
    protected boolean isInputSlotEmpty() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT).isEmpty();
    }

    protected void updateLit(Level level, BlockState state) {
        int lit = 6;
        int step = maxProgress / 5;

        if (mode == 1) {
            if (canSend()) {
                lit = (progress >= 4 * step) ? 5 : (progress >= 3 * step) ? 4 : (progress >= 2 * step) ? 3 : (progress >= step) ? 2 : 1;
            }
        } else if (mode == 0) {
            if (!isInputSlotEmpty()) {
                lit = (progress <= step) ? 5 : (progress <= 2 * step) ? 4 : (progress <= 3 * step) ? 3 : (progress <= 4 * step) ? 2 : 1;
            }
        }

        setLit(level, state, lit);
    }


    protected void setLit(Level level, BlockState state, int lit) {
        if(state.getValue(TranslocatorItem.LIT) != lit) {
            level.setBlock(this.getBlockPos(), this.getBlockState().setValue(TranslocatorItem.LIT, lit), 3);
        }
    }

    public void toggleMode(ItemStack stack, Player player, boolean shift, BlockPos pos) {
        if (stack.is(ModItems.PUSHER_UPGRADE.get())) {
            if (!this.itemHandler.getStackInSlot(UPGRADE_SLOT).isEmpty()) {
                ItemEntity entity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), this.itemHandler.getStackInSlot(UPGRADE_SLOT));
                level.addFreshEntity(entity);
                this.itemHandler.setStackInSlot(UPGRADE_SLOT, ItemStack.EMPTY);
                this.mode = 0;
                player.sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_receive")));
            } else {
                this.itemHandler.setStackInSlot(UPGRADE_SLOT, stack);
                this.mode = 1;
                player.getMainHandItem().shrink(1);
                player.sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_send")));
            }
        }else{
            addItemFiler(stack, player, shift);
        }
    }

    //TODO OVERRIDE Null
    protected void addItemFiler(ItemStack stack, Player player, boolean shift) {
        if (!stack.isEmpty() && stack.getItem() != ModItemsAdditions.TRANSLOCATOR_LINK.get()) {
            String itemName = stack.getItem().builtInRegistryHolder().key().location().toString();
            if (filter != null && Arrays.asList(filter).contains(itemName)) return;
            if (filter == null) {
                filter = new String[]{itemName};
            } else {
                String[] newFilter = Arrays.copyOf(filter, filter.length + 1);
                newFilter[newFilter.length - 1] = itemName;
                filter = newFilter;
            }
        } else if (shift) {
            if (filter == null || filter.length == 0) return;
            String item = filter[filter.length - 1];
            ItemStack displayStack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(item)));
            filter = Arrays.copyOf(filter, filter.length - 1);
            setChanged();
            player.sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_filter")).append("\n ☼ §c"+displayStack.getHoverName().getString()));
        }

        // Criando a mensagem principal
        MutableComponent message = Component.literal(InfinityNexusMod.message)
                .append(Component.translatable("tooltip.infinity_nexus.translocator_filter").append(" ").withStyle(ChatFormatting.GOLD));

        if (filter == null || filter.length == 0) {
            message.append(Component.translatable("tooltip.infinity_nexus_mod.tank_empty"));
        } else {
            for (String item : filter) {
                ItemStack displayStack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(item)));
                if (!displayStack.isEmpty()) {
                    MutableComponent itemComponent = Component.empty()
                            .append("\n")
                            .append(" ☼ ")
                            .append(displayStack.getHoverName()
                                    .copy()
                                    .withStyle(style -> style.withHoverEvent(new HoverEvent(
                                            HoverEvent.Action.SHOW_ITEM,
                                            new HoverEvent.ItemStackInfo(displayStack)
                                    ))).withStyle(ChatFormatting.AQUA)
                            );
                    message.append(itemComponent);
                }
            }
        }

        player.sendSystemMessage(message);
    }

    public void setCords(List<BlockPos> positions, Player player) {
        ItemStack upgradeStack = this.itemHandler.getStackInSlot(UPGRADE_SLOT);
        if (upgradeStack.is(ModItems.PUSHER_UPGRADE.get())) {
            upgradeStack.set(ModDataComponents.TRANSLOCATOR_COORDS.get(), ImmutableList.copyOf(positions));

            for (BlockPos pos : positions) {
                SendParticlesPath.makePath((ServerLevel) this.getLevel(), ParticleTypes.SCRAPE,
                        worldPosition, pos, 0.5d, 0.5d, 0.5d);
            }

            player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                    .append(Component.translatable("tooltip.infinity_nexus.translocator_link")));
            this.mode = 1;
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(TranslocatorItem.LIT, 1), 3);
        } else {
            player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                    .append(Component.translatable("tooltip.infinity_nexus.translocator_link_fail")));
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

    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
}