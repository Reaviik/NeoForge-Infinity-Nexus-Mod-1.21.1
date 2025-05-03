package com.Infinity.Nexus.Mod.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.stream.Collectors;

public record ItemStackComponent(ItemStack itemStack) {

    public static final Codec<ItemStackComponent> CODEC = ItemStack.CODEC.xmap(
            ItemStackComponent::new,
            ItemStackComponent::itemStack
    );

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStackComponent that = (ItemStackComponent) o;
        return ItemStack.matches(this.itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        int componentsHash = itemStack.getComponents().hashCode();
        int tagsHash = itemStack.getTags()
                .map(TagKey::location)
                .collect(Collectors.toSet())
                .hashCode();

        return Objects.hash(
                BuiltInRegistries.ITEM.getKey(itemStack.getItem()),
                itemStack.getCount(),
                componentsHash,
                tagsHash
        );
    }
}