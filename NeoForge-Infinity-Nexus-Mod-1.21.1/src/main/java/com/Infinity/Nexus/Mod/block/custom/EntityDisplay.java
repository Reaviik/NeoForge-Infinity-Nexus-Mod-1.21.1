package com.Infinity.Nexus.Mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EntityDisplay extends Block {
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 2);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public EntityDisplay(Properties pProperties) {
        super(pProperties);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            try {
                List<Mob> entities = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class, new AABB(new Vec3(pPos.getX(), pPos.getY(), pPos.getZ()), new Vec3(pPos.getX()+1, pPos.getY()+1, pPos.getZ()+1))));
                entities.forEach(Entity -> {
                    Entity.setNoAi(false);
                });
            } catch (Exception ignored) {

            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if(pEntity instanceof Mob mob) {
            try {
                if(!mob.isNoAi() && pState.getValue(LIT) == 0 && !(mob instanceof EnderDragon) && !(mob instanceof WitherBoss)) {
                    switch (pState.getValue(FACING)) {
                        case EAST:
                            mob.yHeadRot = -90;
                            mob.yBodyRot = -90;
                            mob.setYRot(-90);
                            break;
                        case WEST:
                            mob.yHeadRot = 90;
                            mob.yBodyRot = 90;
                            mob.setYRot(90);
                            break;
                        case SOUTH:
                            mob.yHeadRot = 0;
                            mob.yBodyRot = 0;
                            mob.setYRot(0);
                            break;
                        case NORTH:
                            mob.yHeadRot = 180;
                            mob.yBodyRot = 180;
                            mob.setYRot(180);
                            break;
                    }
                    mob.teleportTo(pPos.getX() + 0.5, pPos.getY(), pPos.getZ() + 0.5);
                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 10));
                    mob.setSpeed(0.0F);
                    mob.setNoAi(true);
                    mob.noCulling = true;
                    pLevel.setBlock(pPos, pState.setValue(LIT, 1), 3);
                }
            }catch (Exception ignored) {
            }
        }
    }

    @Override
    public StateDefinition<Block, BlockState> getStateDefinition() {
        return super.getStateDefinition();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(0, 0, 0, 16, 2, 16);
    }
}
