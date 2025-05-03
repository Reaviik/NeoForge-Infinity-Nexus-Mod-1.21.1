package com.Infinity.Nexus.Mod.entity;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.entity.custom.Asgreon;
import com.Infinity.Nexus.Mod.entity.custom.Flaron;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, InfinityNexusMod.MOD_ID);

    public static final Supplier<EntityType<Asgreon>> ASGREON = ENTITY_TYPES.register("asgreon",
            () -> EntityType.Builder.of(Asgreon::new, MobCategory.CREATURE).sized(0.8f, 1.7f).build("asgreon"));
    public static final Supplier<EntityType<Flaron>> FLARON = ENTITY_TYPES.register("flaron",
            () -> EntityType.Builder.of(Flaron::new, MobCategory.CREATURE).sized(0.8f, 1.7f).build("flaron"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
