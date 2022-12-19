package cn.sunnysky.functionalmoderncomputers.blocks.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileWindmill extends TileEntity {
    public boolean canRotate(){
        return
                getWorld()
                        .getTileEntity(
                                new BlockPos(getPos().getX(),
                                        getPos().getY() - 1,
                                        getPos().getZ()))
                        instanceof TileWindmillBase;
    }
}
