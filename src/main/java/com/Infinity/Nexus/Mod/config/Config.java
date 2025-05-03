package com.Infinity.Nexus.Mod.config;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

@EventBusSubscriber(modid = InfinityNexusMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    //Instancia a Configuração
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    //ASSEMBLER CONFIGURATION
    private static final ModConfigSpec.IntValue ASSEMBLER_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia a Montadora pode armazenar").defineInRange("assembler_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue ASSEMBLER_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia a Montadora pode receber por tick").defineInRange("assembler_energy_transfer", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue ASSEMBLER_ENERGY_REQUEST  = BUILDER.comment("Define a quantidade de energia a Montadora vai gastar por estagio da receita").defineInRange("assembler_energy_request", 32, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue ASSEMBLER_FLUID_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de liquido a Montadora pode armazenar").defineInRange("assembler_fluid_capacity", 10000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue ASSEMBLER_MINIMUM_TICK  = BUILDER.comment("Define a menor velocidade que a Montadora pode trabalhar").defineInRange("assembler_minimum_tick", 1, 1, Integer.MAX_VALUE);

    //CRUSHER CONFIGURATION
    private static final ModConfigSpec.IntValue CRUSHER_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia o Triturador pode armazenar").defineInRange("crusher_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue CRUSHER_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia o Triturador pode receber por tick").defineInRange("crusher_energy_transfer", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue CRUSHER_ENERGY_REQUEST  = BUILDER.comment("Define a quantidade de energia o Triturador vai gastar por estagio da receita").defineInRange("crusher_energy_request", 32, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue CRUSHER_MINIMUM_TICK  = BUILDER.comment("Define a menor velocidade que o Triturador pode trabalhar").defineInRange("crusher_minimum_tick", 1, 1, Integer.MAX_VALUE);
    //FACTORY
    private static final ModConfigSpec.IntValue FACTORY_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia a Factory pode armazenar").defineInRange("factory_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue FACTORY_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia a Factory pode receber por tick").defineInRange("factory_energy_transfer", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue FACTORY_ENERGY_REQUEST  = BUILDER.comment("Define a quantidade de energia a Factory vai gastar por estagio da receita").defineInRange("factory_energy_request", 32, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue FACTORY_MINIMUM_TICK  = BUILDER.comment("Define a menor velocidade que a Factory pode trabalhar").defineInRange("factory_minimum_tick", 1, 1, Integer.MAX_VALUE);
    //GENERATOR
    private static final ModConfigSpec.IntValue GENERATOR_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia o Gerador pode armazenar").defineInRange("generator_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue GENERATOR_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia o Gerador pode receber por tick").defineInRange("generator_energy_transfer", 600000, 1, Integer.MAX_VALUE);
    //MATTER CONDENSER
    private static final ModConfigSpec.IntValue MATTER_CONDENSER_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia o Condensador de Máteria pode armazenar").defineInRange("matter_condenser_energy_capacity", 2147483640, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MATTER_CONDENSER_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia o Condensador de Máteria pode receber por tick").defineInRange("matter_condenser_energy_transfer", 214748364, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MATTER_CONDENSER_ENERGY_REQUEST  = BUILDER.comment("Define a quantidade de energia o Condensador de Máteria vai gastar por estagio da receita").defineInRange("matter_condenser_energy_request", 1, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MATTER_CONDENSER_RESIDUAL_MATTER_STORAGE  = BUILDER.comment("Define a quantidade de Materia Residual que o Condensador de Máteria pode armazenar").defineInRange("matter_condenser_maximum_catalyst_storage", 500000, 1, Integer.MAX_VALUE);
    //MINER
    private static final ModConfigSpec.IntValue MINER_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia a Mineradora pode armazenar").defineInRange("miner_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MINER_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia a Mineradora pode receber por tick").defineInRange("miner_energy_transfer", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MINER_FLUID_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de experiencia a Mineradora pode armazenar").defineInRange("miner_experience_capacity", 10000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MINER_TIER_CRYSTAL_CHANCE  = BUILDER.comment("1% sobre este valor vezes o nivel da Mineradora, define a porcentagem de chances de gerar o cristal do próximo level").defineInRange("miner_tier_crystal_chance", 100, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MINER_OLD_TIER_CRYSTAL_CHANCE  = BUILDER.comment("O minel da Mineradora vezes este valor").defineInRange("miner_old_tier_crystal_chance", 100, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MINER_MINIMUM_TICK  = BUILDER.comment("Define a menor velocidade que a Mineradora pode trabalhar").defineInRange("miner_minimum_tick", 1, 1, Integer.MAX_VALUE);
    //MOB CRUSHER
    private static final ModConfigSpec.IntValue MOB_CRUSHER_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia o Matador de Mobs pode armazenar").defineInRange("mob_crusher_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MOB_CRUSHER_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia o Matador de Mobs pode receber por tick").defineInRange("mob_crusher_energy_transfer", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MOB_CRUSHER_ENERGY_REQUEST  = BUILDER.comment("Define a quantidade de energia o Matador de Mobs vai gastar por estagio da receita").defineInRange("mob_crusher_energy_require", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MOB_CRUSHER_FLUID_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de experiencia o Matador de Mobs pode armazenar").defineInRange("mob_crusher_experience_capacity", 10000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MOB_CRUSHER_WORK_SPEED  = BUILDER.comment("Define a velocidade que o Matador de Mobs pode trabalhar em tick").defineInRange("mob_crusher_work_slow", 20, 1, Integer.MAX_VALUE);
    //PRESS
    private static final ModConfigSpec.IntValue PRESS_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia a Prensa pode armazenar").defineInRange("press_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue PRESS_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia a Prensa pode receber por tick").defineInRange("press_energy_transfer", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue PRESS_MINIMUM_TICK  = BUILDER.comment("Define a menor velocidade que a Prensa pode trabalhar").defineInRange("press_minimum_tick", 1, 1, Integer.MAX_VALUE);
    //RECYCLER
    private static final ModConfigSpec.IntValue RECYCLER_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia o Reciclador pode armazenar").defineInRange("recycler_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue RECYCLER_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia o Reciclador pode receber por tick").defineInRange("recycler_energy_transfer", 320000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue RECYCLER_ENERGY_REQUEST  = BUILDER.comment("Define a quantidade de energia o Reciclador vai gastar por estagio da receita").defineInRange("recycler_energy_require", 1500, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue RECYCLER_MINIMUM_TICK  = BUILDER.comment("Define a menor velocidade que o Reciclador pode trabalhar").defineInRange("recycler_minimum_tick", 1, 1, Integer.MAX_VALUE);
    //SMELTER
    private static final ModConfigSpec.IntValue SMELTER_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia a Fundidora pode armazenar").defineInRange("smelter_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SMELTER_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia a Fundidora pode receber por tick").defineInRange("smelter_energy_transfer", 320000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SMELTER_ENERGY_REQUEST  = BUILDER.comment("Define a quantidade de energia a Fundidora vai gastar por estagio da receita").defineInRange("smelter_energy_require", 32, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SMELTER_MINIMUM_TICK  = BUILDER.comment("Define a menor velocidade que a Fundidora pode trabalhar").defineInRange("smelter_minimum_tick", 1, 1, Integer.MAX_VALUE);
    //SOLAR
    private static final ModConfigSpec.IntValue SOLAR_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia o Painel Solar pode armazenar").defineInRange("solar_energy_capacity", 64000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SOLAR_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia o Painel Solar pode receber por tick").defineInRange("solar_energy_transfer", 512000, 1, Integer.MAX_VALUE);
    //BATTERY
    private static final ModConfigSpec.IntValue BATTERY_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia a Bateria pode armazenar").defineInRange("battery_energy_capacity", 540000000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue BATTERY_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia a Bateria pode receber por tick").defineInRange("battery_energy_transfer", 512000, 1, Integer.MAX_VALUE);
    //SQUEEZER
    private static final ModConfigSpec.IntValue SQUEEZER_ENERGY_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de energia o Espremedor pode armazenar").defineInRange("squeezer_energy_capacity", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SQUEEZER_ENERGY_TRANSFER_RATE  = BUILDER.comment("Define a quantidade de energia o Espremedor pode receber por tick").defineInRange("squeezer_energy_transfer", 600000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SQUEEZER_FLUID_STORAGE_CAPACITY  = BUILDER.comment("Define a quantidade de liquido o Espremedor pode armazenar").defineInRange("squeezer_fluid_capacity", 10000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SQUEEZER_MINIMUM_TICK  = BUILDER.comment("Define a menor velocidade que o Espremedor pode trabalhar").defineInRange("squeezer_minimum_tick", 1, 1, Integer.MAX_VALUE);
    //PLACER
    private static final ModConfigSpec.ConfigValue<List<String>> LIST_OF_NON_PLACEABLE_BLOCKS = BUILDER
            .comment("Lista de Itens que o Placer não pode colocar no chão.")
            .define("list_of_non_placeable_blocks", List.of(
                    "minecraft:redstone"
            ));
    //DISPLAY
    private static final ModConfigSpec.ConfigValue<Double> DISPLAY_ROTATION_SPEED_MULTIPLIER = BUILDER.comment("Define o multiplicador da velocidade de rotacao dos itens no display").define("display_rotation_speed_multiplier", 0.05D);
    //Tank
    private static final ModConfigSpec.IntValue TANK_CAPACITY = BUILDER.comment("Define a quantidade de itens que o tanque pode armazenar").defineInRange("tank_capacity", 16000000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue TANK_CAN_ENDLESS = BUILDER.comment("Define se o tanque pode ficar com liquido infinito quando cheio").define("tank_can_endless", true);
    private static final ModConfigSpec.BooleanValue BLACKLIST_TANK_FLUIDS_TOGGLE = BUILDER.comment("False = blacklist, True = whitelist").define("blacklist_tank_fluids_toggle", false);
    private static final ModConfigSpec.ConfigValue<List<String>> BLACKLIST_TANK_FLUIDS = BUILDER
            .comment("Lista de liquidos que o tanque nao pode tornar infinito.")
            .define("blacklist_tank_fluids", List.of(
                    "infinity_nexus_mod:sugarcane_juice_water_fluid",
                    "infinity_nexus_mod:wine_water_fluid",
                    "infinity_nexus_mod:potato_juice_water_fluid",
                    "infinity_nexus_mod:vinegar_water_fluid"
            ));
    private static final ModConfigSpec.IntValue TRANSLOCATOR_DELAY = BUILDER.comment("Define a quantidade de ticks entre uma operação e outra").defineInRange("translator_delay", 20, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue TRANSLOCATOR_RANGE_LIMIT = BUILDER.comment("Define a distancia maxima que o translator pode enviar").defineInRange("translator_range_limit", 100, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue TRANSLOCATOR_SKIP_PROGRESS = BUILDER.comment("Define a quantidade de ticks ignorados quando recebe um item de outro translocador").defineInRange("translator_skip_progress", 15, 1, Integer.MAX_VALUE);

    //Builda o Arquivo
    public static final ModConfigSpec SPEC = BUILDER.build();



    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        //Carrega as variaveis de Configuração para a memoria para uso posterior
        //ASSEMBLER
        ConfigUtils.assembler_energy_storage_capacity = ASSEMBLER_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.assembler_energy_transfer_rate = ASSEMBLER_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.assembler_energy_request = ASSEMBLER_ENERGY_REQUEST.get();
        ConfigUtils.assembler_fluid_storage_capacity = ASSEMBLER_FLUID_STORAGE_CAPACITY.get();
        ConfigUtils.assembler_minimum_tick = ASSEMBLER_MINIMUM_TICK.get();
        //CRUSHER
        ConfigUtils.crusher_energy_storage_capacity = CRUSHER_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.crusher_energy_transfer_rate = CRUSHER_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.crusher_energy_request = CRUSHER_ENERGY_REQUEST.get();
        ConfigUtils.crusher_minimum_tick = CRUSHER_MINIMUM_TICK.get();
        //FACTORY
        ConfigUtils.factory_energy_storage_capacity = FACTORY_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.factory_energy_transfer_rate = FACTORY_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.factory_energy_request = FACTORY_ENERGY_REQUEST.get();
        ConfigUtils.factory_minimum_tick = FACTORY_MINIMUM_TICK.get();
        //GENERATOR
        ConfigUtils.generator_energy_storage_capacity = GENERATOR_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.generator_energy_transfer_rate = GENERATOR_ENERGY_TRANSFER_RATE.get();
        //MATTER CONDENSER
        ConfigUtils.matter_condenser_energy_storage_capacity = MATTER_CONDENSER_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.matter_condenser_energy_transfer_rate = MATTER_CONDENSER_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.matter_condenser_energy_request = MATTER_CONDENSER_ENERGY_REQUEST.get();
        ConfigUtils.matter_condenser_maximum_catalyst_storage = MATTER_CONDENSER_RESIDUAL_MATTER_STORAGE.get();
        //MINER
        ConfigUtils.miner_energy_storage_capacity = MINER_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.miner_energy_transfer_rate = MINER_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.miner_fluid_storage_capacity = MINER_FLUID_STORAGE_CAPACITY.get();
        ConfigUtils.miner_tier_crystal_chance = MINER_TIER_CRYSTAL_CHANCE.get();
        ConfigUtils.miner_old_tier_crystal_chance = MINER_OLD_TIER_CRYSTAL_CHANCE.get();
        ConfigUtils.miner_minimum_tick = MINER_MINIMUM_TICK.get();
        //MOB CRUSHER
        ConfigUtils.mob_crusher_energy_storage_capacity = MOB_CRUSHER_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.mob_crusher_energy_transfer_rate = MOB_CRUSHER_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.mob_crusher_energy_request = MOB_CRUSHER_ENERGY_REQUEST.get();
        ConfigUtils.mob_crusher_fluid_storage_capacity = MOB_CRUSHER_FLUID_STORAGE_CAPACITY.get();
        ConfigUtils.mob_crusher_work_speed = MOB_CRUSHER_WORK_SPEED.get();
        //PRESS
        ConfigUtils.press_energy_storage_capacity = PRESS_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.press_energy_transfer_rate = PRESS_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.press_minimum_tick = PRESS_MINIMUM_TICK.get();
        //RECYCLER
        ConfigUtils.recycler_energy_storage_capacity = RECYCLER_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.recycler_energy_transfer_rate = RECYCLER_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.recycler_energy_request = RECYCLER_ENERGY_REQUEST.get();
        ConfigUtils.recycler_minimum_tick = RECYCLER_MINIMUM_TICK.get();
        //SMELTER
        ConfigUtils.smelter_energy_storage_capacity = SMELTER_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.smelter_energy_transfer_rate = SMELTER_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.smelter_energy_request = SMELTER_ENERGY_REQUEST.get();
        ConfigUtils.smelter_minimum_tick = SMELTER_MINIMUM_TICK.get();
        //SOLAR
        ConfigUtils.solar_energy_storage_capacity = SOLAR_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.solar_energy_transfer_rate = SOLAR_ENERGY_TRANSFER_RATE.get();
        //BATTERY
        ConfigUtils.battery_energy_storage_capacity = BATTERY_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.battery_energy_transfer_rate = BATTERY_ENERGY_TRANSFER_RATE.get();
        //SQUEEZER
        ConfigUtils.squeezer_energy_storage_capacity = SQUEEZER_ENERGY_STORAGE_CAPACITY.get();
        ConfigUtils.squeezer_energy_transfer_rate = SQUEEZER_ENERGY_TRANSFER_RATE.get();
        ConfigUtils.squeezer_fluid_storage_capacity = SQUEEZER_FLUID_STORAGE_CAPACITY.get();
        ConfigUtils.squeezer_minimum_tick = SQUEEZER_MINIMUM_TICK.get();
        //PLACER
        ConfigUtils.list_of_non_placeable_blocks = LIST_OF_NON_PLACEABLE_BLOCKS.get();
        //DISPLAY
        ConfigUtils.display_rotation_speed_multiplier = DISPLAY_ROTATION_SPEED_MULTIPLIER.get();
        //TANK
        ConfigUtils.tank_capacity = TANK_CAPACITY.get();
        ConfigUtils.tank_can_endless = TANK_CAN_ENDLESS.get();
        ConfigUtils.blacklist_tank_fluids_toggle = BLACKLIST_TANK_FLUIDS_TOGGLE.get();
        ConfigUtils.blacklist_tank_fluids = BLACKLIST_TANK_FLUIDS.get();
        //TRANSLOCATOR
        ConfigUtils.translocator_delay = TRANSLOCATOR_DELAY.get();
        ConfigUtils.translocator_range_limit = TRANSLOCATOR_RANGE_LIMIT.get();
        ConfigUtils.translocator_skip_progress = TRANSLOCATOR_SKIP_PROGRESS.get();
    }
}
