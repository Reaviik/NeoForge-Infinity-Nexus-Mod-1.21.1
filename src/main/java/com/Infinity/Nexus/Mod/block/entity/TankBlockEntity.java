package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.FluidUtils;
import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Tank;
import com.Infinity.Nexus.Mod.component.FluidStackComponent;
import com.Infinity.Nexus.Mod.component.ItemStackComponent;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.screen.tank.TankMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TankBlockEntity extends BaseBlockEntity implements MenuProvider {
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(3) {

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 2 -> ModUtils.isUpgrade(stack);
                default -> false;
            };
        }
    };

    private static final int FLUID_SLOT = 0;
    private static final int OUTPUT_FLUID_SLOT = 1;
    private static final int FLUID_CAPACITY = ConfigUtils.tank_capacity;
    private final FluidTank FLUID_STORAGE = createFluidStorage();


    private FluidTank createFluidStorage() {
        return new FluidTank(FLUID_CAPACITY) {
            @Override
            public void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }

            @NotNull
            @Override
            public FluidStack drain(int maxDrain, FluidAction action) {
                int maxAllowedDrain = Math.min(maxDrain, FLUID_STORAGE.getCapacity() / 2);
                if (endless == 1) {
                    maxAllowedDrain = Math.min(maxAllowedDrain, FLUID_STORAGE.getCapacity() / 2);
                }

                int drained = Math.min(fluid.getAmount(), maxAllowedDrain);
                FluidStack stack = new FluidStack(fluid.getFluid(), drained);

                if (action.execute() && drained > 0) {
                    fluid.shrink(drained);
                    onContentsChanged();

                    if (endless == 1) {
                        fluid.setAmount(FLUID_STORAGE.getCapacity());
                        onContentsChanged();
                    }
                }

                return stack;
            }
        };
    }


    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);
    private Lazy<IFluidHandler> lazyFluidHandler = Lazy.of(() -> FLUID_STORAGE);

    protected final ContainerData data;
    private int endless = 0;

    public TankBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TANK_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> TankBlockEntity.this.endless;
                    case 1 -> FLUID_STORAGE.getFluidAmount();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> TankBlockEntity.this.endless = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = Lazy.of(() -> itemHandler);
        lazyFluidHandler = Lazy.of(() -> FLUID_STORAGE);
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        ItemStack stack = new ItemStack(this.getBlockState().getBlock().asItem());
        stack = storageUpgradeAndFluid(stack, FLUID_STORAGE, itemHandler, 2);
        ItemEntity itemTank = new ItemEntity(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), stack);
        this.level.addFreshEntity(itemTank);

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    protected static ItemStack storageUpgradeAndFluid(ItemStack itemStack, FluidTank fluidTank, RestrictedItemStackHandler itemHandler, int upgradeSlot) {
        ItemStack stack = itemHandler.getStackInSlot(upgradeSlot);
        if (!stack.isEmpty()) {
            itemStack.set(ModDataComponents.ITEM_STACK, new ItemStackComponent(stack));
            ItemStackHandlerUtils.extractItem(upgradeSlot, 1, false, itemHandler);
        }

        if(fluidTank.getFluidAmount() > 0) {
            itemStack.set(ModDataComponents.TANK_FLUID, new FluidStackComponent(fluidTank.getFluid().copy()));
        }
        return itemStack;
    }

    @Override
    public Component getDisplayName() {
        String endless = this.endless == 1 ? Component.translatable("tooltip.infinity_nexus_mod.tank_endless").getString() : "";
        return Component.translatable("block.infinity_nexus_mod.tank").append(": ").append(FLUID_STORAGE.getFluid().getHoverName()).append(" ").append(endless);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new TankMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }
    public static long getFluidCapacity() {
        return FLUID_CAPACITY;
    }


    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("tank.progress", endless);
        pTag = FLUID_STORAGE.writeToNBT(registries, pTag);
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        endless = pTag.getInt("tank.progress");
        FLUID_STORAGE.readFromNBT(registries, pTag);
    }

    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    public IFluidHandler getFluidHandler(Direction direction) {
        return FLUID_STORAGE;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        try {
            if (pLevel.isClientSide) {
                return;
            }
            manageFluids();
            ejectFluid();
            verifyEndless(pState, pLevel, pPos);
            setChanged(pLevel, pPos, pState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void manageFluids() {
        ItemStack itemStack = itemHandler.getStackInSlot(FLUID_SLOT);
        if(!FluidUtils.isFluidHandlerItem(itemStack)) return;
        FluidStack fluidStack = FluidUtils.getFluidInItem(itemStack);
        if (fluidStack.isEmpty()) {
            FluidUtils.transferFromTankToItem(FLUID_STORAGE, itemHandler,OUTPUT_FLUID_SLOT, FLUID_SLOT);
            return;
        }
        FluidUtils.transferFromItemToTank(FLUID_STORAGE, itemHandler, OUTPUT_FLUID_SLOT, FLUID_SLOT);
    }

    private void ejectFluid() {
        if(!itemHandler.getStackInSlot(2).is(ModItems.PUSHER_UPGRADE.get())) {
            return;
        }
        if (FLUID_STORAGE.getFluid().isEmpty()) {
            return;
        }

        IFluidHandler fluidTank = FluidUtils.getBlockFluidHandler(worldPosition.below(), level);
        if(fluidTank != null) {
            FluidUtils.transferFromTankToTank(FLUID_STORAGE, fluidTank, 10000);
        }

    }


    private void verifyEndless(BlockState pState, Level pLevel, BlockPos pPos) {
        if (endless == 1 && !ConfigUtils.tank_can_endless) {
            endless = 0;
            if (pState.getValue(Tank.LIT) != 0) {
                pLevel.setBlock(pPos, pState.setValue(Tank.LIT, 0), 3);
            }
            return;
        }

        if (endless != 1 && ConfigUtils.tank_can_endless) {
            if (FLUID_STORAGE.getFluid().getAmount() >= FLUID_STORAGE.getCapacity()) {
                String fluidId = FLUID_STORAGE.getFluid().getFluid().builtInRegistryHolder().key().location().toString();
                boolean isBlacklisted = ConfigUtils.blacklist_tank_fluids.contains(fluidId);
                boolean shouldBeEndless = (ConfigUtils.blacklist_tank_fluids_toggle && isBlacklisted) ||
                        (!ConfigUtils.blacklist_tank_fluids_toggle && !isBlacklisted);

                if (shouldBeEndless) {
                    endless = 1;
                    FluidUtils.fillFluidToTank(FLUID_STORAGE, new FluidStack(FLUID_STORAGE.getFluid().getFluid(), FLUID_STORAGE.getCapacity()));
                    if (pState.getValue(Tank.LIT) != 1) {
                        pLevel.setBlock(pPos, pState.setValue(Tank.LIT, 1), 3);
                    }
                }
            }
        }

        if (endless == 1) {
            if (FLUID_STORAGE.getFluid().getAmount() < FLUID_STORAGE.getCapacity()) {
                FluidUtils.fillFluidToTank(FLUID_STORAGE, new FluidStack(FLUID_STORAGE.getFluid().getFluid(), FLUID_STORAGE.getCapacity()));
            }
            if (pState.getValue(Tank.LIT) != 1) {
                pLevel.setBlock(pPos, pState.setValue(Tank.LIT, 1), 3);
            }
        }
    }

    public static int getInputSlot() {
        return FLUID_SLOT;
    }

    public static int getOutputSlot() {
        return OUTPUT_FLUID_SLOT;
    }

    public IFluidTank getFluidTank() {
        return FLUID_STORAGE;
    }

    public void fillBucket(ItemStack mainHandItem, Player pPlayer, Level pLevel) {
        if(mainHandItem.is(Items.BUCKET.asItem())) {
            if (FLUID_STORAGE.getFluidAmount() >= 1000) {
                mainHandItem.shrink(1);
                pPlayer.level().playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.5F, 0.2F);
                ItemEntity itemEntity = new ItemEntity(pLevel, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), new ItemStack(FLUID_STORAGE.getFluid().getFluid().getBucket()));
                pLevel.addFreshEntity(itemEntity);
                FLUID_STORAGE.drain(1000, IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }
    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        if(itemStack.is(ModItems.PUSHER_UPGRADE)) {
            SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, new int[]{2}, this.itemHandler);
        }
    }

    public void setFluidAndUpgrade(ItemStack stack) {
        FluidStackComponent fluidStack = stack.has(ModDataComponents.TANK_FLUID) ? stack.get(ModDataComponents.TANK_FLUID) : null;
        ItemStackComponent itemStack = stack.has(ModDataComponents.ITEM_STACK) ? stack.get(ModDataComponents.ITEM_STACK) : null;
        if(fluidStack != null && !fluidStack.fluidStack().isEmpty()) {
            FluidUtils.fillFluidToTank(FLUID_STORAGE, fluidStack.fluidStack());
        }
        if(itemStack != null){
            ItemStackHandlerUtils.setStackInSlot(2, itemStack.itemStack().copy(), itemHandler);
        }
    }
}