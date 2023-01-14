package cn.sunnysky.functionalmoderncomputers.registry;

import cn.sunnysky.functionalmoderncomputers.api.IWithModel;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.log;

/**
 * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
 */
@Mod.EventBusSubscriber
public class ObjectRegistryHandler {


    /** Listen for the register event for creating custom items */
    public static void registerItems() {
        log.info("Generating rods");
        ItemHandler.createCraftingMaterials();
        log.info("Registering items");
        ForgeRegistries.ITEMS.registerAll(ItemHandler.items());
        log.info("Registered " + ItemHandler.size() + " items");
           /*
             event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
             event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
            */
    }
    /** Listen for the register event for creating custom blocks */
    public static void registerBlocks() {
        log.info("Registering blocks");
        ForgeRegistries.BLOCKS.registerAll(BlockHandler.blocks());
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

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        addRecipes();
    }

    private static void addRecipes() {
        GameRegistry.addSmelting(BlockHandler.TIN_ORE,ItemHandler.TIN_ING0T.getDefaultInstance(),1.0F);
    }

}
