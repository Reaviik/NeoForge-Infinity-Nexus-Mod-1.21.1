package com.Infinity.Nexus.Mod.tab;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTabProgression {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfinityNexusMod.MOD_ID);
    public static final Supplier<CreativeModeTab> INFINITY_TAB_ADDITIONS = CREATIVE_MODE_TABS.register("infinity_nexus_mod_progression",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.infinity_nexus_mod_addition"))
                    .icon(() -> new ItemStack(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING))
                    .displayItems((pParameters, pOutput) -> {
                        //-------------------------//-------------------------//

                        pOutput.accept(ModBlocksProgression.SILVER_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.TIN_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.LEAD_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.NICKEL_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.BRASS_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.BRONZE_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.ALUMINUM_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.PLASTIC_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.GLASS_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.STEEL_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING);
                        pOutput.accept(ModBlocksProgression.INFINITY_MACHINE_CASING);

                        pOutput.accept(ModItemsProgression.GOLD_WIRE_CAST);
                        pOutput.accept(ModItemsProgression.GOLD_SCREW_CAST);
                        pOutput.accept(ModItemsProgression.GOLD_SHEET_CAST);
                        pOutput.accept(ModItemsProgression.GOLD_ROD_CAST);
                        pOutput.accept(ModItemsProgression.GOLD_INGOT_CAST);
                        pOutput.accept(ModItemsProgression.INFINITY_MESH_CAST);

                        pOutput.accept(ModItemsProgression.RAW_WIRE_CLAY_MODEL);
                        pOutput.accept(ModItemsProgression.RAW_SCREW_CLAY_MODEL);
                        pOutput.accept(ModItemsProgression.RAW_SHEET_CLAY_MODEL);
                        pOutput.accept(ModItemsProgression.RAW_ROD_CLAY_MODEL);
                        pOutput.accept(ModItemsProgression.WIRE_CLAY_MODEL);
                        pOutput.accept(ModItemsProgression.SCREW_CLAY_MODEL);
                        pOutput.accept(ModItemsProgression.SHEET_CLAY_MODEL);
                        pOutput.accept(ModItemsProgression.ROD_CLAY_MODEL);

                        pOutput.accept(ModItemsProgression.COPPER_WIRE);
                        pOutput.accept(ModItemsProgression.LEAD_WIRE);
                        pOutput.accept(ModItemsProgression.IRON_WIRE);
                        pOutput.accept(ModItemsProgression.TIN_WIRE);
                        pOutput.accept(ModItemsProgression.IRON_WIRE);
                        pOutput.accept(ModItemsProgression.GOLD_WIRE);
                        pOutput.accept(ModItemsProgression.SILVER_WIRE);
                        pOutput.accept(ModItemsProgression.NICKEL_WIRE);
                        pOutput.accept(ModItemsProgression.BRASS_WIRE);
                        pOutput.accept(ModItemsProgression.BRONZE_WIRE);
                        pOutput.accept(ModItemsProgression.STEEL_WIRE);
                        pOutput.accept(ModItemsProgression.ALUMINUM_WIRE);
                        pOutput.accept(ModItemsProgression.INDUSTRIAL_WIRE);
                        pOutput.accept(ModItemsProgression.INFINITY_WIRE);

                        pOutput.accept(ModItemsProgression.COPPER_SCREW);
                        pOutput.accept(ModItemsProgression.LEAD_SCREW);
                        pOutput.accept(ModItemsProgression.TIN_SCREW);
                        pOutput.accept(ModItemsProgression.IRON_SCREW);
                        pOutput.accept(ModItemsProgression.GOLD_SCREW);
                        pOutput.accept(ModItemsProgression.SILVER_SCREW);
                        pOutput.accept(ModItemsProgression.NICKEL_SCREW);
                        pOutput.accept(ModItemsProgression.BRASS_SCREW);
                        pOutput.accept(ModItemsProgression.BRONZE_SCREW);
                        pOutput.accept(ModItemsProgression.STEEL_SCREW);
                        pOutput.accept(ModItemsProgression.ALUMINUM_SCREW);
                        pOutput.accept(ModItemsProgression.INDUSTRIAL_SCREW);
                        pOutput.accept(ModItemsProgression.INFINITY_SCREW);

                        pOutput.accept(ModItemsProgression.COPPER_ROD);
                        pOutput.accept(ModItemsProgression.LEAD_ROD);
                        pOutput.accept(ModItemsProgression.TIN_ROD);
                        pOutput.accept(ModItemsProgression.IRON_ROD);
                        pOutput.accept(ModItemsProgression.GOLD_ROD);
                        pOutput.accept(ModItemsProgression.SILVER_ROD);
                        pOutput.accept(ModItemsProgression.NICKEL_ROD);
                        pOutput.accept(ModItemsProgression.BRASS_ROD);
                        pOutput.accept(ModItemsProgression.BRONZE_ROD);
                        pOutput.accept(ModItemsProgression.STEEL_ROD);
                        pOutput.accept(ModItemsProgression.ALUMINUM_ROD);
                        pOutput.accept(ModItemsProgression.INDUSTRIAL_ROD);
                        pOutput.accept(ModItemsProgression.INFINITY_ROD);

                        pOutput.accept(ModItemsProgression.COPPER_SHEET);
                        pOutput.accept(ModItemsProgression.LEAD_SHEET);
                        pOutput.accept(ModItemsProgression.TIN_SHEET);
                        pOutput.accept(ModItemsProgression.IRON_SHEET);
                        pOutput.accept(ModItemsProgression.GOLD_SHEET);
                        pOutput.accept(ModItemsProgression.SILVER_SHEET);
                        pOutput.accept(ModItemsProgression.NICKEL_SHEET);
                        pOutput.accept(ModItemsProgression.BRASS_SHEET);
                        pOutput.accept(ModItemsProgression.BRONZE_SHEET);
                        pOutput.accept(ModItemsProgression.STEEL_SHEET);
                        pOutput.accept(ModItemsProgression.ALUMINUM_SHEET);
                        pOutput.accept(ModItemsProgression.INDUSTRIAL_SHEET);
                        pOutput.accept(ModItemsProgression.INFINITY_SHEET);
                        pOutput.accept(ModItemsProgression.IRIDIUM_MESH);

                        pOutput.accept(ModItemsProgression.VOXEL_TOP);
                        pOutput.accept(ModItemsProgression.VOXEL_DOW);
                        pOutput.accept(ModItemsProgression.VOXEL_NORTH);
                        pOutput.accept(ModItemsProgression.VOXEL_SOUTH);
                        pOutput.accept(ModItemsProgression.VOXEL_WEST);
                        pOutput.accept(ModItemsProgression.VOXEL_EAST);
                        pOutput.accept(ModBlocksAdditions.VOXEL_BLOCK);


                        //-------------------------//-------------------------//
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
