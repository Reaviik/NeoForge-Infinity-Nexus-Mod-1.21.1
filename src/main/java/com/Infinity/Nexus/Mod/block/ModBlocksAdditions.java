package com.Infinity.Nexus.Mod.block;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.custom.*;
import com.Infinity.Nexus.Mod.block.custom.pedestals.*;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocksAdditions {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(InfinityNexusMod.MOD_ID);

    public static final DeferredBlock<Block> INFINIUM_STELLARUM_BLOCK = registerBlock("infinium_stellarum_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.DIAMOND)));
    public static final DeferredBlock<Block> INFINITY_BLOCK = registerBlock("infinity_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.DIAMOND)));
    public static final DeferredBlock<Block> LEAD_BLOCK = registerBlock("lead_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.LAPIS)));
    public static final DeferredBlock<Block> ALUMINUM_BLOCK = registerBlock("aluminum_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> NICKEL_BLOCK = registerBlock("nickel_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_YELLOW)));
    public static final DeferredBlock<Block> ZINC_BLOCK = registerBlock("zinc_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> TIN_BLOCK = registerBlock("tin_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> BRASS_BLOCK = registerBlock("brass_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<Block> BRONZE_BLOCK = registerBlock("bronze_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<Block> STEEL_BLOCK = registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_BLACK)));
    public static final DeferredBlock<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.COPPER).mapColor(MapColor.RAW_IRON)));


    public static final DeferredBlock<Block> RAW_INFINITY_BLOCK = registerBlock("raw_infinity_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> RAW_LEAD_BLOCK = registerBlock("raw_lead_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> RAW_ALUMINUM_BLOCK = registerBlock("raw_aluminum_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> RAW_NICKEL_BLOCK = registerBlock("raw_nickel_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> RAW_ZINC_BLOCK = registerBlock("raw_zinc_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> RAW_URANIUM_BLOCK = registerBlock("raw_uranium_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.RAW_IRON)));



    public static final DeferredBlock<Block> INFINITY_ORE = registerBlock("infinity_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> LEAD_ORE = registerBlock("lead_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> ALUMINUM_ORE = registerBlock("aluminum_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> NICKEL_ORE = registerBlock("nickel_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> ZINC_ORE = registerBlock("zinc_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> SILVER_ORE = registerBlock("silver_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> TIN_ORE = registerBlock("tin_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> URANIUM_ORE = registerBlock("uranium_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));

    public static final DeferredBlock<Block> DEEPSLATE_INFINITY_ORE = registerBlock("deepslate_infinity_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final DeferredBlock<Block> DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final DeferredBlock<Block> DEEPSLATE_ALUMINUM_ORE = registerBlock("deepslate_aluminum_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final DeferredBlock<Block> DEEPSLATE_NICKEL_ORE = registerBlock("deepslate_nickel_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final DeferredBlock<Block> DEEPSLATE_ZINC_ORE = registerBlock("deepslate_zinc_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final DeferredBlock<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));
    public static final DeferredBlock<Block> DEEPSLATE_URANIUM_ORE = registerBlock("deepslate_uranium_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.NETHER_ORE).mapColor(MapColor.DEEPSLATE)));

    public static final DeferredBlock<Block> EXPLORAR_PORTAL_FRAME = registerBlock("explorar_portal_frame", () -> new PortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.POLISHED_DEEPSLATE).mapColor(MapColor.QUARTZ)));
    public static final DeferredBlock<Block> EXPLORAR_PORTAL = registerBlock("explorar_portal", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.SPORE_BLOSSOM)));
    public static final DeferredBlock<Block> VOXEL_BLOCK = registerBlock("voxel_cube", () -> new Voxel(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).strength(3.0f, 6.0f).sound(SoundType.FROGLIGHT).mapColor(MapColor.ICE)));
    public static final DeferredBlock<Block> ENTITY_CENTRALIZER = registerBlock("entity_centralizer", () -> new EntityCentralizer(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET).strength(1.0f, 1.0f).sound(SoundType.FROGLIGHT).noCollission().mapColor(MapColor.METAL)));

    public static final DeferredBlock<Block> ASPHALT = registerBlock("asphalt", () -> new Asphalt(BlockBehaviour.Properties.ofFullCopy(Blocks.LADDER).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.POLISHED_DEEPSLATE).mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> MOB_CRUSHER = registerBlock("mob_crusher", () -> new MobCrusher(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> CRUSHER = registerBlock("crusher", () -> new Crusher(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Crusher.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> PRESS = registerBlock("press", () -> new Press(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Press.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> ASSEMBLY = registerBlock("assembler", () -> new Assembler(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Assembler.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> FACTORY = registerBlock("factory", () -> new Factory(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Factory.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> INFUSER = registerBlock("infuser", () -> new Infuser(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Infuser.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> SQUEEZER = registerBlock("squeezer", () -> new Squeezer(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Squeezer.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> SMELTERY = registerBlock("smeltery", () -> new Smeltery(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Smeltery.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> GENERATOR = registerBlock("generator", () -> new Generator(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Generator.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> RECYCLER = registerBlock("recycler", () -> new Recycler(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Recycler.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> MATTER_CONDENSER = registerBlock("matter_condenser", () -> new MatterCondenser(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(MatterCondenser.LIT) >= 8 ? 2 : 1).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> COMPACTOR = registerBlock("compactor", () -> new Compactor(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Compactor.LIT) == 1 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> TRANSLOCATOR_ITEM = registerBlock("translocator", () -> new TranslocatorItem(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(TranslocatorItem.LIT) * 2).forceSolidOn().noOcclusion().mapColor(MapColor.METAL).noCollission()));
    public static final DeferredBlock<Block> TRANSLOCATOR_ENERGY = registerBlock("translocator_energy", () -> new TranslocatorEnergy(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(TranslocatorItem.LIT) * 2).forceSolidOn().noOcclusion().mapColor(MapColor.METAL).noCollission()));
    public static final DeferredBlock<Block> TRANSLOCATOR_FLUID = registerBlock("translocator_fluid", () -> new TranslocatorFluid(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(TranslocatorItem.LIT) * 2).forceSolidOn().noOcclusion().mapColor(MapColor.METAL).noCollission()));
    public static final DeferredBlock<Block> PLACER = registerBlock("placer", () -> new Placer(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> DISPLAY = registerBlock("display", () -> new ItemDisplay(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.STONE)));
    public static final DeferredBlock<Block> ENTITY_DISPLAY = registerBlock("entity_display", () -> new EntityDisplay(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> 1).noOcclusion().noCollission().mapColor(MapColor.WOOD)));
    public static final DeferredBlock<Block> TANK = registerBlock("tank", () -> new Tank(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Tank.LIT) == 1 ? 5 : 1).noLootTable().noOcclusion().mapColor(MapColor.TERRACOTTA_BLACK)));
    public static final DeferredBlock<Block> BATTERY = registerBlock("battery", () -> new Battery(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Battery.LIT) == 8 ? 2 : 0).noLootTable().noOcclusion().mapColor(MapColor.TERRACOTTA_BLACK)));

    public static final DeferredBlock<Block> TECH_PEDESTAL = registerBlock("tech_pedestal", () -> new TechPedestal(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final DeferredBlock<Block> MAGIC_PEDESTAL = registerBlock("magic_pedestal", () -> new MagicPedestal(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final DeferredBlock<Block> CREATIVITY_PEDESTAL = registerBlock("creativity_pedestal", () -> new CreativityPedestal(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final DeferredBlock<Block> DECOR_PEDESTAL = registerBlock("decor_pedestal", () -> new DecorPedestal(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final DeferredBlock<Block> EXPLORATION_PEDESTAL = registerBlock("exploration_pedestal", () -> new ExplorationPedestal(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final DeferredBlock<Block> RESOURCE_PEDESTAL = registerBlock("resource_pedestal", () -> new ResourcePedestal(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));

    public static final DeferredBlock<Block> SOLAR = registerBlock("solar", () -> new Solar(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(1.0f, 6.0f).lightLevel((state) -> state.getValue(Solar.LIT) > 0 ? 2 : 0).noLootTable().noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> FERMENTATION_BARREL = registerBlock("fermentation_barrel", () -> new FermentationBarrel(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).sound(SoundType.WOOD).strength(1.0f, 6.0f).noOcclusion().mapColor(MapColor.WOOD)));

    public static final DeferredBlock<LiquidBlock> LUBRICANT = BLOCKS.register("lubricant", () -> new LiquidBlock(ModFluids.LUBRICANT_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<LiquidBlock> ETHANOL = BLOCKS.register("ethanol", () -> new LiquidBlock(ModFluids.ETHANOL_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<LiquidBlock> OIL = BLOCKS.register("oil", () -> new LiquidBlock(ModFluids.OIL_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_BLACK)));
    public static final DeferredBlock<LiquidBlock> VINEGAR = BLOCKS.register("vinegar", () -> new LiquidBlock(ModFluids.VINEGAR_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<LiquidBlock> SUGARCANE_JUICE = BLOCKS.register("sugarcane_juice", () -> new LiquidBlock(ModFluids.SUGARCANE_JUICE_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<LiquidBlock> WINE = BLOCKS.register("wine", () -> new LiquidBlock(ModFluids.WINE_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<LiquidBlock> EXPERIENCE = BLOCKS.register("experience", () -> new LiquidBlock(ModFluids.EXPERIENCE_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_GREEN)));
    public static final DeferredBlock<LiquidBlock> STARLIQUID = BLOCKS.register("starliquid", () -> new LiquidBlock(ModFluids.STARLIQUID_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<LiquidBlock> POTATO_JUICE = BLOCKS.register("potato_juice", () -> new LiquidBlock(ModFluids.POTATO_JUICE_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().mapColor(MapColor.COLOR_BROWN)));

    public static final DeferredBlock<Block> CATWALK = registerBlock("catwalk", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),1));
    public static final DeferredBlock<Block> CATWALK_2 = registerBlock("catwalk_2", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),2));
    public static final DeferredBlock<Block> CATWALK_3 = registerBlock("catwalk_3", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),3));
    public static final DeferredBlock<Block> CATWALK_4 = registerBlock("catwalk_4", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),4));
    public static final DeferredBlock<Block> CATWALK_5 = registerBlock("catwalk_5", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),5));
    public static final DeferredBlock<Block> CATWALK_6 = registerBlock("catwalk_6", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),6));
    public static final DeferredBlock<Block> CATWALK_7 = registerBlock("catwalk_7", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),7));
    public static final DeferredBlock<Block> CATWALK_8 = registerBlock("catwalk_8", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),8));
    public static final DeferredBlock<Block> CATWALK_9 = registerBlock("catwalk_9", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),9));
    public static final DeferredBlock<Block> CATWALK_10 = registerBlock("catwalk_10", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),10));
    public static final DeferredBlock<Block> CATWALK_11 = registerBlock("catwalk_11", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),11));
    public static final DeferredBlock<Block> CATWALK_12 = registerBlock("catwalk_12", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),12));
    public static final DeferredBlock<Block> CATWALK_13 = registerBlock("catwalk_13", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),13));
    public static final DeferredBlock<Block> CATWALK_14 = registerBlock("catwalk_14", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),14));
    public static final DeferredBlock<Block> CATWALK_15 = registerBlock("catwalk_15", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),15));
    public static final DeferredBlock<Block> CATWALK_16 = registerBlock("catwalk_16", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),16));
    public static final DeferredBlock<Block> CATWALK_17 = registerBlock("catwalk_17", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),17));
    public static final DeferredBlock<Block> CATWALK_18 = registerBlock("catwalk_18", () -> new Catwalk(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.RAW_IRON),18));

    public static final DeferredBlock<Block> DEPOT = registerBlock("depot", () -> new Depot(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.WOOD), "item.infinity_nexus.depot_description"));
    public static final DeferredBlock<Block> DEPOT_STONE = registerBlock("depot_stone", () -> new Depot_Stone(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS).requiresCorrectToolForDrops().strength(3.0f, 6.0f).noOcclusion().mapColor(MapColor.STONE), "item.infinity_nexus.depot_stone_description"));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItemsAdditions.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
