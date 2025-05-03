package com.Infinity.Nexus.Mod.component;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Objects;

public record FluidStackComponent(FluidStack fluidStack) {

    public static final Codec<FluidStackComponent> CODEC = FluidStack.CODEC.xmap(
            FluidStackComponent::new,
            FluidStackComponent::fluidStack
    );

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FluidStackComponent that = (FluidStackComponent) o;
        return fluidStack.is(that.fluidStack.getFluid()) && fluidStack.getAmount() == that.fluidStack.getAmount() && fluidStack.getTags().equals(that.fluidStack.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fluidStack.getFluid(), fluidStack.getAmount(), fluidStack.getTags());
    }
}