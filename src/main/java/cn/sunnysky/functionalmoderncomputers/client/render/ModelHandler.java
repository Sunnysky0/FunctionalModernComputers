package cn.sunnysky.functionalmoderncomputers.client.render;

import cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers;
import cn.sunnysky.functionalmoderncomputers.client.render.amlfrom1710.AdvancedModelLoader;
import cn.sunnysky.functionalmoderncomputers.client.render.amlfrom1710.IModelCustom;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHandler {
    private static IModelCustom load(String path){ return AdvancedModelLoader.loadModel(new ResourceLocation(FunctionalModernComputers.MOD_ID,path));}

    private static ResourceLocation loadTex(String path){ return (new ResourceLocation(FunctionalModernComputers.MOD_ID,path));}

    //public static final IModelCustom WINDMILL_MODEL = load("models/block/windmill.obj");
    public static final ResourceLocation WINDMILL_TEX = loadTex("textures/models/windmill.png");
}
