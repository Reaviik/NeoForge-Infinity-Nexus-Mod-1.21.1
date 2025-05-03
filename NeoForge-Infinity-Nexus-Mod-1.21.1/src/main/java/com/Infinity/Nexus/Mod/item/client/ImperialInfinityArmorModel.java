package com.Infinity.Nexus.Mod.item.client;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.custom.ImperialInfinityArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ImperialInfinityArmorModel extends GeoModel<ImperialInfinityArmorItem> {
    @Override
    public ResourceLocation getModelResource(ImperialInfinityArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "geo/item/armor/imperial_infinity_3d_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ImperialInfinityArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/models/armor/imperial_infinity_3d_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ImperialInfinityArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "animations/item/armor/imperial_infinity_3d_armor.animation.json");
    }
}
