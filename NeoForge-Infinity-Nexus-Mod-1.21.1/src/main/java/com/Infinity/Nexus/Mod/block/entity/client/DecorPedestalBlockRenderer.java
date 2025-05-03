package com.Infinity.Nexus.Mod.block.entity.client;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.pedestals.DecorPedestalBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DecorPedestalBlockRenderer extends GeoBlockRenderer<DecorPedestalBlockEntity> {
    public DecorPedestalBlockRenderer(String pedestalName) {
        super(new MagicPedestalBlockModel(pedestalName));
    }

    public static class MagicPedestalBlockModel extends GeoModel<DecorPedestalBlockEntity> {
        private final String pedestalName;

        public MagicPedestalBlockModel(String pedestalName) {
            this.pedestalName = pedestalName;
        }

        @Override
        public ResourceLocation getModelResource(DecorPedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "geo/" + pedestalName + "_pedestal_model.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(DecorPedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/block/pedestals/" + pedestalName + "_pedestal.png");
        }

        @Override
        public ResourceLocation getAnimationResource(DecorPedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "animations/" + pedestalName + "_pedestal.animation.json");
        }

        @Override
        public RenderType getRenderType(DecorPedestalBlockEntity animatable, ResourceLocation texture) {
            return RenderType.entityTranslucent(getTextureResource(animatable));
        }
    }
}

