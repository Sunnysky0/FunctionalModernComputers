package cn.sunnysky.functionalmoderncomputers.registry;

import cn.sunnysky.functionalmoderncomputers.items.ANDGate;
import cn.sunnysky.functionalmoderncomputers.items.Hammer;
import cn.sunnysky.functionalmoderncomputers.items.ItemStaticRender;
import cn.sunnysky.functionalmoderncomputers.items.ORGate;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemHandler {
    private static final List<Item> ITEMS = new ArrayList<>();

    public static final ANDGate AND_GATE = new ANDGate();

    public static final ORGate OR_GATE = new ORGate();

    public static final Item TIN_ING0T = createItem("tin_ingot");

    public static final Item HAMMER = new Hammer();

    public static void register(Item i){ITEMS.add(i);}

    public static void register(String registryName){new ItemStaticRender(registryName).setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);}

    public static Item[] items(){ return  ITEMS.toArray(new Item[0]); }

    public static int size(){ return ITEMS.size(); }

    public static void forEach(Consumer<Item> c){
        ITEMS.forEach(c);
    }

    public static Item createItem(String registryName){
        return new ItemStaticRender(registryName).setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    public static void createCraftingMaterials(){

        register("iron_rod");
        register("bearing");
        register("turbine_blade");

        register("paper_dielectric");
        register("tin_plate");
    }
}
