package com.Infinity.Nexus.Mod.item.client;


import com.Infinity.Nexus.Mod.item.custom.ImperialInfinityArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ImperialInfinityArmorRenderer extends GeoArmorRenderer<ImperialInfinityArmorItem> {
    public ImperialInfinityArmorRenderer() {
        super(new ImperialInfinityArmorModel());
    }
}
