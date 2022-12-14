package cn.sunnysky.functionalmoderncomputers.client.gui.elements;

import java.awt.Rectangle;
import java.util.List;

import cn.sunnysky.functionalmoderncomputers.client.gui.IGuiWrapper;
import cn.sunnysky.functionalmoderncomputers.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiElement {

    public static final Minecraft mc = Minecraft.getMinecraft();

    protected final ResourceLocation RESOURCE;
    protected final IGuiWrapper guiObj;
    protected final ResourceLocation defaultLocation;

    public GuiElement(ResourceLocation resource, IGuiWrapper gui, ResourceLocation def) {
        RESOURCE = resource;
        guiObj = gui;
        defaultLocation = def;
    }

    public void displayTooltip(String s, int xAxis, int yAxis) {
        guiObj.displayTooltip(s, xAxis, yAxis);
    }

    public void displayTooltips(List<String> list, int xAxis, int yAxis) {
        guiObj.displayTooltips(list, xAxis, yAxis);
    }

    public void renderScaledText(String text, int x, int y, int color, int maxX) {
        int length = getFontRenderer().getStringWidth(text);

        if (length <= maxX) {
            getFontRenderer().drawString(text, x, y, color);
        } else {
            float scale = (float) maxX / length;
            float reverse = 1 / scale;
            float yAdd = 4 - (scale * 8) / 2F;

            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            getFontRenderer().drawString(text, (int) (x * reverse), (int) ((y * reverse) + yAdd), color);
            GlStateManager.popMatrix();
        }
        //Make sure the color does not leak from having drawn the string
        RenderUtil.resetColor();
    }

    public FontRenderer getFontRenderer() {
        return guiObj.getFont();
    }

    public void mouseClickMove(int mouseX, int mouseY, int button, long ticks) {
    }

    public void mouseReleased(int x, int y, int type) {
    }

    public void mouseWheel(int x, int y, int delta) {
    }

    public abstract Rectangle4i getBounds(int guiWidth, int guiHeight);

    public abstract void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight);

    public abstract void renderForeground(int xAxis, int yAxis);

    public abstract void preMouseClicked(int xAxis, int yAxis, int button);

    public abstract void mouseClicked(int xAxis, int yAxis, int button);

    public interface IInfoHandler {

        List<String> getInfo();
    }

    public static class Rectangle4i {

        public final int x;
        public final int y;
        public final int width;
        public final int height;

        public Rectangle4i(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public Rectangle toRectangle() {
            return new Rectangle(x, y, width, height);
        }
    }

    protected boolean inBounds(int xAxis, int yAxis) {
        return false;
    }
}