package com.Infinity.Nexus.Mod.block;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocksProgression {
        public static final DeferredRegister.Blocks BLOCKS =
                DeferredRegister.createBlocks(InfinityNexusMod.MOD_ID);

    public static final DeferredBlock<Block> SILVER_MACHINE_CASING = registerBlock("silver_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> TIN_MACHINE_CASING = registerBlock("tin_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> LEAD_MACHINE_CASING = registerBlock("lead_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.COLOR_BLACK)));
    public static final DeferredBlock<Block> NICKEL_MACHINE_CASING = registerBlock("nickel_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_BLACK)));
    public static final DeferredBlock<Block> BRASS_MACHINE_CASING = registerBlock("brass_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<Block> BRONZE_MACHINE_CASING = registerBlock("bronze_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_ORANGE)));
    public static final DeferredBlock<Block> STEEL_MACHINE_CASING = registerBlock("steel_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.ANVIL).mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> ALUMINUM_MACHINE_CASING = registerBlock("aluminum_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> PLASTIC_MACHINE_CASING = registerBlock("plastic_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.LODESTONE).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> GLASS_MACHINE_CASING = registerBlock("glass_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(1.0f, 6.0f).sound(SoundType.GLASS).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> INDUSTRIAL_MACHINE_CASING = registerBlock("industrial_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(6.0f, 6.0f).sound(SoundType.LANTERN).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final DeferredBlock<Block> INFINITY_MACHINE_CASING = registerBlock("infinity_machine_casing",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).requiresCorrectToolForDrops().strength(4.0f, 6.0f).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.DIAMOND)));



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
