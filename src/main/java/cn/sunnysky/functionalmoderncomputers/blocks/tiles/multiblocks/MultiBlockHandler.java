package cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks;

import cn.sunnysky.functionalmoderncomputers.registry.BlockHandler;
import net.minecraft.util.math.BlockPos;

public class MultiBlockHandler {
    public static final MultiBlockStructure TESLA_COIL = new MultiBlockStructure();

    static {
        TESLA_COIL.addComponent(BlockHandler.TESLA_COIL_CORE,new BlockPos(0,0,0));
        TESLA_COIL.addComponent(BlockHandler.HIGH_TENSION_COIL,new BlockPos(0,1,0));
        TESLA_COIL.addComponent(BlockHandler.HIGH_TENSION_COIL,new BlockPos(0,2,0));
    }
}
