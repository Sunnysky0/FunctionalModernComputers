package cn.sunnysky.functionalmoderncomputers.registry;

import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.InfiniteWaterProvider;
import cn.sunnysky.functionalmoderncomputers.blocks.Waterwheel;
import cn.sunnysky.functionalmoderncomputers.items.ANDGate;
import cn.sunnysky.functionalmoderncomputers.items.ORGate;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BlockHandler {
    private static final List<Block> BLOCKS = new ArrayList<>();

    public static final Waterwheel WATERWHEEL = new Waterwheel();

    public static final InfiniteWaterProvider INFINITE_WATER_PROVIDER = new InfiniteWaterProvider();

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
}
