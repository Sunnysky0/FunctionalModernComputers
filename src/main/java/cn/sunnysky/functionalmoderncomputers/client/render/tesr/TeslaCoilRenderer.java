package cn.sunnysky.functionalmoderncomputers.client.render.tesr;

import cn.sunnysky.functionalmoderncomputers.blocks.multiblocks.TeslaCoilCore;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks.TileTeslaCoil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TeslaCoilRenderer extends TileEntitySpecialRenderer<TileTeslaCoil> {

    @Override
    public void render(TileTeslaCoil te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        if(te.getBlock().isFormed(te.getWorld().getBlockState(te.getPos())))
            renderAll(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderAll(TileTeslaCoil te) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(.5, 0, .5);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = te.getWorld();
        BlockPos blockPos = te.getPos();

        Tessellator tessellator = Tessellator.getInstance();

        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        bufferBuilder.setTranslation( - blockPos.getX(), - blockPos.getY(), - blockPos.getZ());
        bufferBuilder.color(255,255,255,255);

        IBlockState state = world.getBlockState(blockPos).withProperty(TeslaCoilCore.FACING, EnumFacing.NORTH);
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, blockPos, bufferBuilder, true);

        bufferBuilder.setTranslation(0.0D, 0.0D, 0.0D);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}
