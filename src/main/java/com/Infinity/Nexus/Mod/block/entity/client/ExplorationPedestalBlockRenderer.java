package com.Infinity.Nexus.Mod.block.entity.client;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.pedestals.ExplorationPedestalBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ExplorationPedestalBlockRenderer extends GeoBlockRenderer<ExplorationPedestalBlockEntity> {
    public ExplorationPedestalBlockRenderer(String pedestalName) {
        super(new MagicPedestalBlockModel(pedestalName));
    }

    public static class MagicPedestalBlockModel extends GeoModel<ExplorationPedestalBlockEntity> {
        private final String pedestalName;

        public MagicPedestalBlockModel(String pedestalName) {
            this.pedestalName = pedestalName;
        }

        @Override
        public ResourceLocation getModelResource(ExplorationPedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "geo/" + pedestalName + "_pedestal_model.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(ExplorationPedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/block/pedestals/" + pedestalName + "_pedestal.png");
        }

        @Override
        public ResourceLocation getAnimationResource(ExplorationPedestalBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "animations/" + pedestalName + "_pedestal.animation.json");
        }

        @Override
        public RenderType getRenderType(ExplorationPedestalBlockEntity animatable, ResourceLocation texture) {
            return RenderType.entityTranslucent(getTextureResource(animatable));
        }
    }
}

