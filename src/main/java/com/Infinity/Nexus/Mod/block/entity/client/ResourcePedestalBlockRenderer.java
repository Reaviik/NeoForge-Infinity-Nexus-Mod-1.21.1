package com.Infinity.Nexus.Mod.block.entity.client;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.pedestals.ResourcePedestalBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ResourcePedestalBlockRenderer extends GeoBlockRenderer<ResourcePedestalBlockEntity> {
    public ResourcePedestalBlockRenderer(String pedestalName) {
        super(new MagicPedestalBlockModel(pedestalName));
    }

    public static class MagicPedestalBlockModel extends GeoModel<ResourcePedestalBlockEntity> {
        private final String pedestalName;

        public MagicPedestalBlockModel(String pedestalName) {
            this.pedestalName = pedestalName;
        }

        @Override
        public ResourceLocation getModelResource(ResourcePedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "geo/" + pedestalName + "_pedestal_model.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(ResourcePedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/block/pedestals/" + pedestalName + "_pedestal.png");
        }

        @Override
        public ResourceLocation getAnimationResource(ResourcePedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "animations/" + pedestalName + "_pedestal.animation.json");
        }

        @Override
        public RenderType getRenderType(ResourcePedestalBlockEntity animatable, ResourceLocation texture) {
            return RenderType.entityTranslucent(getTextureResource(animatable));
        }
    }
}

