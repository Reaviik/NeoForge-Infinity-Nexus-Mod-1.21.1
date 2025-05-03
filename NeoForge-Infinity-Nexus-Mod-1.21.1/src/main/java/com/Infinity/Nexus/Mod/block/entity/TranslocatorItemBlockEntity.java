package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.utils.ItemStackHandlerUtils;
import com.Infinity.Nexus.Core.utils.SendParticlesPath;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;

public class TranslocatorItemBlockEntity extends TranslocatorBlockEntityBase {
    public TranslocatorItemBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TRASLOCATOR_ITEM_BE.get(), pPos, pBlockState);
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        if (mode == 0 && !isInputSlotEmpty()) {
            if(hasProgressFinished()) {
                depositItem(pLevel, pPos);
            }else{
                increaseProgress();
            }
        } else {
            if(canSend()) {
                if (!isInputSlotEmpty()) {
                    if(hasProgressFinished()) {
                        resetProgress();
                        sendItem(pLevel);
                        upgradeStep();
                    }
                    increaseProgress();
                } else {
                    if(hasProgressFinished() && isInputSlotEmpty()) {
                        resetProgress();
                        pullItem(pLevel, pPos, pState);
                    }
                    increaseProgress();
                }
            }
        }
        updateLit(pLevel, pState);
        setChanged(pLevel, pPos, pState);
    }

    private void pullItem(Level pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity entity = getInventoryPos(pLevel, pPos, pState);
        if (entity == null) return;

        IItemHandler sourceHandler = ItemStackHandlerUtils.getBlockCapabilityItemHandler(pLevel, entity.getBlockPos(), Direction.UP);
        if (sourceHandler == null) return;

        if (filter != null && filter.length > 0) {
            boolean itemPulled = false;
            int startIdx = filterIndex;

            for (int i = 0; i < filter.length; i++) {
                int currentIdx = (startIdx + i) % filter.length;
                String targetItem = filter[currentIdx];

                for (int slot = 0; slot < sourceHandler.getSlots(); slot++) {
                    ItemStack stackInSlot = sourceHandler.getStackInSlot(slot);
                    if (stackInSlot.isEmpty()) continue;

                    String itemId = stackInSlot.getItem().builtInRegistryHolder().key().location().toString();
                    if (!itemId.equals(targetItem)) continue;

                    ItemStack currentStack = this.itemHandler.getStackInSlot(INPUT_SLOT);
                    int maxStackSize = Math.min(stackInSlot.getMaxStackSize(), this.itemHandler.getSlotLimit(INPUT_SLOT));

                    if (currentStack.isEmpty()) {
                        int amountToPull = Math.min(stackInSlot.getCount(), maxStackSize);
                        ItemStack extracted = sourceHandler.extractItem(slot, amountToPull, false);
                        if (!extracted.isEmpty()) {
                            this.itemHandler.setStackInSlot(INPUT_SLOT, extracted);
                            resetProgress();
                            itemPulled = true;
                            filterIndex = (currentIdx + 1) % filter.length;
                            setChanged();
                            break;
                        }
                    } else if (ItemStack.isSameItemSameComponents(currentStack, stackInSlot)) {
                        int spaceAvailable = maxStackSize - currentStack.getCount();
                        if (spaceAvailable > 0) {
                            int amountToPull = Math.min(stackInSlot.getCount(), spaceAvailable);
                            ItemStack extracted = sourceHandler.extractItem(slot, amountToPull, false);
                            if (!extracted.isEmpty()) {
                                currentStack.grow(extracted.getCount());
                                this.itemHandler.setStackInSlot(INPUT_SLOT, currentStack);
                                resetProgress();
                                itemPulled = true;
                                filterIndex = (currentIdx + 1) % filter.length;
                                setChanged();
                                break;
                            }
                        }
                    }
                    if (itemPulled) break;
                }
                if (itemPulled) break;
            }
        } else {
            // Lógica sem filtro
            for (int slot = 0; slot < sourceHandler.getSlots(); slot++) {
                ItemStack stackInSlot = sourceHandler.getStackInSlot(slot);
                if (stackInSlot.isEmpty()) continue;

                ItemStack currentStack = this.itemHandler.getStackInSlot(INPUT_SLOT);
                int maxStackSize = Math.min(stackInSlot.getMaxStackSize(), this.itemHandler.getSlotLimit(INPUT_SLOT));

                if (currentStack.isEmpty()) {
                    int amountToPull = Math.min(stackInSlot.getCount(), maxStackSize);
                    ItemStack extracted = sourceHandler.extractItem(slot, amountToPull, false);
                    if (!extracted.isEmpty()) {
                        this.itemHandler.setStackInSlot(INPUT_SLOT, extracted);
                        resetProgress();
                        break;
                    }
                } else if (ItemStack.isSameItemSameComponents(currentStack, stackInSlot)) {
                    int spaceAvailable = maxStackSize - currentStack.getCount();
                    if (spaceAvailable > 0) {
                        int amountToPull = Math.min(stackInSlot.getCount(), spaceAvailable);
                        ItemStack extracted = sourceHandler.extractItem(slot, amountToPull, false);
                        if (!extracted.isEmpty()) {
                            currentStack.grow(extracted.getCount());
                            this.itemHandler.setStackInSlot(INPUT_SLOT, currentStack);
                            resetProgress();
                            break;
                        }
                    }
                }
            }
        }
    }


    private void depositItem(Level level, BlockPos pos) {
        BlockEntity entity = getInventoryPos(level, pos, getBlockState());
        if (entity == null) return;

        IItemHandler targetHandler = ItemStackHandlerUtils.getBlockCapabilityItemHandler(level, entity.getBlockPos(), Direction.DOWN);
        if (targetHandler == null) return;

        ItemStack stackToDeposit = this.itemHandler.getStackInSlot(INPUT_SLOT);
        if (stackToDeposit.isEmpty()) return;

        for (int slot = 0; slot < targetHandler.getSlots(); slot++) {
            ItemStack remaining = targetHandler.insertItem(slot, stackToDeposit, false);
            if (remaining.getCount() != stackToDeposit.getCount()) {
                this.itemHandler.setStackInSlot(INPUT_SLOT, remaining);
                resetProgress();
                if (remaining.isEmpty()) break;
            }
        }
    }


    private void sendItem(Level level) {
        BlockPos targetPos = getDestination();
        BlockEntity entity = level.getBlockEntity(targetPos);
        if (entity instanceof TranslocatorItemBlockEntity translocator) {
            ItemStack stackToSend = this.itemHandler.getStackInSlot(INPUT_SLOT);
            if (stackToSend != null && !stackToSend.isEmpty()) {
                if (translocator.receiveItem(stackToSend)) {
                    this.itemHandler.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
                    SendParticlesPath.makePath((ServerLevel) this.getLevel(), ParticleTypes.CRIT, worldPosition, targetPos, 0.5D, 0.5D, 0.5D);
                }else{
                    progress = maxProgress - 5;
                }
            }
        }
    }

    public boolean receiveItem(ItemStack stack) {
        if (stack.isEmpty() || !isInputSlotEmpty() || !isFiltered(stack)) {
            return false;
        }

        ItemStack currentStack = this.itemHandler.getStackInSlot(INPUT_SLOT);

        if (currentStack.isEmpty()) {
            int stackLimit = Math.min(stack.getMaxStackSize(), this.itemHandler.getSlotLimit(INPUT_SLOT));
            ItemStack toInsert = stack.copy();
            toInsert.setCount(Math.min(toInsert.getCount(), stackLimit));
            this.itemHandler.setStackInSlot(INPUT_SLOT, toInsert);
        } else if (currentStack == stack) {
            int maxStackSize = Math.min(currentStack.getMaxStackSize(), this.itemHandler.getSlotLimit(INPUT_SLOT));
            int newCount = currentStack.getCount() + stack.getCount();

            if (newCount <= maxStackSize) {
                currentStack.grow(stack.getCount());
            } else {
                int remaining = maxStackSize - currentStack.getCount();
                currentStack.grow(remaining);
            }
            this.itemHandler.setStackInSlot(INPUT_SLOT, currentStack);
        } else {
            return false;
        }

        if (canSend()) {
            progress = ConfigUtils.translocator_skip_progress;
        }

        setChanged();
        return true;
    }

}