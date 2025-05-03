package com.Infinity.Nexus.Mod.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final KeyMapping FLIGHT_KEY = new KeyMapping(
            "key.infinity_nexus.flight",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            "key.category.infinity_nexus"
    );
}