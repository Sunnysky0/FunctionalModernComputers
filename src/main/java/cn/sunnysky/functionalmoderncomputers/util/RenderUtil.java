package cn.sunnysky.functionalmoderncomputers.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

//from Mekansim
public class RenderUtil {
    //Color
    public static void resetColor() {
        GlStateManager.color(1, 1, 1, 1);
    }

    private static float getRed(int color) {
        return (color >> 16 & 0xFF) / 255.0F;
    }

    private static float getGreen(int color) {
        return (color >> 8 & 0xFF) / 255.0F;
    }

    private static float getBlue(int color) {
        return (color & 0xFF) / 255.0F;
    }

    public static void color(int color) {
        GlStateManager.color(getRed(color), getGreen(color), getBlue(color), (color >> 24 & 0xFF) / 255f);
    }

    public static void color(@Nullable FluidStack fluid, float fluidScale) {
        if (fluid != null) {
            int color = fluid.getFluid().getColor(fluid);
            if (fluid.getFluid().isGaseous(fluid)) {
                GlStateManager.color(getRed(color), getGreen(color), getBlue(color), Math.min(1, fluidScale + 0.2F));
            } else {
                color(color);
            }
        }
    }

    public static void color(@Nullable FluidStack fluid) {
        if (fluid != null && fluid.getFluid() != null) {
            color(fluid.getFluid().getColor(fluid));
        }
    }

    public static void color(@Nullable Fluid fluid) {
        if (fluid != null) {
            color(fluid.getColor());
        }
    }

    public static void color(@Nullable EnumColor color) {
        color(color, 1.0F);
    }

    public static void color(@Nullable EnumColor color, float alpha) {
        color(color, alpha, 1.0F);
    }

    public static void color(@Nullable EnumColor color, float alpha, float multiplier) {
        if (color != null) {
            GlStateManager.color(color.getColor(0) * multiplier, color.getColor(1) * multiplier, color.getColor(2) * multiplier, alpha);
        }
    }

    public static int getColorARGB(EnumColor color, float alpha) {
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 1) {
            alpha = 1;
        }
        int argb = (int) (255 * alpha) << 24;
        argb |= color.rgbCode[0] << 16;
        argb |= color.rgbCode[1] << 8;
        argb |= color.rgbCode[2];
        return argb;
    }
}
