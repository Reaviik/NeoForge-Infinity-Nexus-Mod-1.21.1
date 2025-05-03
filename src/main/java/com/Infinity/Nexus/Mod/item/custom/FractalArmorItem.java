package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.component.ModDataComponents;
import com.Infinity.Nexus.Mod.item.ModArmorMaterials;
import com.Infinity.Nexus.Mod.item.client.parceiros.FractalArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class FractalArmorItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation START_ANIM = RawAnimation.begin().thenPlay("animation.start").thenLoop("animation.idle");
    private static final RawAnimation FLY_START_ANIM = RawAnimation.begin().thenPlay("animation.fly_start").thenLoop("animation.flying");

    public FractalArmorItem() {
        super(ModArmorMaterials.INFINITY, Type.CHESTPLATE, new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }


    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {

                GeoArmorRenderer<?> renderer;
                renderer = new FractalArmorModel.FractalArmorRenderer(itemStack.getOrDefault(ModDataComponents.FRACTAL_TYPE.get(), "infinity"));

                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "StateController", 20, this::handleAnimations));
    }

    private PlayState handleAnimations(AnimationState<FractalArmorItem> state) {
        Entity entity = state.getData(DataTickets.ENTITY);

        if (!(entity instanceof Player || entity instanceof ArmorStand)) return PlayState.STOP;

        boolean isFlying = false;

        if (entity instanceof Player player) {
            isFlying = player.getAbilities().flying || player.isFallFlying();
        }
        AnimationController<FractalArmorItem> controller = state.getController();

        if (controller.getAnimationState() == AnimationController.State.STOPPED) {
            controller.setAnimation(START_ANIM);
            return PlayState.CONTINUE;
        }

        controller.stop();
        if (isFlying) {
            controller.setAnimation(FLY_START_ANIM);
        } else {
            controller.setAnimation(START_ANIM);
        }
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}