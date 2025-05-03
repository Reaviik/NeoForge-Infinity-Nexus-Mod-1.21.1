package com.Infinity.Nexus.Mod.entity.ai;

import com.Infinity.Nexus.Mod.entity.custom.Asgreon;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AsgreonAttackGoal extends MeleeAttackGoal {
    private final Asgreon entity;
    private int attackDelay = 50;
    private int tickUntilNextAttack = 80;
    private boolean shouldCountTillNextAttack = false;

    public AsgreonAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.entity = (Asgreon) pMob;
    }


    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy) {
        if(isEnemyWithinAttackDistance(pEnemy, 5)){
            shouldCountTillNextAttack = true;

            if (isTimeToStartAttacAnimation()) {
                entity.setAttacking(true);
            }
            if (isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        }else{
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }
    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
        return pDistToEnemySqr <= 5.0D;
    }

    protected void resetAttackCooldown() {
        this.tickUntilNextAttack = this.adjustedTickDelay(attackDelay * 2);
    }
    public boolean isTimeToAttack() {
        return tickUntilNextAttack <= 0;
    }

    @Override
    protected int getTicksUntilNextAttack() {
        return tickUntilNextAttack;
    }

    private void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(pEnemy);
    }

    private boolean isTimeToStartAttacAnimation() {
        return tickUntilNextAttack <= attackDelay;
    }
    @Override
    public void start() {
        super.start();
        this.attackDelay = 50;
        this.tickUntilNextAttack = 80;
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack){
            this.tickUntilNextAttack = Math.max(this.tickUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
