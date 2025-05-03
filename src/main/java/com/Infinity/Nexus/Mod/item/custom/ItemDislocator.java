package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.component.ModDataComponents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemDislocator extends Item {


    public ItemDislocator(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand ihand) {
        if (!level.isClientSide() && !Screen.hasShiftDown()) {
            ItemStack stack = player.getItemInHand(ihand);
            player.sendSystemMessage(Component.translatable(stack.getOrDefault(ModDataComponents.DISLOCATOR_ONOFRE.get(), false)
                    ? "chat.infinity_nexus_mod.item_dislocator_off"
                    : "chat.infinity_nexus_mod.item_dislocator_on"));
            stack.set(ModDataComponents.DISLOCATOR_ONOFRE.get(), !stack.getOrDefault(ModDataComponents.DISLOCATOR_ONOFRE.get(), false));
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return super.use(level, player, ihand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        if (!level.isClientSide() && entity instanceof Player player && stack.getOrDefault(ModDataComponents.DISLOCATOR_ONOFRE.get(), false)) {
            try {
                List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(entity.getX() + 5, entity.getY() + 5, entity.getZ() + 5, entity.getX() - 5, entity.getY() - 5, entity.getZ() - 5));
                for(ItemEntity item: entities){
                    if (item.isAlive() && !item.hasPickUpDelay()) {
                        item.playerTouch(player);
                    }
                }
            }catch (Exception e){
                System.out.println("\nInfinity Item Dislocator\n"+e+"\n\n");
            }
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.infinity_nexus_mod.item_dislocator"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
            tooltipComponents.add(Component.literal(stack.getOrDefault(ModDataComponents.DISLOCATOR_ONOFRE.get(), false) ? "§aON" : "§cOFF"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
