package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ModSword extends SwordItem {

    private final Component translation;
    private final MobEffectInstance[] effects;

    public ModSword(Tier pTier, Properties pProperties, Component tooltip, MobEffectInstance[] effects) {
        super(pTier, pProperties.attributes(SwordItem.createAttributes(pTier, pTier.getAttackDamageBonus(), pTier.getSpeed())));

        this.translation = tooltip;
        this.effects = effects;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        for (MobEffectInstance effect : effects) {
            pTarget.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration()), pTarget);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(translation);
        } else {
            tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}