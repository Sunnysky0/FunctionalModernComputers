package cn.sunnysky.functionalmoderncomputers;

import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.registry.BlockHandler;
import cn.sunnysky.functionalmoderncomputers.registry.ObjectRegistryHandler;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.log;

public class CommonProxy {
    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    public void preInit(FMLPreInitializationEvent event) {
        ObjectRegistryHandler.registerBlocks();
        ObjectRegistryHandler.registerItems();
        log.info("Registering tile entities");
        loadTiles(event);
    }

    public void loadTiles(FMLPreInitializationEvent event)
    {
        AtomicInteger ct = new AtomicInteger();
        BlockHandler.forEachTile(
                e -> {
                    registerTileEntity(
                            ((IWithTileEntity) e).tileClass(),
                            Objects.requireNonNull(e.getRegistryName()).toString());
                    ct.getAndIncrement();
                }
        );

        log.info("Registered " + ct + " tile entities");
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    public void init(FMLInitializationEvent event) {
        log.info("Registering GUI Handler");
        NetworkRegistry.INSTANCE.registerGuiHandler(FunctionalModernComputers.INSTANCE, new GuiProxy());
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }

    public void registerItemModelRenderer(Item item, int meta, String id){

    }

    @SuppressWarnings("deprecation")
    public static String localize(String unlocalized, Object... args) {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }


    @SuppressWarnings("deprecation")
    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, id);
    }
}
