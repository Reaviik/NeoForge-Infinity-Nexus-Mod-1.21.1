package com.Infinity.Nexus.Mod.item.client.parceiros;

import com.Infinity.Nexus.Core.utils.GetResourceLocation;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.custom.FractalArmorItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class FractalArmorModel extends GeoModel<FractalArmorItem> {
    static String name;
    public FractalArmorModel(String name) {
        FractalArmorModel.name = name;
    }

    public static class FractalArmorRenderer extends GeoArmorRenderer<FractalArmorItem> {
        public FractalArmorRenderer(String name) {
            super(new FractalArmorModel(name));
        }

        @Override
        public RenderType getRenderType(FractalArmorItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
            return RenderType.eyes(texture);
        }
    }

    @Override
    public ResourceLocation getModelResource(FractalArmorItem animatable) {
        return GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, "geo/item/armor/fractal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FractalArmorItem animatable) {
        String path = "textures/models/armor/" + name + ".png";
        ResourceLocation location = GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, path);
        if (!resourceExists(location)) {
            location = GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/models/armor/infinity.png");
        }
        return location;
    }

    private boolean resourceExists(ResourceLocation location) {
        String path = String.format("/assets/%s/%s", location.getNamespace(), location.getPath());
        return getClass().getResource(path) != null;
    }

    @Override
    public ResourceLocation getAnimationResource(FractalArmorItem animatable) {
        return GetResourceLocation.withNamespaceAndPath(InfinityNexusMod.MOD_ID, "animations/item/armor/fractal.animation.json");
    }

}

