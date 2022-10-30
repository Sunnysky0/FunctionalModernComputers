package cn.sunnysky.functionalmoderncomputers.registry;

import cn.sunnysky.functionalmoderncomputers.api.IWithModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.log;

/**
 * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
 */
@Mod.EventBusSubscriber
public class ObjectRegistryHandler {


    /** Listen for the register event for creating custom items */
    @SubscribeEvent
    public static void addItems(RegistryEvent.Register<Item> event) {
        log.info("Generating rods");
        ItemHandler.createCraftingMaterials();
        log.info("Registering items");
        event.getRegistry().registerAll(ItemHandler.items());
        log.info("Registered " + ItemHandler.size() + " items");
           /*
             event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
             event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
            */
    }
    /** Listen for the register event for creating custom blocks */
    @SubscribeEvent
    public static void addBlocks(RegistryEvent.Register<Block> event) {
        log.info("Registering blocks");
        event.getRegistry().registerAll(BlockHandler.blocks());
        log.info("Registered " + BlockHandler.size() + " blocks");
           /*
             event.getRegistry().register(new MySpecialBlock().setRegistryName(MOD_ID, "mySpecialBlock"));
            */
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // Register models
        ItemHandler.forEach(
                i -> {
                    if (i instanceof IWithModel) ((IWithModel) i).registerModel();
                }
        );
        BlockHandler.forEach(
                i -> {
                    if (i instanceof IWithModel) ((IWithModel) i).registerModel();
                }
        );
    }
}
