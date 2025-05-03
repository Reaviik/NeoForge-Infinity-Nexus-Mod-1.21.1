package com.Infinity.Nexus.Mod.item.client;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.custom.InfinityArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class InfinityArmorModel extends GeoModel<InfinityArmorItem> {
    @Override
    public ResourceLocation getModelResource(InfinityArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "geo/item/armor/infinity_3d_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InfinityArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/models/armor/infinity_3d_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(InfinityArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "animations/item/armor/infinity_3d_armor.animation.json");
    }
}
