package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.command.Infuser;
import com.Infinity.Nexus.Mod.command.Teste;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = InfinityNexusMod.MOD_ID)
public class CommandRegister {
    @SubscribeEvent
    public static void onCommandRegister(final RegisterCommandsEvent event) {
        new Teste(event.getDispatcher());
        new Infuser(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}