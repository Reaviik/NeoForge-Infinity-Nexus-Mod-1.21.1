package com.Infinity.Nexus.Mod.recipe.auto;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class CompactorRecipeCopy {
    public static InteractionResult copy(Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand) {
        if (pLevel.isClientSide() && !pPlayer.isCreative()) {
            return InteractionResult.SUCCESS;
        }
        if (RecipeCopyUtils.getWand(pPlayer.getItemInHand(pHand).getItem())) {
            if(pPlayer.getOffhandItem().isEmpty()){
                pPlayer.sendSystemMessage(Component.literal(InfinityNexusMod.message + "Missing output item (offhand item)"));
                return InteractionResult.PASS;
            }
            BlockPos centerPos = pPos.above(2);
            List<ItemEntity> entities = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.above(2)));

            if (!entities.isEmpty() && entities.get(0).getItem().getCount() == 1) {
                List<String> blockIds = new ArrayList<>();

                for (int y = -1; y <= 1; y++) { // Altura
                    for (int z = -1; z <= 1; z++) { // Profundidade
                        for (int x = -1; x <= 1; x++) { // Largura
                            BlockPos scanPos = centerPos.offset(x, y, z);

                            if (x == 0 && y == 0 && z == 0) { // Posição central
                                String nbt = RecipeCopyUtils.getItemNBT(entities.get(0).getItem());
                                String formattedNBT = nbt.substring(1, nbt.length() - 1);
                                String fixNBT = nbt.contains("null") ? "" : "\", " + formattedNBT;

                                blockIds.add(entities.get(0).getItem().getItem().builtInRegistryHolder().key().location() + fixNBT);
                            } else { // Blocos ao redor
                                BlockState blockState = pLevel.getBlockState(scanPos);
                                String blockId = blockState.getBlock().builtInRegistryHolder().key().location().toString();

                                if (blockId != null) {
                                    blockIds.add(blockId);
                                }
                            }
                        }
                    }
                }

                StringBuilder recipeBuilder = new StringBuilder();
                recipeBuilder.append("{\n")
                        .append("  \"type\": \"infinity_nexus_mod:compacting\",\n")
                        .append("  \"ingredients\": [\n");
                for (int i = 0; i < blockIds.size(); i++) {
                    recipeBuilder.append("    { \"item\": \"" + blockIds.get(i) + "\" },\n");
                }
                if (recipeBuilder.charAt(recipeBuilder.length() - 2) == ',') {
                    recipeBuilder.delete(recipeBuilder.length() - 2, recipeBuilder.length());
                }
                recipeBuilder.append("\n],\n")
                        .append(RecipeCopyUtils.getOutputItem(pPlayer.getOffhandItem().copy()))
                        .append("}");

                String recipe = recipeBuilder.toString();
                MutableComponent message = Component.literal(InfinityNexusMod.message + "Click to copy recipe for Compactor")
                        .withStyle(style -> style
                                .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, recipe))
                        );
                pPlayer.sendSystemMessage(message);

                return InteractionResult.CONSUME;
            } else {
                pPlayer.sendSystemMessage(Component.literal(InfinityNexusMod.message + "Missing Catalyst!"));
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

}
