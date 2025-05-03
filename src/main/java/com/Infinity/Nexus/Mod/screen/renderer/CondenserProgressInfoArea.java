package com.Infinity.Nexus.Mod.screen.renderer;

import com.Infinity.Nexus.Mod.block.entity.MatterCondenserBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  https://github.com/BluSunrize/ImmersiveEngineering/blob/1.19.2/LICENSE
 *
 *  Slightly Modified Version by: Kaupenjoe
 */

public class CondenserProgressInfoArea {
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private final MatterCondenserBlockEntity entity;

    public CondenserProgressInfoArea(int xMin, int yMin, MatterCondenserBlockEntity entity)  {
        this(xMin, yMin, entity,6,62);
    }

    public CondenserProgressInfoArea(int xMin, int yMin, MatterCondenserBlockEntity entity, int width, int height)  {
        xPos = xMin;
        yPos = yMin;
        this.width = width;
        this.height = height;
        this.entity = entity;
    }

    public List<Component> getTooltips() {
        //System.out.println(entity.getProgress() / entity.getMaxProgress() * 100 +"% >"+entity.getProgress());
        return List.of(Component.literal((double) entity.getProgress() / entity.getMaxProgress() * 100 +"%"));
    }

    public void render(GuiGraphics guiGraphics) {
        int stored = (int)(height * (entity.getProgress() / (float) entity.getMaxProgress()));
        guiGraphics.fillGradient(xPos,yPos + (height - stored),xPos + width, yPos + height, 0x997F006E, 0x9933002C);
    }
}