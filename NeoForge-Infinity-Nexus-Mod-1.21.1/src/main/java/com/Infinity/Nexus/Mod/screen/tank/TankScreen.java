package com.Infinity.Nexus.Mod.screen.tank;

import com.Infinity.Nexus.Core.renderer.FluidTankRenderer;
import com.Infinity.Nexus.Core.renderer.InfoArea;
import com.Infinity.Nexus.Core.renderer.RenderScreenTooltips;
import com.Infinity.Nexus.Core.utils.MouseUtil;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.TankBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Optional;

public class TankScreen extends AbstractContainerScreen<TankMenu> implements GuiEventListener {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/tank_gui.png");
    private FluidTankRenderer fluidRenderer;
    public TankScreen(TankMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        assignFluidTank();
    }
    private void assignFluidTank() {
        fluidRenderer = new FluidTankRenderer(TankBlockEntity.getFluidCapacity(), true, 16, 62);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if(Screen.hasShiftDown() || isMouseAboveArea(pMouseX, pMouseY, x, y, - 15, + 26, 17, 18)) {
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE, x - 15, y + 28, 193, 84, 18, 18);
        }else{
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE, x - 3, y + 28, 193, 84, 18, 18);
        }

        guiGraphics.blit(TEXTURE, x + 2, y-14, 2, 167, 174, 64);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        fluidRenderer.render(guiGraphics, x+80, y+6, menu.blockEntity.getFluidHandler(null).getFluidInTank(0));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
    private void renderFluidAreaTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                         FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            guiGraphics.renderTooltip(this.font, renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }


    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font,this.playerInventoryTitle,8,74,0XFFFFFF);
        pGuiGraphics.drawString(this.font,this.title,8,-9,0XFFFFFF);

        renderFluidAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y,  menu.blockEntity.getFluidHandler(null).getFluidInTank(0), 80,6, fluidRenderer);

        InfoArea.draw(pGuiGraphics);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}