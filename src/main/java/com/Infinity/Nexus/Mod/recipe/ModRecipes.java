package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, InfinityNexusMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, InfinityNexusMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrusherRecipes>> CRUSHER_SERIALIZER = SERIALIZER.register("crushing", CrusherRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PressRecipes>> PRESS_SERIALIZER = SERIALIZER.register("pressing", PressRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AssemblerRecipes>> ASSEMBLY_SERIALIZER = SERIALIZER.register("assembler", AssemblerRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FactoryRecipes>> FACTORY_SERIALIZER = SERIALIZER.register("factory", FactoryRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SqueezerRecipes>> SQUEEZER_SERIALIZER = SERIALIZER.register("squeezing", SqueezerRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SmelteryRecipes>> SMELTRERY_SERIALIZER = SERIALIZER.register("melting", SmelteryRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FermentationBarrelRecipes>> FERMENTATION_SERIALIZER = SERIALIZER.register("fermentation", FermentationBarrelRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MatterCondenserRecipes>> CONDENSER_SERIALIZER = SERIALIZER.register("condensing", MatterCondenserRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<InfuserRecipes>> INFUSER_SERIALIZER = SERIALIZER.register("infuser", InfuserRecipes.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CompactorRecipes>> COMPACTOR_SERIALIZER = SERIALIZER.register("compacting", CompactorRecipes.Serializer::new);


    public static final DeferredHolder<RecipeType<?>, RecipeType<CrusherRecipes>> CRUSHER_RECIPE_TYPE = TYPES.register("crushing", () -> new RecipeType<CrusherRecipes>() {
        @Override
        public String toString() {
            return "crushing";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<PressRecipes>> PRESS_RECIPE_TYPE = TYPES.register("pressing", () -> new RecipeType<PressRecipes>() {
        @Override
        public String toString() {
            return "pressing";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<AssemblerRecipes>> ASSEMBLY_RECIPE_TYPE = TYPES.register("assembler", () -> new RecipeType<AssemblerRecipes>() {
        @Override
        public String toString() {
            return "assembler";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<FactoryRecipes>> FACTORY_RECIPE_TYPE = TYPES.register("factory", () -> new RecipeType<FactoryRecipes>() {
        @Override
        public String toString() {
            return "factory";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<SqueezerRecipes>> SQUEEZER_RECIPE_TYPE = TYPES.register("squeezing", () -> new RecipeType<SqueezerRecipes>() {
        @Override
        public String toString() {
            return "squeezing";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<SmelteryRecipes>> SMELTRERY_RECIPE_TYPE = TYPES.register("melting", () -> new RecipeType<SmelteryRecipes>() {
        @Override
        public String toString() {
            return "melting";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<FermentationBarrelRecipes>> FERMENTATION_RECIPE_TYPE = TYPES.register("fermenting", () -> new RecipeType<FermentationBarrelRecipes>() {
        @Override
        public String toString() {
            return "fermenting";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<MatterCondenserRecipes>> CONDENSER_RECIPE_TYPE = TYPES.register("condensing", () -> new RecipeType<MatterCondenserRecipes>() {
        @Override
        public String toString() {
            return "condensing";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<InfuserRecipes>> INFUSER_RECIPE_TYPE = TYPES.register("infuser", () -> new RecipeType<InfuserRecipes>() {
        @Override
        public String toString() {
            return "infuser";
        }
    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<CompactorRecipes>> COMPACTOR_RECIPE_TYPE = TYPES.register("compacting", () -> new RecipeType<CompactorRecipes>() {
        @Override
        public String toString() {
            return "compacting";
        }
    });

    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
        TYPES.register(eventBus);
    }
}
