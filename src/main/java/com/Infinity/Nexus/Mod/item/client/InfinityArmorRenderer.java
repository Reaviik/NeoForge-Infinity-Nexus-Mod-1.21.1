package com.Infinity.Nexus.Mod.item.client;


import com.Infinity.Nexus.Mod.item.custom.InfinityArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class InfinityArmorRenderer extends GeoArmorRenderer<InfinityArmorItem> {
    public InfinityArmorRenderer() {
        super(new InfinityArmorModel());
    }
}
