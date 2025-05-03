package com.Infinity.Nexus.Mod.component;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, InfinityNexusMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HAMMER_RANGE = register("hammer_range",  op -> op.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> DISLOCATOR_ONOFRE = register("dislocator_onofre", op -> op.persistent(Codec.BOOL));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> TRANSLOCATOR_TYPE = register("translocator_type", op -> op.persistent(Codec.BOOL));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> FRACTAL_TYPE = register("fractal_type", op -> op.persistent(Codec.STRING));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<BlockPos>>> TRANSLOCATOR_COORDS =
            register("translocator_coords", op -> op.persistent(
                    Codec.list(BlockPos.CODEC).xmap(
                            ImmutableList::copyOf,
                            ImmutableList::copyOf
                    )
            ));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemStackComponent>> ITEM_STACK = register("item_stack", op -> op.persistent(ItemStackComponent.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY = register("energy",  op -> op.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FluidStackComponent>> TANK_FLUID = register("tank_fluid", op -> op.persistent(FluidStackComponent.CODEC));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}