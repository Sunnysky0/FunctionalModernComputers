package cn.sunnysky.functionalmoderncomputers.registry;

import cn.sunnysky.functionalmoderncomputers.items.ANDGate;
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

    public static void register(Item i){ITEMS.add(i);}

    public static Item[] items(){ return  ITEMS.toArray(new Item[0]); }

    public static int size(){ return ITEMS.size(); }

    public static void forEach(Consumer<Item> c){
        ITEMS.forEach(c);
    }

    public static void createCraftingMaterials(){
        register(new ItemStaticRender("iron_rod").setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP));
        register(new ItemStaticRender("bearing").setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP));
        register(new ItemStaticRender("turbine_blade").setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP));
    }
}
