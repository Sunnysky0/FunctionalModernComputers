package cn.sunnysky.functionalmoderncomputers;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Objects;

public class ClientProxy extends CommonProxy{
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);

        OBJLoader.INSTANCE.addDomain(FunctionalModernComputers.MOD_ID);
    }

    public void init(FMLInitializationEvent event){
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), id));
    }

    @Override
    public void registerItemModelRenderer(Item item, int meta, String id) {
        super.registerItemModelRenderer(item, meta, id);
        ModelResourceLocation model = new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), id);
        ModelLoader.registerItemVariants(item,model);

        ModelLoader.setCustomModelResourceLocation(item,meta,model);
    }
}
