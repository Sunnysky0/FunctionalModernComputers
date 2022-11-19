package cn.sunnysky.functionalmoderncomputers.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class AdvancedGuiTile<TILE extends TileEntity> extends AdvancedGui {

    protected final TILE tileEntity;

    public AdvancedGuiTile(TILE tile, Container container) {
        super(container);
        tileEntity = tile;
    }

    public TILE getTileEntity() {
        return tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        int xAxis = mouseX - guiLeft;
        int yAxis = mouseY - guiTop;

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        tileEntity.validate();
    }
}