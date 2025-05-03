package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.entity.InfuserBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Infuser extends BaseMachineBlock {
    public static final MapCodec<BaseEntityBlock> CODEC = simpleCodec(Infuser::new);
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 15, 16);

    public Infuser(Properties pProperties) {
        super(pProperties, CODEC);
    }
    @Override
    protected void dropContents(Level level, BlockPos pos, BlockEntity blockEntity) {
        if (blockEntity instanceof InfuserBlockEntity) {
            ((InfuserBlockEntity) blockEntity).drops();
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        InfuserBlockEntity  blockEntity = (InfuserBlockEntity) level.getBlockEntity(pos);
        if(player.getMainHandItem().isEmpty()) {
            blockEntity.removeStack(player, player.isShiftKeyDown() ? 0 : 1);
        }
        if(!player.isShiftKeyDown()){
            blockEntity.addStack(player.getMainHandItem().copy(),player);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new InfuserBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.INFUSER_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }


    @Override
    protected String[] getDescription() {
        return new String[]{
                Component.literal("§bInfuser Build Example").getString(),
                Component.literal("§4§n[]§f = §4").append(Component.translatable("block.infinity_nexus_mod.infuser")).getString(),
                Component.literal("§5[]§f = ").append(Component.translatable("block.infinity_nexus_mod.magic_pedestal")).getString(),
                Component.literal("§e[]§f = ").append(Component.translatable("block.infinity_nexus_mod.resource_pedestal")).getString(),
                Component.literal("§8[]§f = ").append(Component.translatable("block.infinity_nexus_mod.decor_pedestal")).getString(),
                Component.literal("§3[]§f = ").append(Component.translatable("block.infinity_nexus_mod.tech_pedestal")).getString(),
                Component.literal("§6[]§f = ").append(Component.translatable("block.infinity_nexus_mod.creativity_pedestal")).getString(),
                Component.literal("§2[]§f = ").append(Component.translatable("block.infinity_nexus_mod.exploration_pedestal")).getString(),
                Component.literal(" ").getString(),
                Component.literal("§f[][]§5[]§f[][]").getString(),
                Component.literal("§e[]§f[][][]§8[]").getString(),
                Component.literal("§f[][]§4[]§f[][]").getString(),
                Component.literal("§3[]§f[][][]§6[]").getString(),
                Component.literal("§f[][]§2[]§f[][]").getString()};
    }
}