package cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks;

public class TileTeslaCoil extends StructureCoreTile {

    @Override
    public boolean formStructure() {
        return MultiBlockHandler.TESLA_COIL.tryFormStructure(this);
    }

    @Override
    public void deformStructure() {
        MultiBlockHandler.TESLA_COIL.deformStructure(this);
    }
}
