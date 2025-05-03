package com.Infinity.Nexus.Mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class EntityCentralizer extends CarpetBlock {
    public EntityCentralizer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if(pEntity instanceof Mob mob) {

            try {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 5, false, false));
                double blockX = pPos.getX() + 0.5;
                double blockZ = pPos.getZ() + 0.5;
                double mobX = mob.getX();
                double mobZ = mob.getZ();
                float factor = 0.1f;

                    if (mobZ > blockZ) {
                        mob.absMoveTo(mobX, mob.getY(), mobZ - factor);
                        if (mobX > blockX) {
                            mob.absMoveTo(mobX - factor, mob.getY(), mobZ);
                        }
                    }
                    if (mobZ < blockZ) {
                        mob.absMoveTo(mobX, mob.getY(), mobZ + factor);
                        if (mobX < blockX) {
                            mob.absMoveTo(mobX + factor, mob.getY(), mobZ);
                        }
                    }
            }catch (Exception ignored) {
            }
        }
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Stream.of(
                Block.box(0, 0, 0, 2, 1, 14),
                Block.box(14, 0, 2, 16, 1, 16),
                Block.box(0, 0, 14, 14, 1, 16),
                Block.box(2, 0, 0, 16, 1, 2)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    @Override
    public boolean canSurvive(BlockState p_152922_, LevelReader p_152923_, BlockPos p_152924_) {
        return true;
    }
}
