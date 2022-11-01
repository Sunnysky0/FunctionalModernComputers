package cn.sunnysky.functionalmoderncomputers.client.gui.impl;

import cn.sunnysky.functionalmoderncomputers.CommonProxy;
import cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWaterwheel;
import cn.sunnysky.functionalmoderncomputers.client.gui.AdvancedGuiTile;
import cn.sunnysky.functionalmoderncomputers.client.gui.elements.GuiPowerBar;
import cn.sunnysky.functionalmoderncomputers.inventory.WaterwheelContainer;
import cn.sunnysky.functionalmoderncomputers.util.EnumColor;
import cn.sunnysky.functionalmoderncomputers.util.GeneralUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiWaterwheel extends AdvancedGuiTile<TileWaterwheel> {
    public GuiWaterwheel(InventoryPlayer inventory, TileWaterwheel tile) {
        super(tile, new WaterwheelContainer(inventory,tile));
        ResourceLocation resource = getGuiLocation();
        addGuiElement(new GuiPowerBar(this,tile,resource,7,15));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
        final String localization = CommonProxy.localize("tile.waterwheel.name");
        fontRenderer.drawString(localization, (xSize / 2) - (fontRenderer.getStringWidth(localization) / 2), 6, EnumColor.DARK_BLUE.mcMeta);
        super.drawGuiContainerBackgroundLayer(partialTick, mouseX, mouseY);
    }

    @Override
    protected ResourceLocation getGuiLocation() {
        return new ResourceLocation(FunctionalModernComputers.MOD_ID,"gui/GuiWaterwheel.png");
    }
}
