package com.Infinity.Nexus.Mod.block.entity.pedestals;


import com.Infinity.Nexus.Mod.block.custom.pedestals.BasePedestal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BasePedestalBlockEntity extends BlockEntity implements GeoBlockEntity {
    protected static final RawAnimation WORK = RawAnimation.begin().thenPlay("animation.model.work").thenLoop("animation.model.off");
    protected static final RawAnimation OFF = RawAnimation.begin().then("animation.model.off", Animation.LoopType.LOOP);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public BasePedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::deployAnimController));
    }

    protected <E extends GeoAnimatable> PlayState deployAnimController(final AnimationState<E> state) {
        try{
            if(level.isClientSide) {
                if (this.getBlockState().getValue(BasePedestal.WORK)) {
                    state.getController().setAnimation(WORK);
                    state.setControllerSpeed(1.0f);
                }else{
                    state.getController().setAnimation(OFF);
                    state.setControllerSpeed(1.0f);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}