package com.Infinity.Nexus.Mod.entity.client;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.entity.custom.Asgreon;
import com.Infinity.Nexus.Mod.entity.layers.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class AsgreonRenderer extends MobRenderer<Asgreon, AsgreonModel<Asgreon> > {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/entity/asgreon/asgreon.png");
    private static int light = 5;
    private static boolean lightIncrease = true;

    public AsgreonRenderer(EntityRendererProvider.Context context) {
        super(context, new AsgreonModel<>(context.bakeLayer(ModModelLayers.ASGREON_LAYER)), 0.5f);
    }


    @Override
    public void render(Asgreon pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Asgreon pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        return super.getRenderType(pLivingEntity, pBodyVisible, true, true);
    }

    @Override
    protected int getBlockLightLevel(Asgreon pEntity, BlockPos pPos) {
        if(lightIncrease){
            if(light >= 1400){
                light = 1400;
                lightIncrease = false;
            }else{
                light++;
            }
        }else{
            if(light <= 140){
                light = 140;
                lightIncrease = true;
            }else{
                light--;
            }
        }
        return light/100;
    }

    @Override
    public ResourceLocation getTextureLocation(Asgreon entity) {
        return LOCATION;
    }
}
