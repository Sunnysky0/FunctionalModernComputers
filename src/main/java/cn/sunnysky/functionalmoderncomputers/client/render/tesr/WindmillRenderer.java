package cn.sunnysky.functionalmoderncomputers.client.render.tesr;

import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWindmill;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class WindmillRenderer extends TileEntitySpecialRenderer<TileWindmill> {

    @Override
    public boolean isGlobalRenderer(TileWindmill te) {
        return true;
    }

    @Override
    public void render(TileWindmill te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderWindmill(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderWindmill(TileWindmill te) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(.5, 0, .5);

        if (te.canRotate()) {
            long angle = (System.currentTimeMillis() / 10) % 360;
            GlStateManager.rotate(angle, 0, 1, 0);
        }

        RenderHelper.disableStandardItemLighting();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = te.getWorld();
        // Translate back to local view coordinates so that we can do the actual rendering here
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state = te.getBlockType().getDefaultState();
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}
