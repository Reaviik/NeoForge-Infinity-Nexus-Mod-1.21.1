package com.Infinity.Nexus.Mod.entity.custom;

import com.Infinity.Nexus.Mod.entity.ModEntities;
import com.Infinity.Nexus.Mod.entity.ai.AsgreonAttackGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Asgreon  extends Animal {

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(Asgreon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SITING =
            SynchedEntityData.defineId(Asgreon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE =
            SynchedEntityData.defineId(Asgreon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WALKING =
            SynchedEntityData.defineId(Asgreon.class, EntityDataSerializers.BOOLEAN);


    public  final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public  final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    public  final AnimationState sitAnimationState = new AnimationState();
    public int sitAnimationTimeout = 0;
    public  final AnimationState walkAnimationState = new AnimationState();
    public int walkAnimationTimeout = 0;
    public Asgreon(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(0, new AsgreonAttackGoal(this, 1.0d, true));

        this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.1d));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0d));

        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 12f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 3500d)
                .add(Attributes.MOVEMENT_SPEED, 0.2d)
                .add(Attributes.FOLLOW_RANGE, 24d)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_DAMAGE, 27f)
                .add(Attributes.ATTACK_KNOCKBACK, 3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 22f);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.ASGREON.get().create(pLevel);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerable()) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }


    private void setupAnimationStates() {
        if (attackAnimationTimeout <= 0 && this.isAttacking()) {
            idleAnimationState.stop();
            sitAnimationState.stop();
            walkAnimationState.stop();
            attackAnimationTimeout = 100;
            attackAnimationState.start(tickCount);
        } else {
            attackAnimationTimeout--;
        }

        if (sitAnimationTimeout <= 0 && this.isSitting()) {
            sitAnimationTimeout = 100;
            idleAnimationState.stop();
            walkAnimationState.stop();
            attackAnimationState.stop();
            sitAnimationState.start(tickCount);
            setSitting(true);
        } else {
            sitAnimationTimeout--;
        }

        if (walkAnimationTimeout <= 0 && this.isWalking()) {
            walkAnimationTimeout = 100;
            sitAnimationState.stop();
            idleAnimationState.stop();
            attackAnimationState.stop();
            walkAnimationState.start(tickCount);
            setWalking(true);
        } else {
            walkAnimationTimeout--;
        }

        if (idleAnimationTimeout <= 0 && !this.isAttacking() && !this.isSitting() && !this.isWalking()) {
            idleAnimationTimeout = 100;
            sitAnimationState.stop();
            walkAnimationState.stop();
            attackAnimationState.stop();
            idleAnimationState.start(tickCount);
            setIdle(true);
        } else {
            idleAnimationTimeout--;
        }

        if (!this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationState.stop();
            setAttacking(false);
        }

        if (!this.isSitting() || sitAnimationTimeout <= 0) {
            sitAnimationState.stop();
            this.setNoAi(false);
            setSitting(false);
        }

        if (!this.isWalking() && walkAnimationTimeout <= 0) {
            walkAnimationState.stop();
            setWalking(false);
        }

        if (!this.isIdle() && idleAnimationTimeout <= 0) {
            idleAnimationState.stop();
            setIdle(false);
        }
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        sitAnimationTimeout = 100;
        sitAnimationState.start(this.tickCount);
        this.setNoAi(true);
        setSitting(true);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        if(!this.isSitting()){
            setNoAi(false);
        }
    }

    public void setWalking(boolean walking) {
        this.entityData.set(WALKING, walking);
    }

    public boolean isWalking() {
        return this.entityData.get(WALKING);
    }

    public void setIdle(boolean idle) {
        this.entityData.set(IDLE, idle);
    }

    public boolean isIdle() {
        return this.entityData.get(IDLE);
    }
    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITING, sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITING);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        this.entityData.set(ATTACKING, false);
        this.entityData.set(SITING, false);
        this.entityData.set(WALKING, false);
        this.entityData.set(IDLE, false);
    }
}

