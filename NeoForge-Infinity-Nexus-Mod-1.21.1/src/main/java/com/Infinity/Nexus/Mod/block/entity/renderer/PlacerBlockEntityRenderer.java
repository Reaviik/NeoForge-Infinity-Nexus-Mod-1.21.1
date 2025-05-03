package com.Infinity.Nexus.Mod.block.entity.renderer;

import com.Infinity.Nexus.Mod.block.custom.Placer;
import com.Infinity.Nexus.Mod.block.entity.PlacerBlockEntity;
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
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class PlacerBlockEntityRenderer implements BlockEntityRenderer<PlacerBlockEntity> {

    public PlacerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PlacerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource,
                       int pPackedLight, int pPackedOverlay) {


        ItemStack itemStack = pBlockEntity.getRenderStack(0);
        Direction direction = pBlockEntity.getBlockState().getValue(Placer.FACING);

        float x = switch (direction) {
            case UP -> 0.5f;
            case DOWN -> 0.5f;
            case NORTH -> 0.5f;
            case SOUTH -> 0.5f;
            case EAST -> 1f;
            case WEST -> 0f;
        };
        float y = switch (direction) {
            case UP -> 1.0F;
            case DOWN -> 0f;
            case NORTH -> 0.5f;
            case SOUTH -> 0.5f;
            case EAST -> 0.5f;
            case WEST -> 0.5f;
        };
        float z = switch (direction) {
            case UP -> 0.5f;
            case DOWN -> 0.5f;
            case NORTH -> 0f;
            case SOUTH -> 1f;
            case EAST -> 0.5f;
            case WEST -> 0.5f;
        };
        if(itemStack != ItemStack.EMPTY) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                pPoseStack.pushPose();
                pPoseStack.translate(x, y, z);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(Placer.FACING).toYRot()));
                pPoseStack.mulPose(Axis.YN.rotationDegrees(180.0F));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0);
                pPoseStack.popPose();
        }
    }
    private int getLightLevel(Level pLevel, BlockPos pPos) {
        int blight = pLevel.getBrightness(LightLayer.BLOCK, pPos);
        int slight = pLevel.getBrightness(LightLayer.SKY, pPos);

        return LightTexture.pack(blight, slight);
    }
}
