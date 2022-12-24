package cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks;

import cn.sunnysky.functionalmoderncomputers.blocks.multiblocks.StructureBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

//TODO Multiblock structure
public class StructureCoreTile extends StructureTile implements ITickable {

    public  boolean checkStructureValidity() {
        return MultiBlockHandler.TESLA_COIL.verifyStructure(this);
    }

    public void changeDirection(EnumFacing facing){

    }

    public StructureBlock<?> getBlock(){return (StructureBlock<?>) getBlockType();}

    @Override
    public void update() {

    }

    @Override
    public boolean isAffiliatedTo(StructureCoreTile coreTile) {
        return affiliation.get() == coreTile;
    }

    @Override
    public void setAffiliation(StructureCoreTile coreTile) {
        if (coreTile != this)
            throw new RuntimeException("Cannot add a core to a structure that already has one");
        super.setAffiliation(coreTile);
    }
}
