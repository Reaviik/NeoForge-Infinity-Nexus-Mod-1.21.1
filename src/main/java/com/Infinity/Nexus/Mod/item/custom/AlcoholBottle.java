package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;

public class AlcoholBottle extends PotionItem {
    public AlcoholBottle(Properties properties) {
        super(properties);
    }


    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        if (pEntityLiving instanceof ServerPlayer player) {
            player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 1));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 1));
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 1));
            player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 2000, 1));
        }
        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }
}