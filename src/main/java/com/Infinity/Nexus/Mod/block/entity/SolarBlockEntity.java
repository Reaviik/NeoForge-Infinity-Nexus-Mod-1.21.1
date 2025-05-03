package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.InfinityNexusCore;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.utils.EnergyUtils;
import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Core.utils.SoundUtils;
import com.Infinity.Nexus.Mod.block.custom.Solar;
import com.Infinity.Nexus.Mod.component.ItemStackComponent;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.custom.SolarUpgrade;
import com.Infinity.Nexus.Mod.screen.solar.SolarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolarBlockEntity extends BaseBlockEntity implements MenuProvider {

    private static final int COMPONENT_SLOT = 0;
    private static final int ENERGY_TRANSFER = 5832;
    private static final int TRANSFER = 64000;
    private static final int CAPACITY = 512000;
    private static int GEM = 0;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();


    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(CAPACITY, TRANSFER) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
            }

            @Override
            public boolean canReceive() {
                return super.canReceive();
            }
        };
    }
    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getItem() instanceof SolarUpgrade;
        }
    };

    protected final ContainerData data;
    public SolarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SOLAR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return GEM;
            }

            @Override
            public void set(int pIndex, int pValue) {
                 data.set(0, pValue);
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }


    public void drops() {
        ItemStack stack = new ItemStack(this.getBlockState().getBlock().asItem());
        stack = storageComponentAndEnergy(stack, ENERGY_STORAGE, itemHandler, COMPONENT_SLOT);
        ItemEntity battery = new ItemEntity(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), stack);
        this.level.addFreshEntity(battery);
    }

    @Override
    public Component getDisplayName() {
        return switch (getSolarLevel()) {
            case 0 -> Component.translatable("block.infinity_nexus_mod.solar");
            case 1 -> Component.translatable("item.infinity_nexus_mod.solar_pane");
            case 2 -> Component.translatable("item.infinity_nexus_mod.solar_pane_advanced");
            case 3 -> Component.translatable("item.infinity_nexus_mod.solar_pane_ultimate");
            case 4 -> Component.translatable("item.infinity_nexus_mod.solar_pane_quantum");
            case 5 -> Component.translatable("item.infinity_nexus_mod.solar_pane_photonic");
            default -> throw new IllegalStateException("Unexpected value: " + getSolarLevel());
        };
    }
    private int getSolarLevel() {
        int level = 0;
        ItemStack stack = itemHandler.getStackInSlot(COMPONENT_SLOT);
        if (stack.getItem() == ModItemsAdditions.SOLAR_PANE.get()) {
            level = 1;
        }else
        if (stack.getItem() == ModItemsAdditions.SOLAR_PANE_ADVANCED.get()) {
            level = 2;
        }else
        if (stack.getItem() == ModItemsAdditions.SOLAR_PANE_ULTIMATE.get()) {
            level = 3;
        }else
        if (stack.getItem() == ModItemsAdditions.SOLAR_PANE_QUANTUM.get()) {
            level = 4;
        }else
        if (stack.getItem() == ModItemsAdditions.SOLAR_PANE_PHOTONIC.get()) {
            level = 5;
        }
        return level;
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SolarMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
    }

    public boolean getTime(){
        return this.getLevel().getLevelData().getDayTime() % 24000 < 12000;
    }
    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }
    public IItemHandler getItemHandler(Direction direction) {
        return itemHandler;
    }
    public IEnergyStorage getEnergyStorage(Direction direction) {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("solar.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(pTag.getInt("solar.energy"));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }
        int level = getSolarLevel();
        pLevel.setBlock(pPos, pState.setValue(Solar.LIT, level), 3);


        if (isRedstonePowered(pPos)) {
            return;
        }

        distributeEnergy();
        if(!hasUpgrade()){
            return;
        }

        if (!hasRecipe()) {
            GEM = 0;
            return;
        }
        GEM = 1;
        if (!hasEnoughEnergySpace()) {
            return;
        }
        generateEnergy();
        setChanged(pLevel, pPos, pState);
    }

    private boolean hasUpgrade() {
        return getSolarLevel() > 0;
    }

    private void generateEnergy() {
        ENERGY_STORAGE.receiveEnergy(getGenerationRate(),false);
    }

    public  int getGenerationRate() {
        int level = getSolarLevel();
        int[] energy = {0, 8, 72, 648, 5832, 52488};
        return getTime() ? energy[level] : energy[level] / 8;
    }

    private void distributeEnergy() {
        if (level == null || level.isClientSide() || ENERGY_STORAGE.getEnergyStored() <= 0) {
            return;
        }
        for (Direction direction : Direction.values()) {
            if (ENERGY_STORAGE.getEnergyStored() <= 0) break;

            BlockPos neighborPos = getBlockPos().relative(direction);
            BlockEntity neighborBlockEntity = level.getBlockEntity(neighborPos);

            if (neighborBlockEntity == null || neighborBlockEntity instanceof SolarBlockEntity || neighborBlockEntity instanceof GeneratorBlockEntity) {
                continue;
            }

            try {
                IEnergyStorage neighborStorage = EnergyUtils.getBlockCapabilityEnergyHandler(level, neighborPos, direction);
                if (neighborStorage == null) continue;

                EnergyUtils.transferEnergy(ENERGY_STORAGE, neighborStorage, ENERGY_TRANSFER);
            } catch (Exception e) {
                InfinityNexusCore.LOGGER.error("Failed to transfer energy to neighbor at {}: {}", neighborPos, e.getMessage());
            }
        }
    }

    private boolean hasEnoughEnergySpace() {
        return ENERGY_STORAGE.getEnergyStored() < ENERGY_STORAGE.getMaxEnergyStored();
    }

    private boolean hasRecipe() {
        return this.getLevel().canSeeSky(this.getBlockPos().above());
    }

    public void setSolarLevel(ItemStack itemStack, Player player) {
        if (this.itemHandler.getStackInSlot(COMPONENT_SLOT).isEmpty()) {
            ItemStack stack = itemStack.copy();
            stack.setCount(1);
            this.itemHandler.setStackInSlot(COMPONENT_SLOT, stack);
            ItemStackHandlerUtils.setStackInSlot(COMPONENT_SLOT, stack, itemHandler);
            player.getMainHandItem().shrink(1);
            this.setChanged();
        }else{
            ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
            ItemStack stack = itemStack.copy();
            stack.setCount(1);
            ItemStackHandlerUtils.setStackInSlot(COMPONENT_SLOT, stack, itemHandler);
            ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), component);
            if (!player.isCreative()) {
                player.getMainHandItem().shrink(1);
                this.level.addFreshEntity(itemEntity);
            }
            this.setChanged();
        }
        SoundUtils.playSound(level, this.getBlockPos(), SoundSource.BLOCKS, SoundEvents.ARMOR_EQUIP_NETHERITE.value(),  1.0f, 1.0f);
    }

    public void setEnergyAndUpgrade(ItemStack stack) {
        ItemStackComponent itemStack = stack.has(ModDataComponents.ITEM_STACK) ? stack.get(ModDataComponents.ITEM_STACK) : null;
        if(itemStack != null){
            ItemStackHandlerUtils.setStackInSlot(COMPONENT_SLOT, itemStack.itemStack().copy(), itemHandler);
        }
        int energy = stack.has(ModDataComponents.ENERGY) ? stack.get(ModDataComponents.ENERGY) : 0;
        if(energy > 0){
            ENERGY_STORAGE.setEnergy(energy);
        }
    }
}