package cn.sunnysky.functionalmoderncomputers.client.gui.elements;

import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileElectricAppliance;
import cn.sunnysky.functionalmoderncomputers.client.gui.IGuiWrapper;
import cn.sunnysky.functionalmoderncomputers.client.gui.elements.GuiElement;
import cn.sunnysky.functionalmoderncomputers.util.GeneralUtil;
import java.awt.Rectangle;

import cofh.redstoneflux.api.IEnergyHandler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPowerBar extends GuiElement {

    private final TileElectricAppliance tileEntity;
    private final IPowerInfoHandler handler;
    private final int xLocation;
    private final int yLocation;
    private final int width = 6;
    private final int height = 56;

    public GuiPowerBar(IGuiWrapper gui, TileElectricAppliance tile, ResourceLocation def, int x, int y) {
        super(GeneralUtil.getResource(GeneralUtil.ResourceType.GUI_ELEMENT, "GuiPowerBar.png"), gui, def);
        tileEntity = tile;

        handler = new IPowerInfoHandler() {
            @Override
            public String getTooltip() {
                return GeneralUtil.getEnergyDisplay(tileEntity.energy(), tileEntity.maxEnergy());
            }

            @Override
            public double getLevel() {
                return (double) tileEntity.energy() / tileEntity.maxEnergy();
            }
        };

        xLocation = x;
        yLocation = y;
    }

    public GuiPowerBar(IGuiWrapper gui, IPowerInfoHandler h, ResourceLocation def, int x, int y) {
        super(GeneralUtil.getResource(GeneralUtil.ResourceType.GUI_ELEMENT, "GuiPowerBar.png"), gui, def);
        tileEntity = null;
        handler = h;

        xLocation = x;
        yLocation = y;
    }

    @Override
    public Rectangle4i getBounds(int guiWidth, int guiHeight) {
        return new Rectangle4i(guiWidth + xLocation, guiHeight + yLocation, width, height);
    }

    @Override
    protected boolean inBounds(int xAxis, int yAxis) {
        return xAxis >= xLocation && xAxis <= xLocation + width && yAxis >= yLocation && yAxis <= yLocation + height;
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(RESOURCE);
        guiObj.drawTexturedRect(guiWidth + xLocation, guiHeight + yLocation, 0, 0, width, height);
        if (handler.getLevel() > 0) {
            int displayInt = (int) (handler.getLevel() * 52) + 2;
            guiObj.drawTexturedRect(guiWidth + xLocation, guiHeight + yLocation + height - displayInt, 6, height - displayInt, width, displayInt);
        }
        mc.renderEngine.bindTexture(defaultLocation);
    }

    @Override
    public void renderForeground(int xAxis, int yAxis) {
        mc.renderEngine.bindTexture(RESOURCE);
        if (handler.getTooltip() != null && inBounds(xAxis, yAxis)) {
            displayTooltip(handler.getTooltip(), xAxis, yAxis);
        }
        mc.renderEngine.bindTexture(defaultLocation);
    }

    @Override
    public void preMouseClicked(int xAxis, int yAxis, int button) {
    }

    @Override
    public void mouseClicked(int xAxis, int yAxis, int button) {
    }

    public static abstract class IPowerInfoHandler {

        public String getTooltip() {
            return null;
        }

        public abstract double getLevel();
    }
}