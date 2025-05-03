package com.Infinity.Nexus.Mod.screen.assembler;

import com.Infinity.Nexus.Core.renderer.EnergyInfoArea;
import com.Infinity.Nexus.Core.renderer.FluidTankRenderer;
import com.Infinity.Nexus.Core.renderer.InfoArea;
import com.Infinity.Nexus.Core.renderer.RenderScreenTooltips;
import com.Infinity.Nexus.Core.utils.MouseUtil;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class AssemblerScreen extends AbstractContainerScreen<AssemblerMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(InfinityNexusMod.MOD_ID, "textures/gui/assembler_gui.png");

    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer fluidRenderer;

    public AssemblerScreen(AssemblerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        assignEnergyInfoArea();
        assignFluidTank();
    }

    private void assignFluidTank() {
        fluidRenderer = new FluidTankRenderer(ConfigUtils.assembler_fluid_storage_capacity, true, 6, 62);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 159, y + 6, menu.getBlockEntity().getEnergyStorage());
    }
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font,this.playerInventoryTitle,8,74,0XFFFFFF);
        pGuiGraphics.drawString(this.font,this.title,8,-9,0XFFFFFF);

        renderEnergyAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);
        renderFluidAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y, menu.blockEntity.getFluidHandler(null).getFluidInTank(0), 146,6, fluidRenderer);
        renderTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);

        InfoArea.draw(pGuiGraphics);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderFluidAreaTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                         FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            guiGraphics.renderTooltip(this.font, renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 159,  6, 6, 62)) {
            pGuiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(), Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if (Screen.hasShiftDown()) {
            if (isMouseAboveArea(pMouseX, pMouseY, x, y, -12, 10, 17, 53)) {
                RenderScreenTooltips.renderUpgradeSlotTooltipAndItems(this.font, pGuiGraphics, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 7, 28, 17, 17)) {
                RenderScreenTooltips.renderComponentSlotTooltipAndItems(this.font, pGuiGraphics, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 145, 6, 6, 62)) {
                List<Component> components = List.of(Component.literal("Lubricant"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics ,components, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 124, 6, 17, 17)) {
                List<Component> components = List.of(Component.literal("Lubricant Input"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics ,components, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 124, 52, 17, 17)) {
                List<Component> components = List.of(Component.literal("Lubricant Output"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics ,components, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 57, 6, 17, 17)
                || isMouseAboveArea(pMouseX, pMouseY, x, y, 57, 28, 17, 17)
                || isMouseAboveArea(pMouseX, pMouseY, x, y, 57, 52, 17, 17)

                || isMouseAboveArea(pMouseX, pMouseY, x, y, 80, 6, 17, 17)
                || isMouseAboveArea(pMouseX, pMouseY, x, y, 80, 52, 17, 17)

                || isMouseAboveArea(pMouseX, pMouseY, x, y, 103, 6, 17, 17)
                || isMouseAboveArea(pMouseX, pMouseY, x, y, 103, 28, 17, 17)
                || isMouseAboveArea(pMouseX, pMouseY, x, y, 103, 52, 17, 17)


            ){
                List<Component> components = List.of(Component.literal("Input Slot"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics ,components, pMouseX, pMouseY, x, y);
            }else if(isMouseAboveArea(pMouseX, pMouseY, x, y, 80, 28, 17, 17)) {
                List<Component> components = List.of(Component.literal("Output Slot"));
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
        if(Screen.hasShiftDown() || isMouseAboveArea(pMouseX, pMouseY, x, y, - 15, + 10, 17, 54)) {
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE, x - 15, y + 10, 193, 84, 18, 131);
        }else{
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE, x - 3, y + 10, 193, 84, 18, 131);
        }
        guiGraphics.blit(TEXTURE, x + 2, y-14, 2, 167, 174, 64);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);


        renderProgressArrow(guiGraphics, x, y);
        energyInfoArea.render(guiGraphics);
        fluidRenderer.render(guiGraphics, x+146, y+6, menu.blockEntity.getFluidHandler(null).getFluidInTank(0));
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {

            guiGraphics.blit(TEXTURE, x + 58, y + 6, 176, 0, 62, menu.getScaledProgress());
            guiGraphics.blit(TEXTURE, x + 58, y + 6, 176, 0, menu.getScaledProgress(), 62);
            guiGraphics.blit(TEXTURE, x + 120, y + 68 , 238, 62,  -62, -menu.getScaledProgress());
            guiGraphics.blit(TEXTURE, x + 120, y + 68 , 238, 62,  -menu.getScaledProgress(), -62);

        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics,mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}