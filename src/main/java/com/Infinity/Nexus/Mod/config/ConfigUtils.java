package com.Infinity.Nexus.Mod.config;

import java.util.List;

public class ConfigUtils {
    //Cria as Variaveis de Configuração
    //ASSEMBLER
    public static int assembler_energy_storage_capacity;
    public static int assembler_energy_transfer_rate;
    public static int assembler_energy_request;
    public static int assembler_fluid_storage_capacity;
    public static int assembler_minimum_tick;
    //CRUSHER
    public static int crusher_energy_storage_capacity;
    public static int crusher_energy_transfer_rate;
    public static int crusher_energy_request;
    public static int crusher_minimum_tick;
    //FACTORY
    public static int factory_energy_storage_capacity;
    public static int factory_energy_transfer_rate;
    public static int factory_energy_request;
    public static int factory_minimum_tick;
    //GENERATOR
    public static int generator_energy_storage_capacity;
    public static int generator_energy_transfer_rate;
    //MATTER CONDENSER
    public static int matter_condenser_energy_storage_capacity;
    public static int matter_condenser_energy_transfer_rate;
    public static int matter_condenser_energy_request;
    public static int matter_condenser_maximum_catalyst_storage;
    //MINER
    public static int miner_energy_storage_capacity;
    public static int miner_energy_transfer_rate;
    public static int miner_fluid_storage_capacity;
    public static int miner_tier_crystal_chance;
    public static int miner_old_tier_crystal_chance;
    public static int miner_minimum_tick;
    //MOB CRUSHER
    public static int mob_crusher_energy_storage_capacity;
    public static int mob_crusher_energy_transfer_rate;
    public static int mob_crusher_energy_request;
    public static int mob_crusher_fluid_storage_capacity;
    public static int mob_crusher_work_speed;
    //PRESS
    public static int press_energy_storage_capacity;
    public static int press_energy_transfer_rate;
    public static int press_minimum_tick;
    //RECYCLER
    public static int recycler_energy_storage_capacity;
    public static int recycler_energy_transfer_rate;
    public static int recycler_energy_request;
    public static int recycler_minimum_tick;
    //SMELTERY
    public static int smelter_energy_storage_capacity;
    public static int smelter_energy_transfer_rate;
    public static int smelter_energy_request;
    public static int smelter_minimum_tick;
    //SOLAR
    public static int solar_energy_storage_capacity;
    public static int solar_energy_transfer_rate;
    //BATTERY
    public static int battery_energy_storage_capacity;
    public static int battery_energy_transfer_rate;
    //COMPACTOR AUTO
    public static int compactor_auto_energy_storage_capacity;
    public static int compactor_auto_energy_transfer_rate;
    //SQUEEZER
    public static int squeezer_energy_storage_capacity;
    public static int squeezer_energy_transfer_rate;
    public static int squeezer_fluid_storage_capacity;
    public static int squeezer_minimum_tick;
    //DISPLAY
    public static double display_rotation_speed_multiplier;
    public static List<String> list_of_non_placeable_blocks;
    //TANK
    public static int tank_capacity;
    public static boolean tank_can_endless;
    public static boolean blacklist_tank_fluids_toggle;
    public static List<String> blacklist_tank_fluids;
    //TRANSLOCATOR
    public static int translocator_delay;
    public static int translocator_range_limit;
    public static int translocator_skip_progress;

    //public static ItemStack getStrengthUpgrade() {
    //    return new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(AssemblerConfig.list_of_upgrades.get(1)))));
    //}

}
