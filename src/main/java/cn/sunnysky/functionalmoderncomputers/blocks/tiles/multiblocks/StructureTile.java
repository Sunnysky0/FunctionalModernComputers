package cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks;

import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileElectricAppliance;
import cn.sunnysky.functionalmoderncomputers.util.CapabilityUtil;
import net.minecraft.util.EnumFacing;

public class StructureTile extends TileElectricAppliance implements IStructureTile {

    public StructureTile() {
        this.energyCap = CapabilityUtil.newEnergyInsulatorCap(storage);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return 0;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return false;
    }

}
