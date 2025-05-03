package com.Infinity.Nexus.Mod.block.entity.renderer;

import com.Infinity.Nexus.Mod.block.custom.ItemDisplay;
import com.Infinity.Nexus.Mod.block.entity.DisplayBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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

public class DisplayBlockEntityRenderer implements BlockEntityRenderer<DisplayBlockEntity> {

    public DisplayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(DisplayBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource,
                       int pPackedLight, int pPackedOverlay) {


        ItemStack itemStack = pBlockEntity.getRenderStack(0);

        if(itemStack != ItemStack.EMPTY) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            int LIT = pBlockEntity.getBlockState().getValue(ItemDisplay.LIT);

            if (LIT <= 3) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5, LIT < 2 ? 0.5 : 2.1, 0.5);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ItemDisplay.FACING).toYRot()));
                pPoseStack.mulPose(Axis.YN.rotationDegrees(LIT == 0 || LIT == 2 ? 180 : pBlockEntity.getRotation()));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0);
                pPoseStack.popPose();

            } else if (LIT == 4 || LIT == 5) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5, LIT == 4 ? 0.5 : 2.1, 0.5);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ItemDisplay.FACING).toYRot()));
                pPoseStack.mulPose(Axis.XN.rotationDegrees(45));
                pPoseStack.mulPose(Axis.YN.rotationDegrees(180));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
                pPoseStack.popPose();

            } else if (LIT == 6 || LIT == 7) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5,0.5, 0.5);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ItemDisplay.FACING).toYRot()));
                pPoseStack.mulPose(Axis.ZN.rotationDegrees(LIT == 6 ? 225 : 45));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
                pPoseStack.popPose();
            }
        }
    }
    private int getLightLevel(Level pLevel, BlockPos pPos) {
        int blight = pLevel.getBrightness(LightLayer.BLOCK, pPos);
        int slight = pLevel.getBrightness(LightLayer.SKY, pPos);

        return LightTexture.pack(blight, slight);
    }
}
