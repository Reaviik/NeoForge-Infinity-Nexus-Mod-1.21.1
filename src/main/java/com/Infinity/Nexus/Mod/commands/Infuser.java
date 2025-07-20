package com.Infinity.Nexus.Mod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class Infuser {
    public Infuser(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("inm").then(Commands.literal("pedestals")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        player.sendSystemMessage(Component.literal("§31 §f= " + Component.translatable("block.infinity_nexus_mod.tech_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§e2 §f= " + Component.translatable("block.infinity_nexus_mod.resource_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§53 §f= " + Component.translatable("block.infinity_nexus_mod.magic_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§74 §f= " + Component.translatable("block.infinity_nexus_mod.decor_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§65 §f= " + Component.translatable("block.infinity_nexus_mod.creativity_pedestal").getString()));
        player.sendSystemMessage(Component.literal("§26 §f= " + Component.translatable("block.infinity_nexus_mod.exploration_pedestal").getString()));

        return 1;
    }

}