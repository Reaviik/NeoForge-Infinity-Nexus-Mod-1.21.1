package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;

import java.util.Set;

@EventBusSubscriber(modid = InfinityNexusMod.MOD_ID)
public class ComputeFovModifierBowEvent {
    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
        Set<Item> BOWS = Set.of(
                ModItemsAdditions.CARBON_BOW.get(),
                ModItemsAdditions.INFINITY_BOW.get(),
                ModItemsAdditions.IMPERIAL_INFINITY_BOW.get()
        );
        if (event.getPlayer().isUsingItem() && BOWS.contains(event.getPlayer().getMainHandItem().getItem())) {
            float fovModifier = 1f;
            int tickUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTick = (float) tickUsingItem / 20f;
            if(deltaTick > 1f) {
                deltaTick = 1f;
            }else{
                deltaTick *= deltaTick;
            }
            fovModifier += 1f - deltaTick * 0.15f;
            event.setNewFovModifier(fovModifier);
        }
    }
}