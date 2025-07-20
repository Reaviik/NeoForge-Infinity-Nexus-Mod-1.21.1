package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.block.entity.renderer.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = InfinityNexusMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.FACTORY_BE.get(),
                FactoryBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.DISPLAY_BE.get(),
                DisplayBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.PLACER_BE.get(),
                PlacerBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.INFUSER_BE.get(),
                InfuserBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.TANK_BE.get(),
                TankBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.COMPACTOR_AUTO_BE.get(),
                CompactorAutoEntityRenderer::new);
    }
}
