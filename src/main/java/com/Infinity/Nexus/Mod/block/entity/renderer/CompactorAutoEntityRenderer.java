package com.Infinity.Nexus.Mod.block.entity.renderer;

import com.Infinity.Nexus.Mod.block.entity.CompactorAutoBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.neoforged.neoforge.items.IItemHandler;

public class CompactorAutoEntityRenderer implements BlockEntityRenderer<CompactorAutoBlockEntity> {
    private static final int SIZE = 3;
    private static final float SPACING = 1.0f;
    private static final float BASE_HEIGHT = 1.5f;
    private static final float ITEM_SCALE = 1f;
    private static final float CENTER_X = 0.5f;
    private static final float CENTER_Z = 0.5f;

    public CompactorAutoEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CompactorAutoBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        IItemHandler itemHandler = blockEntity.getItemHandler(null);
        if (itemHandler == null) return;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();
        int lightLevel = getLightLevel(level, pos);
        int progress = blockEntity.getCurrentProgress();
        int maxProgress = blockEntity.getMaxProgress();

        float animationFactor = maxProgress > 0 ? (float) progress / maxProgress : 0f;

        for (int layer = 0; layer < SIZE; layer++) {
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    int slot = layer * SIZE * SIZE + row * SIZE + col;
                    ItemStack stack = itemHandler.getStackInSlot(slot);

                    if (!stack.isEmpty()) {
                        poseStack.pushPose();

                        float originalX = (col - 1) * SPACING;
                        float originalY = layer * SPACING;
                        float originalZ = (row - 1) * SPACING;

                        float factor = Math.max(0.2f, 1 - animationFactor);
                        float xPos = CENTER_X + (originalX * factor);
                        float yPos = BASE_HEIGHT + (originalY * factor);
                        float zPos = CENTER_Z + (originalZ * factor);

                        poseStack.translate(xPos, yPos, zPos);

                        float scale = ITEM_SCALE * Math.max(0.2f, (1 - animationFactor));
                        poseStack.scale(scale, scale, scale);

                        itemRenderer.renderStatic(stack, ItemDisplayContext.NONE, lightLevel, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, level, 0);

                        poseStack.popPose();
                    }
                }
            }
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos.above());
        int skyLight = level.getBrightness(LightLayer.SKY, pos.above());
        return LightTexture.pack(blockLight, skyLight);
    }
}