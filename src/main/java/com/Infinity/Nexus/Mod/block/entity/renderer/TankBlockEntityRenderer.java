package com.Infinity.Nexus.Mod.block.entity.renderer;

import com.Infinity.Nexus.Mod.block.entity.TankBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
//Creditos a: https://github.com/DaRealTurtyWurty/1.20-Tutorial-Mod/blob/main/src/main/java/dev/turtywurty/tutorialmod/client/renderer/ExampleFluidBER.java
public class TankBlockEntityRenderer implements BlockEntityRenderer<TankBlockEntity> {
        private final BlockEntityRendererProvider.Context context;

        public TankBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
            this.context = ctx;
        }

        @Override
        public void render(@NotNull TankBlockEntity pBlockEntity, float pPartialTick,
                           @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight,
                           int pPackedOverlay) {
            FluidStack fluidStack = pBlockEntity.getFluidTank().getFluid();
            if (fluidStack.isEmpty())
                return;

            Level level = pBlockEntity.getLevel();
            if (level == null)
                return;

            BlockPos pos = pBlockEntity.getBlockPos();

            IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
            ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
            if (stillTexture == null)
                return;

            FluidState state = fluidStack.getFluid().defaultFluidState();

            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
            int tintColor = fluidTypeExtensions.getTintColor(state, level, pos);

            float height = (((float) pBlockEntity.getFluidTank().getFluidAmount() / pBlockEntity.getFluidTank().getCapacity()) * 0.45f) + 0.35f;
            float start = 0.35f;

            VertexConsumer builder = pBuffer.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

            if(pBlockEntity.getFluidTank().getFluidAmount() < pBlockEntity.getFluidTank().getCapacity()) {
                drawQuad(builder, pPoseStack, 0.25f, height, 0.25f, 0.75f, height, 0.75f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
            }

            drawQuad(builder, pPoseStack, 0.25f, start, 0.25f, 0.75f, height, 0.25f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);

            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            pPoseStack.translate(-1f, 0, -1.5f);
            drawQuad(builder, pPoseStack, 0.25f, start, 0.75f, 0.75f, height, 0.75f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
            pPoseStack.popPose();

            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            pPoseStack.translate(-1f, 0, 0);
            drawQuad(builder, pPoseStack, 0.25f, start, 0.25f, 0.75f, height, 0.25f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
            pPoseStack.popPose();

            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
            pPoseStack.translate(0, 0, -1f);
            drawQuad(builder, pPoseStack, 0.25f, start, 0.25f, 0.75f, height, 0.25f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
            pPoseStack.popPose();

        }

        private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
            builder.addVertex(poseStack.last().pose(), x, y, z)
                    .setColor(color)
                    .setUv(u, v)
                    .setUv2(packedLight,packedLight)
                    .setNormal(1, 0, 0);
            //TODO endvertx
        }

        private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
            drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
            drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
            drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
            drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
        }
}
