package cn.sunnysky.functionalmoderncomputers.client.gui.impl;

import cn.sunnysky.functionalmoderncomputers.CommonProxy;
import cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWaterwheel;
import cn.sunnysky.functionalmoderncomputers.client.gui.AdvancedGuiTile;
import cn.sunnysky.functionalmoderncomputers.client.gui.elements.GuiPowerBar;
import cn.sunnysky.functionalmoderncomputers.inventory.WaterwheelContainer;
import cn.sunnysky.functionalmoderncomputers.util.EnumColor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWaterwheel extends AdvancedGuiTile<TileWaterwheel> {
    public GuiWaterwheel(InventoryPlayer inventory, TileWaterwheel tile) {
        super(tile, new WaterwheelContainer(inventory,tile));
        ResourceLocation resource = getGuiLocation();
        addGuiElement(new GuiPowerBar(this,tile,resource,7,15));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTick, mouseX, mouseY);
        final String localization = CommonProxy.localize("tile.waterwheel.name");
        final int x = (xSize / 2) - (fontRenderer.getStringWidth(localization) / 2);
        renderScaledText(localization, x, 6, EnumColor.DARK_BLUE.mcMeta,x);
    }

    @Override
    protected ResourceLocation getGuiLocation() {
        return new ResourceLocation(FunctionalModernComputers.MOD_ID,"gui/GuiWaterwheel.png");
    }
}
