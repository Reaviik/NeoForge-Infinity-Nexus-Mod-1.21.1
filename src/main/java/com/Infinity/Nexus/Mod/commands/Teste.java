package com.Infinity.Nexus.Mod.commands;

import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class Teste {
    public Teste(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("inm").then(Commands.literal("hand")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        player.sendSystemMessage(Component.literal("Item: " + heldItem.getItem().getDescription().getString()));
        player.sendSystemMessage(Component.literal("Quantidade: " + heldItem.getCount()));
        player.sendSystemMessage(Component.literal("Meta: " + heldItem.getDamageValue()));

        // Listar todos os componentes
        player.sendSystemMessage(Component.literal("Componentes:"));

        // Acessar o registro de componentes
        BuiltInRegistries.DATA_COMPONENT_TYPE.stream()
                .filter(heldItem::has)
                .forEach(componentType -> {
                    String componentId = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(componentType).toString();
                    Object componentValue = heldItem.get(componentType);

                    player.sendSystemMessage(Component.literal(" - " + componentId + ": " + componentValue));
                });

        Level level = context.getSource().getLevel();
        ServerLevel serverLevel = ServerLifecycleHooks.getCurrentServer().getLevel(level.dimension());
        IFFakePlayer fakePlayer = new IFFakePlayer((ServerLevel) level );
        BlockPos pos = BlockPos.containing(context.getSource().getPosition()).above(3);
        fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, heldItem.copy());

        //boolean b = fakePlayer.onPlaceItemIntoWorld(serverLevel, pos, heldItem, InteractionHand.MAIN_HAND, Direction.NORTH);

        //player.sendSystemMessage(Component.literal("onPlaceItemIntoWorld: " + b));
        return 1;
    }

}