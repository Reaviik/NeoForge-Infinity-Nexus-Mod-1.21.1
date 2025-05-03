package com.Infinity.Nexus.Mod.recipe.auto;

import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.FactoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class FactoryRecipeCopy {
    public static InteractionResult copy(Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand) {
        if (pLevel.isClientSide() && pPlayer.isCreative()) {
            return InteractionResult.SUCCESS;
        }
        if (RecipeCopyUtils.getWand(pPlayer.getItemInHand(pHand).getItem())) {
            if (pPlayer.getOffhandItem().isEmpty()) {
                pPlayer.sendSystemMessage(Component.literal(InfinityNexusMod.message + "Missing output item (offhand item)"));
                return InteractionResult.PASS;
            }
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FactoryBlockEntity factory) {
                IItemHandler itemHandler = factory.getRecipeInventory();
                if(itemHandler.getStackInSlot(21).isEmpty()) {
                    pPlayer.sendSystemMessage(Component.literal(InfinityNexusMod.message + "Missing component item"));
                    return InteractionResult.PASS;
                }
                int component = ModUtils.getComponentLevel(itemHandler.getStackInSlot(21));
                List<String> itemIds = new ArrayList<>();

                for (int i = 0; i < 15; i++) {
                    if (!itemHandler.getStackInSlot(i).isEmpty()) {
                        itemIds.add(itemHandler.getStackInSlot(i).getItem().builtInRegistryHolder().key().location().toString());
                    } else {
                        pPlayer.sendSystemMessage(Component.literal(InfinityNexusMod.message + "Missing input item, need 16 items"));
                        return InteractionResult.PASS;
                    }
                }

                StringBuilder recipeBuilder = new StringBuilder();
                recipeBuilder.append("{\n")
                        .append("  \"type\": \"infinity_nexus_mod:factory\",\n")
                        .append("  \"duration\": 1520,\n")
                        .append("  \"energy\": 1000,\n")
                        .append("  \"ingredients\": [\n")
                        .append("    { \"tag\": \"infinity_nexus_core:up_" + component + "\" },\n");
                for (String itemId : itemIds) {
                    recipeBuilder.append("    { \"item\": \"" + itemId + "\" },\n");
                }
                if (recipeBuilder.charAt(recipeBuilder.length() - 2) == ',') {
                    recipeBuilder.delete(recipeBuilder.length() - 2, recipeBuilder.length());
                }
                recipeBuilder.append("\n],\n")
                        .append(RecipeCopyUtils.getOutputItem(pPlayer.getOffhandItem().copy()))
                        .append("}");

                String recipe = recipeBuilder.toString();
                MutableComponent message = Component.literal(InfinityNexusMod.message + "Click to copy recipe for Factory")
                        .withStyle(style -> style
                                .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, recipe))
                        );
                pPlayer.sendSystemMessage(message);

                return InteractionResult.PASS;
            }
        }
        return InteractionResult.PASS;
    }
}
