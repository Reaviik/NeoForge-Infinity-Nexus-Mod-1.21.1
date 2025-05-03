package com.Infinity.Nexus.Mod;

import com.Infinity.Nexus.Core.fluid.BaseFluidType;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.block.entity.client.*;
import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.config.Config;
import com.Infinity.Nexus.Mod.entity.ModEntities;
import com.Infinity.Nexus.Mod.entity.client.AsgreonRenderer;
import com.Infinity.Nexus.Mod.entity.client.FlaronRenderer;
import com.Infinity.Nexus.Mod.fluid.ModFluidType;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.ModItemProperties;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import com.Infinity.Nexus.Mod.networking.ModMessages;
import com.Infinity.Nexus.Mod.recipe.ModRecipes;
import com.Infinity.Nexus.Mod.screen.ModMenuTypes;
import com.Infinity.Nexus.Mod.screen.assembler.AssemblerScreen;
import com.Infinity.Nexus.Mod.screen.battery.BatteryScreen;
import com.Infinity.Nexus.Mod.screen.condenser.CondenserScreen;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherScreen;
import com.Infinity.Nexus.Mod.screen.factory.FactoryScreen;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelScreen;
import com.Infinity.Nexus.Mod.screen.generator.GeneratorScreen;
import com.Infinity.Nexus.Mod.screen.mobcrusher.MobCrusherScreen;
import com.Infinity.Nexus.Mod.screen.placer.PlacerScreen;
import com.Infinity.Nexus.Mod.screen.press.PressScreen;
import com.Infinity.Nexus.Mod.screen.recycler.RecyclerScreen;
import com.Infinity.Nexus.Mod.screen.smeltery.SmelteryScreen;
import com.Infinity.Nexus.Mod.screen.solar.SolarScreen;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerScreen;
import com.Infinity.Nexus.Mod.screen.tank.TankScreen;
import com.Infinity.Nexus.Mod.tab.ModTabAdditions;
import com.Infinity.Nexus.Mod.tab.ModTabProgression;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(InfinityNexusMod.MOD_ID)
public class InfinityNexusMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "infinity_nexus_mod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String message = "§f[§4I§5n§9fi§3ni§bty§f]: §e";

    public InfinityNexusMod(IEventBus modEventBus, ModContainer modContainer)
    {

        ModBlocksAdditions.register(modEventBus);
        ModBlocksProgression.register(modEventBus);

        ModItemsAdditions.register(modEventBus);
        ModItemsProgression.register(modEventBus);

        ModTabAdditions.register(modEventBus);
        ModTabProgression.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);
        ModEntities.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidType.register(modEventBus);

        ModDataComponents.register(modEventBus);

        modEventBus.register(ModMessages.class);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::registerScreens);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC,"InfinityNexus/config.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            //ModMessages.register();
        });
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.MOB_CRUSHER_MENU.get(), MobCrusherScreen::new);
        event.register(ModMenuTypes.CRUSHER_MENU.get(), CrusherScreen::new);
        event.register(ModMenuTypes.PRESS_MENU.get(), PressScreen::new);
        event.register(ModMenuTypes.ASSEMBLY_MENU.get(), AssemblerScreen::new);
        event.register(ModMenuTypes.FACTORY_MENU.get(), FactoryScreen::new);
        event.register(ModMenuTypes.SQUEEZER_MENU.get(), SqueezerScreen::new);
        event.register(ModMenuTypes.SMELTERY_MENU.get(), SmelteryScreen::new);
        event.register(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
        event.register(ModMenuTypes.FERMENTATION_BARREL_MENU.get(), FermentationBarrelScreen::new);
        event.register(ModMenuTypes.RECYCLER_MENU.get(), RecyclerScreen::new);
        event.register(ModMenuTypes.MATTER_CONDENSER_MENU.get(), CondenserScreen::new);
        event.register(ModMenuTypes.SOLAR_MENU.get(), SolarScreen::new);
        event.register(ModMenuTypes.PLACER_MENU.get(), PlacerScreen::new);
        event.register(ModMenuTypes.TANK_MENU.get(), TankScreen::new);
        event.register(ModMenuTypes.BATTERY_MENU.get(), BatteryScreen::new);
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            ModItemProperties.addCustomItemProperties();

            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.LUBRICANT_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.LUBRICANT_FLOWING.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.ETHANOL_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.ETHANOL_FLOWING.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_FLOWING.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.VINEGAR_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.VINEGAR_FLOWING.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SUGARCANE_JUICE_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SUGARCANE_JUICE_FLOWING.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.WINE_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.WINE_FLOWING.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.EXPERIENCE_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.EXPERIENCE_FLOWING.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.STARLIQUID_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.STARLIQUID_FLOWING.get(), RenderType.translucent());

                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.INFUSER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.VOXEL_BLOCK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.ENTITY_DISPLAY.get(), RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.TANK.get(), RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.TRANSLOCATOR_ITEM.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.TRANSLOCATOR_ENERGY.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.TRANSLOCATOR_FLUID.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_2.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_3.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_4.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_5.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_6.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_7.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_8.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_9.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_10.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_11.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_12.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_13.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_14.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_15.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_16.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_17.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocksAdditions.CATWALK_18.get(), RenderType.translucent());


                EntityRenderers.register(ModEntities.ASGREON.get(), AsgreonRenderer::new);
                EntityRenderers.register(ModEntities.FLARON.get(), FlaronRenderer::new);
            });
        }
        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(((BaseFluidType) ModFluidType.LUBRICANT.get()).getClientFluidTypeExtensions(), ModFluidType.LUBRICANT.get());
            event.registerFluidType(((BaseFluidType) ModFluidType.ETHANOL.get()).getClientFluidTypeExtensions(), ModFluidType.ETHANOL.get());
            event.registerFluidType(((BaseFluidType) ModFluidType.OIL.get()).getClientFluidTypeExtensions(), ModFluidType.OIL.get());
            event.registerFluidType(((BaseFluidType) ModFluidType.VINEGAR.get()).getClientFluidTypeExtensions(), ModFluidType.VINEGAR.get());
            event.registerFluidType(((BaseFluidType) ModFluidType.SUGARCANE_JUICE.get()).getClientFluidTypeExtensions(), ModFluidType.SUGARCANE_JUICE.get());
            event.registerFluidType(((BaseFluidType) ModFluidType.WINE.get()).getClientFluidTypeExtensions(), ModFluidType.WINE.get());
            event.registerFluidType(((BaseFluidType) ModFluidType.EXPERIENCE.get()).getClientFluidTypeExtensions(), ModFluidType.EXPERIENCE.get());
            event.registerFluidType(((BaseFluidType) ModFluidType.STARLIQUID.get()).getClientFluidTypeExtensions(), ModFluidType.STARLIQUID.get());
            event.registerFluidType(((BaseFluidType) ModFluidType.POTATO_JUICE.get()).getClientFluidTypeExtensions(), ModFluidType.POTATO_JUICE.get());
        }
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.TECH_PEDESTAL_BE.get(), context -> new TechPedestalBlockRenderer("tech"));
            event.registerBlockEntityRenderer(ModBlockEntities.MAGIC_PEDESTAL_BE.get(), context -> new MagicPedestalBlockRenderer("magic"));
            event.registerBlockEntityRenderer(ModBlockEntities.DECOR_PEDESTAL_BE.get(), context -> new DecorPedestalBlockRenderer("decor"));
            event.registerBlockEntityRenderer(ModBlockEntities.EXPLORATION_PEDESTAL_BE.get(), context -> new ExplorationPedestalBlockRenderer("exploration"));
            event.registerBlockEntityRenderer(ModBlockEntities.RESOURCE_PEDESTAL_BE.get(), context -> new ResourcePedestalBlockRenderer("resource"));
            event.registerBlockEntityRenderer(ModBlockEntities.CREATIVITY_PEDESTAL_BE.get(), context -> new CreativityPedestalBlockRenderer("creativity"));
        }
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("   §4_____§5_   __§9__________§3_   ______§b_______  __");
        LOGGER.info("  §4/_  _§5/ | / §9/ ____/  _§3/ | / /  _§b/_  __| \\/ /");
        LOGGER.info("   §4/ /§5/  |/ §9/ /_   / /§3/  |/ // /  §b/ /   \\  / ");
        LOGGER.info(" §4_/ /§5/ /|  §9/ __/ _/ /§3/ /|  // /  §b/ /    / /  ");
        LOGGER.info("§4/___§5/_/ |_§9/_/   /___§3/_/ |_/___/ §b/_/    /_/   ");
        LOGGER.info("§b          Infinity Nexus Machines");

    }
}
