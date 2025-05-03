package com.Infinity.Nexus.Mod.screen.factory;

import com.Infinity.Nexus.Core.renderer.EnergyInfoArea;
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

import java.util.Optional;

public class FactoryScreen extends AbstractContainerScreen<FactoryMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/factory_gui.png");

    private EnergyInfoArea energyInfoArea;
    protected int imageWidth = 176;
    protected int imageHeight = 222;

    public FactoryScreen(FactoryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        assignEnergyInfoArea();
    }


    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 159, y+6, menu.getBlockEntity().getEnergyStorage(), 6, imageHeight- 105);
    }
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font,this.playerInventoryTitle,8,100,0XFFFFFF);
        pGuiGraphics.drawString(this.font,this.title,8,-38,0XFFFFFF);

        renderEnergyAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);

        InfoArea.draw(pGuiGraphics);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }


    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 159,  6, 6, 117)) {
            pGuiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(), Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if(Screen.hasShiftDown() || isMouseAboveArea(pMouseX, pMouseY, x, y, - 15, + 37, 17, 54)) {
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE, x - 15, y + 37, 193, 84, 18, 54);
        }else{
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE, x - 3, y + 37, 193, 84, 18, 54);
        }

        guiGraphics.blit(TEXTURE, x + 2, y - 14, 2, imageHeight, imageWidth -4, 66);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);


        renderProgressArrow(guiGraphics, x, y);
        energyInfoArea.render(guiGraphics);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 151, y + 123 , 182, 117,  -6, -menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}