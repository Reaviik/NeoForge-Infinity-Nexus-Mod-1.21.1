package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.component.CoreDataComponents;
import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import com.Infinity.Nexus.Core.itemStackHandler.RestrictedItemStackHandler;
import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.*;
import com.Infinity.Nexus.Mod.block.custom.MobCrusher;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.screen.mobcrusher.MobCrusherMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MobCrusherBlockEntity extends BlockEntity implements MenuProvider {
    private static final int[] OUTPUT_SLOT = {0,1,2,3,4,5,6,7,8};
    private static final int[] UPGRADE_SLOTS = {9,10,11,12};
    private static final int COMPONENT_SLOT = 13;
    private static final int SWORD_SLOT = 14;
    private static final int LINK_SLOT = 15;
    private static final int FUEL_SLOT = 16;

    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 120;
    private int hasRedstoneSignal = 0;
    private int stillCrafting = 0;
    private int hasSlotFree = 0;
    private int hasComponent = 0;
    private int hasEnoughEnergy = 0;
    private int hasRecipe = 0;
    private int linkx = 0;
    private int linky = 0;
    private int linkz = 0;
    private int linkFace = 0;

    private static final int ENERGY_CAPACITY = 60000;
    private static final int ENERGY_TRANSFER = 640;
    private static final int ENERGY_REQ = 32;
    private final FluidTank FLUID_STORAGE = createFluidStorage();
    private static final int FluidStorageCapacity = 10000;


    private final RestrictedItemStackHandler itemHandler = new RestrictedItemStackHandler(17) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1,2,3,4,5,6,7,8 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 9,10,11,12 -> ModUtils.isUpgrade(stack);
                case 13 -> ModUtils.isComponent(stack);
                case 14 -> stack.getItem() instanceof SwordItem;
                case 15 -> stack.is(ModItems.LINKING_TOOL.get().asItem());
                case 16 -> stack.getBurnTime(null) > 0;
                default -> super.isItemValid(slot, stack);
            };
        }
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate, boolean fromAutomation) {
            return slot <= 8 ? super.extractItem(slot, amount, simulate, false) : super.extractItem(slot, amount, simulate, fromAutomation);
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(ENERGY_CAPACITY, ENERGY_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
        }
    };

    private FluidTank createFluidStorage() {
        return new FluidTank(FluidStorageCapacity) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if (!getLevel().isClientSide) {
                    getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }

    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);
    private Lazy<IEnergyStorage> lazyEnergyStorage = Lazy.of(() -> ENERGY_STORAGE);
    private Lazy<IFluidHandler> lazyFluidHandler = Lazy.of(() -> FLUID_STORAGE);

    public MobCrusherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOBCRUSHER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MobCrusherBlockEntity.this.progress;
                    case 1 -> MobCrusherBlockEntity.this.maxProgress;
                    case 2 -> MobCrusherBlockEntity.this.hasRedstoneSignal;
                    case 3 -> MobCrusherBlockEntity.this.stillCrafting;
                    case 4 -> MobCrusherBlockEntity.this.hasSlotFree;
                    case 5 -> MobCrusherBlockEntity.this.hasComponent;
                    case 6 -> MobCrusherBlockEntity.this.hasEnoughEnergy;
                    case 7 -> MobCrusherBlockEntity.this.hasRecipe;
                    case 8 -> MobCrusherBlockEntity.this.linkx;
                    case 9 -> MobCrusherBlockEntity.this.linky;
                    case 10 -> MobCrusherBlockEntity.this.linkz;
                    case 11 -> MobCrusherBlockEntity.this.linkFace;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MobCrusherBlockEntity.this.progress = pValue;
                    case 1 -> MobCrusherBlockEntity.this.maxProgress = pValue;
                    case 2 -> MobCrusherBlockEntity.this.hasRedstoneSignal = pValue;
                    case 3 -> MobCrusherBlockEntity.this.stillCrafting = pValue;
                    case 4 -> MobCrusherBlockEntity.this.hasSlotFree = pValue;
                    case 5 -> MobCrusherBlockEntity.this.hasComponent = pValue;
                    case 6 -> MobCrusherBlockEntity.this.hasEnoughEnergy = pValue;
                    case 7 -> MobCrusherBlockEntity.this.hasRecipe = pValue;
                    case 8 -> MobCrusherBlockEntity.this.linkx = pValue;
                    case 9 -> MobCrusherBlockEntity.this.linky = pValue;
                    case 10 -> MobCrusherBlockEntity.this.linkz = pValue;
                    case 11 -> MobCrusherBlockEntity.this.linkFace = pValue;
                }
            }

            @Override
            public int getCount() {
                return 12;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = Lazy.of(() -> itemHandler);
        lazyEnergyStorage = Lazy.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = Lazy.of(() -> FLUID_STORAGE);
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("mobCrusher.progress", progress);
        pTag.putInt("mobCrusher.energy", ENERGY_STORAGE.getEnergyStored());
        pTag = FLUID_STORAGE.writeToNBT(registries, pTag);
        pTag.putInt("mobCrusher.hasRedstoneSignal", getHasRedstoneSignal());
        pTag.putInt("mobCrusher.stillCrafting", getStillCrafting());
        pTag.putInt("mobCrusher.hasSlotFree", getHasSlotFree());
        pTag.putInt("mobCrusher.hasComponent", getHasComponent());
        pTag.putInt("mobCrusher.hasEnoughEnergy", getHasEnoughEnergy());
        pTag.putInt("mobCrusher.hasRecipe", getHasRecipe());
        pTag.putInt("miner.linkx", data.get(8));
        pTag.putInt("miner.linky", data.get(9));
        pTag.putInt("miner.linkz", data.get(10));
        pTag.putInt("miner.linkFace", data.get(11));
        pTag.putBoolean("mobCrusher.showArea", showArea);
        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("mobCrusher.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("mobCrusher.energy"));
        FLUID_STORAGE.readFromNBT(registries, pTag);
        hasRedstoneSignal = pTag.getInt("mobCrusher.hasRedstoneSignal");
        stillCrafting = pTag.getInt("mobCrusher.stillCrafting");
        hasSlotFree = pTag.getInt("mobCrusher.hasSlotFree");
        hasComponent = pTag.getInt("mobCrusher.hasComponent");
        hasEnoughEnergy = pTag.getInt("mobCrusher.hasEnoughEnergy");
        hasRecipe = pTag.getInt("mobCrusher.hasRecipe");
        linkx = pTag.getInt("miner.linkx");
        linky = pTag.getInt("miner.linky");
        linkz = pTag.getInt("miner.linkz");
        linkFace = pTag.getInt("miner.linkFace");
        showArea = pTag.getBoolean("mobCrusher.showArea");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.mob_crusher").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, Player pPlayer) {
        return new MobCrusherMenu(pContainerId, pPlayerInventory, this, this.data, itemHandler);
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
    public IFluidHandler getFluidHandler(Direction direction) {
        return FLUID_STORAGE;
    }
    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public static long getFluidCapacity() {
        return FluidStorageCapacity;
    }
    public FluidStack getFluid() {
        return this.FLUID_STORAGE.getFluid();
    }
    public int getHasRedstoneSignal() { return data.get(2); }
    public int getStillCrafting() { return data.get(3); }
    public int getHasSlotFree() { return data.get(4); }
    public int getHasComponent() { return data.get(5); }
    public int getHasEnoughEnergy() { return data.get(6); }
    public int getHasRecipe() { return data.get(7); }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) {
            return;
        }

        renderAreaPreview(level, pos);

        int machineLevel = Math.max(getMachineLevel() - 1, 0);
        state = state.setValue(MobCrusher.LIT, machineLevel);

        if (isRedstonePowered(pos)) {
            this.data.set(2, 1);
            return;
        }
        this.data.set(2, 0);

        setMaxProgress();
        if (!hasEnoughEnergy()) {
            verifySolidFuel();
            this.data.set(6, 0);
            return;
        }
        this.data.set(6, 1);

        if(hasMobInside(machineLevel, pos, level)) {
            this.data.set(7, 1);
            increaseCraftingProgress();

            if (hasProgressFinished()) {
                this.data.set(3, 1);
                level.setBlock(pos, state.setValue(MobCrusher.LIT, machineLevel+9), 3);
                verifyMobs(level, pos, machineLevel);
                extractEnergy();
                setChanged(level, pos, state);
                resetProgress();
            }
            this.data.set(3, 0);
        } else {
            this.data.set(7, 0);
        }
    }

    private boolean hasMobInside(int machinelevel, BlockPos pPos, Level pLevel) {
        machinelevel = machinelevel + 1;
        List<Mob> mobs = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class, GetNewAABB.getAABB(pPos.offset( machinelevel * -1, 0,  machinelevel * -1), pPos.offset(+machinelevel,3,+machinelevel))));
        return !mobs.isEmpty();
    }

    private void extractEnergy() {
        EnergyUtils.extractEnergyFromRecipe(
                ENERGY_STORAGE,
                500,
                getMachineLevel() + 1,
                maxProgress,
                itemHandler,
                UPGRADE_SLOTS
        );
    }
    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private void resetProgress() {
        progress = 0;
    }

    public int getMachineLevel(){
        if(ModUtils.isComponent(this.itemHandler.getStackInSlot(COMPONENT_SLOT))){
            this.data.set(5, ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT)));
        }else{
            this.data.set(5, 0);
        }
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private void setMaxProgress() {
        maxProgress = 20;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        if (level != null && level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    private  void execute(Mob mob, BlockPos pPos, int machineLevel) {

        //ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        //ModUtils.useComponent(component, level, this.getBlockPos());
        IFFakePlayer player = GetFakePlayer.get((ServerLevel) level);
        player.setItemInHand(InteractionHand.MAIN_HAND, this.itemHandler.getStackInSlot(SWORD_SLOT));
        ServerPlayer randomPlayer = ((ServerLevel) this.level).getRandomPlayer();
        DamageSource source = player.damageSources().playerAttack((randomPlayer != null) && machineLevel >= 7 ? randomPlayer : player);
        //TODO REVISE
        //LootTable table1 = Objects.requireNonNull(this.level.getServer()).getLootData().getLootTable(mob.getLootTable());
        LootTable table = Objects.requireNonNull(this.level.getServer()).reloadableRegistries().getLootTable(mob.getLootTable());
        LootParams.Builder context = new LootParams.Builder((ServerLevel) this.level)
                .withParameter(LootContextParams.THIS_ENTITY, mob)
                .withParameter(LootContextParams.DAMAGE_SOURCE, source)
                .withParameter(LootContextParams.ORIGIN, new Vec3(pPos.getX(), pPos.getY(), pPos.getZ()))
                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player);
        table.getRandomItems(context.create(LootContextParamSets.ENTITY)).forEach(stack ->{
            insertItemOnInventory(stack);
            for(int loot = 0; loot < machineLevel; loot++) {
                int rand = RandomSource.create().nextInt(10);
                if (rand == 0) {
                    insertItemOnInventory(stack);
                }
            }
        });

        List<ItemEntity> extra = new ArrayList<>();
        if (mob.captureDrops() == null) mob.captureDrops(new ArrayList<>());
        if (mob.captureDrops() != null) {
            extra.addAll(mob.captureDrops());
        }
        //TODO FIX
        //ForgeHooks.onLivingDrops(mob, source, extra, 3, true);
        player.attack(mob);
        extra.forEach(itemEntity -> {
            insertItemOnInventory(itemEntity.getItem());
            itemEntity.remove(Entity.RemovalReason.KILLED);
        });
        mob.setHealth(0);
        SendParticlesPath.makePath((ServerLevel) this.getLevel(), ParticleTypes.ELECTRIC_SPARK, worldPosition.above(), mob.getOnPos().above(2), 0.5D, 0.2D, 0.5D);
        insertExpense(mob.getExperienceReward((ServerLevel) level, player));
    }


    private void insertExpense(int experienceReward) {
        FluidStack fluidStack = new FluidStack(ModFluids.EXPERIENCE_SOURCE.get(), experienceReward);
        this.FLUID_STORAGE.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
    }
    public void verifyMobs(Level pLevel, BlockPos pPos, int machinelevel) {
        try {
            machinelevel = machinelevel + 1;
            List<Mob> entities = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class, GetNewAABB.getAABB(pPos.offset( machinelevel * -1, 0,  machinelevel * -1), pPos.offset(+machinelevel,3,+machinelevel))));
            this.data.set(4,0);
            if (!entities.isEmpty()) {
                boolean hasFreeSlots = hasFreeSlots();
                if(!hasFreeSlots && entities.size() > 30) {
                    if(hasProgressFinished()){
                        insertItemOnInventory(ItemStack.EMPTY);
                    }
                    entities.forEach(Entity::discard);
                    notifyOwner();
                }else if(hasFreeSlots){
                    this.data.set(4,1);
                    for (Mob entity : entities) {
                        if (entity != null) {
                            if (!(entity.hasCustomName() || EntityType.getKey(entity.getType()).getPath().equalsIgnoreCase("maid")) && entity.isAlive()) {
                                execute(entity, pPos, machinelevel);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("§f[INM§f]§4: Failed to kill mobs in: " + pPos);
            e.printStackTrace();
        }
    }
    private void insertItemOnInventory(ItemStack itemStack) {
        try {
            if (itemHandler.getStackInSlot(LINK_SLOT).is(ModItems.LINKING_TOOL.get())) {
                ItemStack linkingTool = itemHandler.getStackInSlot(LINK_SLOT).copy();
                AtomicBoolean success = new AtomicBoolean(false);
                String name = linkingTool.getDisplayName().getString();
                this.data.set(8, 0);
                this.data.set(9, 0);
                this.data.set(10, 0);
                if (linkingTool.has(CoreDataComponents.LINKINGTOOL_COORDS)) {
                    BlockPos linkingToolPos = linkingTool.get(CoreDataComponents.LINKINGTOOL_COORDS);
                    this.data.set(8, linkingToolPos.getX());
                    this.data.set(9, linkingToolPos.getY());
                    this.data.set(10, linkingToolPos.getZ());
                    int xl = this.data.get(8);
                    int yl = this.data.get(9);
                    int zl = this.data.get(10);


                    BlockEntity blockEntity = this.level.getBlockEntity(new BlockPos(xl, yl, zl));
                    BlockPos targetPos = new BlockPos(xl, yl, zl);
                    if (blockEntity.getBlockPos().equals(this.getBlockPos())) {
                        level.addFreshEntity(new ItemEntity(level, xl, yl + 1, zl, itemHandler.getStackInSlot(LINK_SLOT).copy()));
                        itemHandler.extractItem(LINK_SLOT, 1, false);
                    }
                    if(!itemHandler.getStackInSlot(OUTPUT_SLOT[7]).isEmpty()) {
                        if (blockEntity != null && canLink(blockEntity)) {
                            //TODO CAPABILITIES ++
                            IItemHandler iItemHandler = ItemStackHandlerUtils.getBlockCapabilityItemHandler(level, targetPos, null);
                                for (int slot = 0; slot < iItemHandler.getSlots(); slot++) {
                                    if (ModUtils.canPlaceItemInContainer(itemStack.copy(), slot, iItemHandler) && iItemHandler.isItemValid(slot, itemStack.copy())) {
                                        iItemHandler.insertItem(slot, itemStack.copy(), false);
                                        SendParticlesPath.makePath((ServerLevel) this.getLevel(),ParticleTypes.SCRAPE, worldPosition.above(), targetPos, 0.5D, 0.2D, 0.5D);
                                        success.set(true);
                                        break;
                                    }
                                }

                                for (int slot = 0; slot < iItemHandler.getSlots(); slot++) {
                                    for (int outputSlot : OUTPUT_SLOT) {
                                        if (!itemHandler.getStackInSlot(outputSlot).isEmpty() && iItemHandler.isItemValid(slot, itemStack.copy()) && ModUtils.canPlaceItemInContainer(itemHandler.getStackInSlot(outputSlot).copy(), slot, iItemHandler)) {
                                            iItemHandler.insertItem(slot, itemHandler.getStackInSlot(outputSlot).copy(), false);
                                            itemHandler.extractItem(outputSlot, itemHandler.getStackInSlot(outputSlot).getCount(), false);
                                            success.set(true);
                                            break;
                                        }
                                    }
                                }
                        }else{
                            ModUtils.ejectItemsWhePusher(worldPosition.above(),UPGRADE_SLOTS, OUTPUT_SLOT, itemHandler, level);
                        }
                    }
                }
                if (!success.get()) {
                    insertItemOnSelfInventory(itemStack);
                }
            } else {
                insertItemOnSelfInventory(itemStack);
            }

        } catch (Exception e) {
            System.out.println("§f[INM§f]§c: Failed to insert item in: " + this.getBlockPos());
        }
    }

    private void insertItemOnSelfInventory(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return;
        }

        for (int slot : OUTPUT_SLOT) {
            ItemStack existingStack = this.itemHandler.getStackInSlot(slot);
            if (!existingStack.isEmpty() &&
                    //TODO REVIEW
                    ItemStack.isSameItem(existingStack, itemStack) &&
                    existingStack.getItem() == itemStack.getItem() &&
                    existingStack.getCount() < existingStack.getMaxStackSize()) {

                int spaceAvailable = existingStack.getMaxStackSize() - existingStack.getCount();
                int amountToAdd = Math.min(spaceAvailable, itemStack.getCount());

                existingStack.grow(amountToAdd);
                itemStack.shrink(amountToAdd);

                if (itemStack.isEmpty()) {
                    return;
                }
            }
        }

        for (int slot : OUTPUT_SLOT) {
            if (this.itemHandler.getStackInSlot(slot).isEmpty()) {
                this.itemHandler.insertItem(slot, itemStack.copy(), false);
                return;
            }
        }
    }

    private boolean canLink(BlockEntity blockEntity) {
        return (int) Math.sqrt(this.getBlockPos().distSqr(blockEntity.getBlockPos())) < 100;
    }

    public String getHasLink() {
        if (this.data.get(8) != 0 || this.data.get(9) != 0 || this.data.get(10) != 0) {
            return "X: " + this.data.get(8) + ", Y: " + this.data.get(9) + ", Z: " + this.data.get(10);
        }
        return "[Unlinked]";
    }

    public ItemStack getLikedBlock() {
        return new ItemStack(level.getBlockState(new BlockPos(
                this.data.get(8),
                this.data.get(9),
                this.data.get(10))).getBlock().asItem());
    }

    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }

    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
    private boolean showArea = false;

    public void setShowArea(boolean show) {
        this.showArea = show;
        this.setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public boolean shouldShowArea() {
        return this.showArea;
    }

    private void renderAreaPreview(Level level, BlockPos pos) {
        if (!showArea || !level.isClientSide()) {
            return;
        }

        int machineLevel = getMachineLevel();
        if (machineLevel <= 0) {
            return;
        }

        int range = machineLevel;
        BlockPos start = pos.above();

        renderCubeEdges(level, start, range);
    }

    private void renderCubeEdges(Level level, BlockPos start, int range) {
        for (int y = 0; y <= 2; y++) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    if (isEdgePosition(x, y, z, range)) {
                        spawnEdgeParticle(level, start, x, y, z);
                    }
                }
            }
        }
    }

    private void spawnEdgeParticle(Level level, BlockPos start, int x, int y, int z) {
        if (level.random.nextFloat() < 0.5f) {
            double particleX = start.getX() + x + 0.5;
            double particleY = start.getY() + y;
            double particleZ = start.getZ() + z + 0.5;

            level.addParticle(
                    ParticleTypes.END_ROD,
                    particleX,
                    particleY,
                    particleZ,
                    0, 0.01, 0
            );
        }
    }

    private boolean isEdgePosition(int x, int y, int z, int range) {
        if (Math.abs(x) == range && Math.abs(z) == range) {
            return true;
        }
        if (y == 0 || y == 2) {
            return Math.abs(x) == range || Math.abs(z) == range;
        }
        return false;
    }

    public void clientTick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            return;
        }

        if (this.showArea) {
            renderAreaPreview(level, pos);
        }
    }


    private boolean hasFreeSlots() {
        for (int slot : OUTPUT_SLOT) {
            if (itemHandler.getStackInSlot(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void notifyOwner() {
    }


    private void verifySolidFuel(){
        ItemStack slotItem = itemHandler.getStackInSlot(FUEL_SLOT);
        int burnTime = slotItem.getBurnTime(null);
        if(burnTime > 1){
            while(itemHandler.getStackInSlot(FUEL_SLOT).getCount() > 0 && this.getEnergyStorage().getEnergyStored() + burnTime < this.getEnergyStorage().getMaxEnergyStored()){
                this.getEnergyStorage().receiveEnergy(burnTime, false);
                itemHandler.extractItem(FUEL_SLOT, 1, false);
            }
        }
    }

    private void handleLinkedInsertion(ItemStack stack) {
        if (stack.isEmpty()) return;

        BlockEntity targetEntity = level.getBlockEntity(new BlockPos(
                this.data.get(8),
                this.data.get(9),
                this.data.get(10)
        ));

        if (targetEntity == null) return;
        IItemHandler itemHandler = ItemStackHandlerUtils.getBlockCapabilityItemHandler(level, targetEntity.getBlockPos(), null);
        if(itemHandler == null){return;}
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            if (itemHandler.isItemValid(slot, stack)) {
                ItemStack remaining = itemHandler.insertItem(slot, stack.copy(), false);
                stack.setCount(remaining.getCount());
                if (stack.isEmpty()) break;
            }
        };
    }
}