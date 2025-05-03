package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.TranslocatorBlockEntityBase;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TranslocatorLink extends Item {
    public TranslocatorLink(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        List<BlockPos> positions = stack.getOrDefault(ModDataComponents.TRANSLOCATOR_COORDS.get(), List.of());
        boolean multiples = stack.getOrDefault(ModDataComponents.TRANSLOCATOR_TYPE.get(), false);

        tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.translocator_link_type")
                .append(Component.literal(" " + (multiples ? "Multiples" : "Single"))));

        if (!positions.isEmpty()) {
            tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.translocator_link_cord"));
            for (BlockPos pos : positions) {
                tooltipComponents.add(Component.literal("X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ()));
            }
        }
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (!context.getLevel().isClientSide()) {
            BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
            Player player = context.getPlayer();
            BlockPos newPos = context.getClickedPos();

            if (blockEntity instanceof TranslocatorBlockEntityBase translocator) {
                if (!Objects.requireNonNull(player).isShiftKeyDown()) {
                    List<BlockPos> positions = stack.getOrDefault(ModDataComponents.TRANSLOCATOR_COORDS.get(), List.of());
                    translocator.setCords(positions, player);
                } else {
                    List<BlockPos> existingPositions = new ArrayList<>(
                            stack.getOrDefault(ModDataComponents.TRANSLOCATOR_COORDS.get(), List.of())
                    );

                    if (existingPositions.contains(newPos)) {
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_duplicate")));
                    } else if (existingPositions.size() >= 9) {
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_full")
                                        .append(Component.literal(" " + existingPositions.size()))));
                    } else {
                        existingPositions.add(newPos.immutable());
                        stack.set(ModDataComponents.TRANSLOCATOR_COORDS.get(), ImmutableList.copyOf(existingPositions));

                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_added",
                                        newPos.getX(), newPos.getY(), newPos.getZ())));
                    }
                }
            } else {
                if (Objects.requireNonNull(player).isShiftKeyDown()) {
                    if (stack.has(ModDataComponents.TRANSLOCATOR_COORDS.get())) {
                        stack.remove(ModDataComponents.TRANSLOCATOR_COORDS.get());
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_clear")));
                    } else {
                        boolean multiples = stack.getOrDefault(ModDataComponents.TRANSLOCATOR_TYPE.get(), false);
                        stack.set(ModDataComponents.TRANSLOCATOR_TYPE.get(), !multiples);
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_link_type")
                                        .append(Component.literal(" " + (!multiples ? "Multiples" : "Single")))));
                    }
                }
            }
        }
        return super.onItemUseFirst(stack, context);
    }
}
