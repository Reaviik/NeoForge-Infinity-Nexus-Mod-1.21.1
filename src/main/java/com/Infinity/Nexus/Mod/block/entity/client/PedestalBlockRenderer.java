package com.Infinity.Nexus.Mod.block.entity.client;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.pedestals.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PedestalBlockRenderer<T extends BasePedestalBlockEntity> extends GeoBlockRenderer<T> {
    public PedestalBlockRenderer(String pedestalName) {
        super(new PedestalBlockModel<>(pedestalName));
    }

    public static class PedestalBlockModel<T extends BasePedestalBlockEntity> extends GeoModel<T> {
        private final String pedestalName;

        public PedestalBlockModel(String pedestalName) {
            this.pedestalName = pedestalName;
        }

        @Override
        public ResourceLocation getModelResource(T animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "geo/" + pedestalName + "_pedestal_model.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(T animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/block/pedestals/" + pedestalName + "_pedestal.png");
        }

        @Override
        public ResourceLocation getAnimationResource(T animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "animations/" + pedestalName + "_pedestal.animation.json");
        }

        @Override
        public RenderType getRenderType(T animatable, ResourceLocation texture) {
            return RenderType.entityTranslucent(getTextureResource(animatable));
        }
    }
}