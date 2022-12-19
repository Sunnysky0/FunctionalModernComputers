package cn.sunnysky.functionalmoderncomputers.registry;

import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.*;
import cn.sunnysky.functionalmoderncomputers.blocks.multiparts.Charger;
import cn.sunnysky.functionalmoderncomputers.blocks.multiparts.EnergyDuct;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BlockHandler {
    private static final List<Block> BLOCKS = new ArrayList<>();

    public static final Waterwheel WATERWHEEL = new Waterwheel();
    public static final InfiniteWaterProvider INFINITE_WATER_PROVIDER = new InfiniteWaterProvider();

    public static final EnergyDuct ENERGY_DUCT = new EnergyDuct();

    public static final Charger CHARGER = new Charger();

    public static final MonitorScreen MONITOR_SCREEN = new MonitorScreen();

    public static final LocalHost LOCAL_HOST = new LocalHost();

    public static final Windmill WINDMILL = new Windmill();
    public static final WindmillBase WINDMILL_BASE = new WindmillBase();

    public static void register(Block i){BLOCKS.add(i);}

    public static Block[] blocks(){ return  BLOCKS.toArray(new Block[0]); }

    public static void forEach(Consumer<Block> c){
        BLOCKS.forEach(c);
    }

    public static void forEachTile(Consumer<Block> c){
        BLOCKS.forEach(
            b -> {
                if (b instanceof IWithTileEntity)
                    c.accept(b);
            });
    }

    public static int size() { return BLOCKS.size(); }
}
