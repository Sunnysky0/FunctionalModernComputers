package cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks;

import net.minecraft.util.math.AxisAlignedBB;

public class TileTeslaCoil extends StructureCoreTile {

    @Override
    public boolean formStructure() {
        return MultiBlockHandler.TESLA_COIL.tryFormStructure(this);
    }

    @Override
    public void deformStructure() {
        MultiBlockHandler.TESLA_COIL.deformStructure(this);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return isFormed;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos().add(-3,0,-3),getPos().add(3,3,3));
    }
}
