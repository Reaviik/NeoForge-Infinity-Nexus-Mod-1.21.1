package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.client.InfinityArmorRenderer;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class InfinityArmorItem extends ArmorItem implements GeoItem {
    private static final int PARTICLE_SOUND_DELAY = 250;
    private static final double PARTICLE_VELOCITY = 0.3;
    private static final double PARTICLE_Y_VELOCITY = -0.2D;
    private static final double PARTICLE_RISE_VELOCITY = 0.02D;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static int particleDelay;
    private static double lastY = Double.MAX_VALUE;
    private static boolean wasFlying = false;
    private static boolean isFalling = false;

    public InfinityArmorItem(Holder<ArmorMaterial> material, Type type, Properties settings) {
        super(material, type, settings);
    }
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!(pEntity instanceof Player player)) {
            return;
        }

        boolean hasFullSet = hasFullSuitOfArmorOn(player);
        if (hasFullSet && player.getAbilities().mayfly) {
            renderParticles(player, pLevel);
        }
    }
    private void renderParticles(Player player, Level level) {
        boolean isCurrentlyFlying = player.getAbilities().flying;

        if (!player.onGround() && !isCurrentlyFlying) {
            double currentY = player.getY();
            if (currentY < lastY) {
                isFalling = true;
            }
            lastY = currentY;
        }

        if (player.onGround()) {
            if (isFalling || wasFlying) {
                handleLandingEffects(player, level);
                isFalling = false;
                wasFlying = false;
            }
            lastY = player.getY();
        }

        if (isCurrentlyFlying) {
            wasFlying = true;
            handleFlightEffects(player, level);
        }
    }
    private void handleFlightEffects(Player player, Level pLevel) {
        double pitch = player.getXRot();
        double yaw = -player.getYRot()+90;
        double v = 0.3 * Math.sin(Math.toRadians(yaw));
        double x = player.getX() + v;
        double y = player.getY();
        double z = player.getZ() + PARTICLE_VELOCITY * Math.cos(Math.toRadians(yaw));
        pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0.0D, PARTICLE_Y_VELOCITY, 0.0D);

        x = player.getX() - v;
        z = player.getZ() - PARTICLE_VELOCITY * Math.cos(Math.toRadians(yaw));
        pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0.0D, PARTICLE_Y_VELOCITY, 0.0D);
        particleDelay++;
        if(particleDelay >= PARTICLE_SOUND_DELAY) {
            particleDelay = 0;
            pLevel.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.CHORUS_FLOWER_GROW, SoundSource.AMBIENT, 0.5F, 0.4F, false);
        }
    }
    private void handleLandingEffects(Player player, Level pLevel) {
        double radius = 0.5;

        double[] radii = {radius + 0.5, radius + 1.0, radius + 1.5};
        int[] stepSizes = {15, 10, 5};

        for (int i = 0; i < radii.length; i++) {
            radius = radii[i];
            int stepSize = stepSizes[i];

            for (int j = 0; j < 360; j += stepSize) {
                double x = player.getX() + radius * Math.cos(Math.toRadians(j));
                double z = player.getZ() + radius * Math.sin(Math.toRadians(j));
                pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, player.getY(), z, 0.0D, PARTICLE_RISE_VELOCITY, 0.0D);
                pLevel.addParticle(ParticleTypes.FIREWORK, x, player.getY(), z, 0.0D, PARTICLE_RISE_VELOCITY, 0.0D);
                pLevel.addParticle(ParticleTypes.SMALL_FLAME, x, player.getY(), z, 0.0D, PARTICLE_RISE_VELOCITY, 0.0D);
            }
        }
        pLevel.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BIG_FALL, SoundSource.AMBIENT, 0.5F, 0.5F, false);
        pLevel.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 0.5F, 0.1F, false);
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        if (player == null) return false;

        return player.getInventory().armor.stream().allMatch(stack -> {
            if (stack.isEmpty()) return false;
            Item item = stack.getItem();
            return item == ModItemsAdditions.INFINITY_BOOTS.get() ||
                    item == ModItemsAdditions.INFINITY_LEGGINGS.get() ||
                    item == ModItemsAdditions.INFINITY_CHESTPLATE.get() ||
                    item == ModItemsAdditions.INFINITY_HELMET.get();
        });
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.infinity_nexus.infinity_armor"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    // Create our armor model/renderer for forge and return it
    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@javax.annotation.Nullable T livingEntity, ItemStack itemStack, @javax.annotation.Nullable EquipmentSlot equipmentSlot, @javax.annotation.Nullable HumanoidModel<T> original) {
                if(this.renderer == null)
                    this.renderer = new InfinityArmorRenderer();

                return this.renderer;
            }
        });
    }

    // Let's add our animation controller
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            state.setAnimation(DefaultAnimations.IDLE);
            state.getController().setAnimation(RawAnimation.begin().then("misc.idle", Animation.LoopType.LOOP));

            Entity entity = state.getData(DataTickets.ENTITY);
            if(!(entity instanceof Player)) {
                return PlayState.STOP;
            }
            Set<Item> wornArmor = new ObjectOpenHashSet<>();

            for (ItemStack stack : ((Player) entity).getArmorSlots()) {
                if (stack.isEmpty())
                    return PlayState.STOP;

                wornArmor.add(stack.getItem());
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}