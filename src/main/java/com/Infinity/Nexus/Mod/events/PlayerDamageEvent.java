package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Set;

@EventBusSubscriber(modid = InfinityNexusMod.MOD_ID)
public class PlayerDamageEvent {
    private static final int BOOTS_SLOT = 0;
    private static final int LEGGINGS_SLOT = 1;
    private static final int CHESTPLATE_SLOT = 2;
    private static final int HELMET_SLOT = 3;

    @SubscribeEvent
    public static void onPlayerHurt(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        try {
            handlePlayerArmorEffects(event, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handlePlayerArmorEffects(LivingIncomingDamageEvent event, Player player) {
        if (handleImperialArmor(event, player)) return;
        if (handleCarbonArmor(event, player)) return;
    }

    private static boolean handleImperialArmor(LivingIncomingDamageEvent event, Player player) {
        if (!hasFullImperialArmor(player)) {
            return false;
        }

        Entity attacker = event.getSource().getEntity();

        if (attacker instanceof Player enemy) {
            handlePlayerAttacker(event, enemy);
            return true;
        }

        if (attacker instanceof Mob mob) {
            handleMobAttacker(event, player, mob);
        }

        event.setCanceled(true);
        return true;
    }
    private static void handlePlayerAttacker(LivingIncomingDamageEvent event, Player enemy) {
        ItemStack weapon = enemy.getMainHandItem();

        if (!isCorrectWeapon(weapon)) {
            sendInvalidWeaponMessage(enemy);
            event.setCanceled(true);
            return;
        }

        applyImperialDamageReduction(event);
    }

    private static void sendInvalidWeaponMessage(Player enemy) {
        enemy.sendSystemMessage(Component.literal(InfinityNexusMod.message + "§cYou cannot attack other players with full Imperial Armor with this weapon!"));
        enemy.setSwimming(true);
        enemy.playNotifySound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    private static void applyImperialDamageReduction(LivingIncomingDamageEvent event) {
        float damage = event.getAmount();
        float newDamage = (damage / 100) * 10;
        event.setAmount(newDamage);
    }

    private static void handleMobAttacker(LivingIncomingDamageEvent event, Player target, Mob mob) {
        if (target instanceof ServerPlayer serverPlayer) {
            serverPlayer.attack(mob);
        }
    }

    private static boolean handleCarbonArmor(LivingIncomingDamageEvent event, Player player) {
        if (!hasFullCarbonArmor(player)) {
            return false;
        }

        applyCarbonArmorPenalty(event, player);
        return true;
    }

    private static void applyCarbonArmorPenalty(LivingIncomingDamageEvent event, Player player) {
        float damage = event.getAmount();
        float hurtDamage = damage * 2;

        for (int i = 0; i < 4; i++) {
            ItemStack armorPart = player.getInventory().getArmor(i);
            if (armorPart.getDamageValue() >= 1) {
                armorPart.hurtAndBreak((int) hurtDamage, player, null);
            }
        }
        event.setAmount(damage / 2);
    }
    private static boolean isCorrectWeapon(ItemStack weapon) {
        Set<Item> IMPERIAL_WEAPONS = Set.of(
            ModItemsAdditions.IMPERIAL_INFINITY_SWORD.get(),
            ModItemsAdditions.IMPERIAL_INFINITY_3D_SWORD.get(),
            ModItemsAdditions.IMPERIAL_INFINITY_AXE.get(),
            ModItemsAdditions.IMPERIAL_INFINITY_PAXEL.get(),
            ModItemsAdditions.IMPERIAL_INFINITY_HAMMER.get(),
            ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE.get(),
            ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL.get(),
            ModItemsAdditions.IMPERIAL_INFINITY_BOW.get(),
            ModItemsAdditions.IMPERIAL_INFINITY_HOE.get()
    );
        return IMPERIAL_WEAPONS.contains(weapon.getItem());
    }
    private static boolean hasFullArmorSet(Player player, Item boots, Item leggings, Item chestplate, Item helmet) {
        return player.getInventory().getArmor(BOOTS_SLOT).getItem() == boots &&
                player.getInventory().getArmor(LEGGINGS_SLOT).getItem() == leggings &&
                player.getInventory().getArmor(CHESTPLATE_SLOT).getItem() == chestplate &&
                player.getInventory().getArmor(HELMET_SLOT).getItem() == helmet;
    }

    private static boolean hasFullImperialArmor(Player player) {
        return hasFullArmorSet(player,
                ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get(),
                ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get(),
                ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get(),
                ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get()
        );
    }

    private static boolean hasFullCarbonArmor(Player player) {
        return hasFullArmorSet(player,
                ModItemsAdditions.CARBON_BOOTS.get(),
                ModItemsAdditions.CARBON_LEGGINGS.get(),
                ModItemsAdditions.CARBON_CHESTPLATE.get(),
                ModItemsAdditions.CARBON_HELMET.get()
        );
    }
}