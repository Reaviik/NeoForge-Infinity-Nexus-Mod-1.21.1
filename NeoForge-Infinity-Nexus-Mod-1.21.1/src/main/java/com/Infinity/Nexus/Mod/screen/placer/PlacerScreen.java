package com.Infinity.Nexus.Mod.screen.placer;

import com.Infinity.Nexus.Core.renderer.InfoArea;
import com.Infinity.Nexus.Core.renderer.RenderScreenTooltips;
import com.Infinity.Nexus.Core.utils.MouseUtil;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class PlacerScreen extends AbstractContainerScreen<PlacerMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/placer_gui.png");

    public PlacerScreen(PlacerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }


    private void renderTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if (Screen.hasShiftDown()) {
             if (isMouseAboveArea(pMouseX, pMouseY, x, y, 7, 28, 17, 17)) {
                RenderScreenTooltips.renderComponentSlotTooltipAndItems(this.font, pGuiGraphics, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 79, 28, 17, 17)) {
                List<Component> components = List.of(Component.literal("Input Slot"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics ,components, pMouseX, pMouseY, x, y);
            }
        }
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x + 2, y-14, 2, 167, 174, 64);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 221, y + 68 , 238, 62,  -62, -menu.getScaledProgress()-10);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font,this.playerInventoryTitle,8,74,0XFFFFFF);
        pGuiGraphics.drawString(this.font,menu.getData(2) == 0 ? "§bWorking" : "§cNot Working",88,74,0XFFFFFF);
        pGuiGraphics.drawString(this.font,this.title,8,-9,0XFFFFFF);

        renderTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);

        InfoArea.draw(pGuiGraphics);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}