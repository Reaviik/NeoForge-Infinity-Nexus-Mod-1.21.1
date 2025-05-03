package com.Infinity.Nexus.Mod.block.entity.renderer;

import com.Infinity.Nexus.Mod.block.custom.Factory;
import com.Infinity.Nexus.Mod.block.entity.FactoryBlockEntity;
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

public class FactoryBlockEntityRenderer implements BlockEntityRenderer<FactoryBlockEntity> {


    float[] x = {1.032f, 0.782f, 0.5f,   0.218f, -0.032f, 1.032f,-0.04f,1.032f,-0.04f,1.032f,-0.04f,1.032f,0.782f,0.5f,0.218f,-0.032f};
    float[] y = {1.175f, 1.145f, 1.115f, 1.145f, 1.175f,  1.145f, 1.145f, 1.115f, 1.115f, 1.145f, 1.145f, 1.175f, 1.145f, 1.115f, 1.145f, 1.175f};
    float[] z = {1.032f, 1.032f, 1.032f, 1.032f, 1.032f, 0.782f,0.782f,0.5f,0.5f,0.218f,0.218f,-0.032f,-0.032f,-0.032f,-0.032f,-0.032f};
    public FactoryBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(FactoryBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource,
                       int pPackedLight, int pPackedOverlay) {

        int maxProgress = pBlockEntity.getMaxProgress();
        int progress = pBlockEntity.getCurrentProgress();


        for (int i = 0; i < 16; i++) {
            if(pBlockEntity.getRenderStack(i) != ItemStack.EMPTY) {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                if (progress > 0 && maxProgress > 0) {

                    float x = this.x[i] - (this.x[i] - 0.5f) * ((float) progress / maxProgress);
                    float y = this.y[i] - (this.y[i] - 1.032f) * ((float) progress / maxProgress);
                    float z = this.z[i] - (this.z[i] - 0.5f) * ((float) progress / maxProgress);
                    ItemStack itemStack = pBlockEntity.getRenderStack(i);

                    pPoseStack.pushPose();
                    pPoseStack.translate(x, y, z);
                    pPoseStack.scale(0.1f, 0.1f, 0.1f);
                    pPoseStack.mulPose(Axis.YP.rotationDegrees(360.0f * ((float) progress / maxProgress)));

                    itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                            pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
                    pPoseStack.popPose();
                } else {
                    ItemStack itemStack = pBlockEntity.getRenderStack(i);

                    pPoseStack.pushPose();
                    pPoseStack.translate(x[i], y[i], z[i]);
                    pPoseStack.scale(0.1f, 0.1f, 0.1f);
                    pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(Factory.FACING).toYRot()));

                    itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                            pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
                    pPoseStack.popPose();
                }
            }
        }
    }
    private int getLightLevel(Level pLevel, BlockPos pPos) {
        int blight = pLevel.getBrightness(LightLayer.BLOCK, pPos);
        int slight = pLevel.getBrightness(LightLayer.SKY, pPos);

        return LightTexture.pack(blight, slight);
    }
}
