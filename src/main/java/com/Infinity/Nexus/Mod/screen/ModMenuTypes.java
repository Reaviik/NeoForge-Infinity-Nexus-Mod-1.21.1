package com.Infinity.Nexus.Mod.screen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.screen.assembler.AssemblerMenu;
import com.Infinity.Nexus.Mod.screen.battery.BatteryMenu;
import com.Infinity.Nexus.Mod.screen.condenser.CondenserMenu;
import com.Infinity.Nexus.Mod.screen.crusher.CrusherMenu;
import com.Infinity.Nexus.Mod.screen.factory.FactoryMenu;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelMenu;
import com.Infinity.Nexus.Mod.screen.generator.GeneratorMenu;
import com.Infinity.Nexus.Mod.screen.mobcrusher.MobCrusherMenu;
import com.Infinity.Nexus.Mod.screen.placer.PlacerMenu;
import com.Infinity.Nexus.Mod.screen.press.PressMenu;
import com.Infinity.Nexus.Mod.screen.recycler.RecyclerMenu;
import com.Infinity.Nexus.Mod.screen.smeltery.SmelteryMenu;
import com.Infinity.Nexus.Mod.screen.solar.SolarMenu;
import com.Infinity.Nexus.Mod.screen.squeezer.SqueezerMenu;
import com.Infinity.Nexus.Mod.screen.tank.TankMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, InfinityNexusMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<MobCrusherMenu>> MOB_CRUSHER_MENU =
            registerMenuType("mob_crusher_menu", MobCrusherMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<CrusherMenu>> CRUSHER_MENU =
            registerMenuType("crusher_menu", CrusherMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<PressMenu>> PRESS_MENU =
            registerMenuType("press_menu", PressMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<AssemblerMenu>> ASSEMBLY_MENU =
            registerMenuType("assembler_menu", AssemblerMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<FactoryMenu>> FACTORY_MENU =
            registerMenuType("factory_menu", FactoryMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SqueezerMenu>> SQUEEZER_MENU =
            registerMenuType("squeezer_menu", SqueezerMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SmelteryMenu>> SMELTERY_MENU =
            registerMenuType("smeltery_menu", SmelteryMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<GeneratorMenu>> GENERATOR_MENU =
            registerMenuType("generator_menu", GeneratorMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<FermentationBarrelMenu>> FERMENTATION_BARREL_MENU =
            registerMenuType("fermentation_barre_menu", FermentationBarrelMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<RecyclerMenu>> RECYCLER_MENU =
            registerMenuType("recycler_menu", RecyclerMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<CondenserMenu>> MATTER_CONDENSER_MENU =
            registerMenuType("condenser_menu", CondenserMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<PlacerMenu>> PLACER_MENU =
            registerMenuType("placer_menu", PlacerMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<TankMenu>> TANK_MENU =
            registerMenuType("tank_menu", TankMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<BatteryMenu>> BATTERY_MENU =
            registerMenuType("battery_menu", BatteryMenu::new);


    //Solar
    public static final DeferredHolder<MenuType<?>, MenuType<SolarMenu>> SOLAR_MENU = registerMenuType("solar_menu", SolarMenu::new);


    public static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
