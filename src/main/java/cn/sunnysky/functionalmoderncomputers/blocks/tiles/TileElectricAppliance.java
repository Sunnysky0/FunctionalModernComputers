package cn.sunnysky.functionalmoderncomputers.blocks.tiles;

import cn.sunnysky.functionalmoderncomputers.util.ForgeDirection;
import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class TileElectricAppliance extends TileEntity implements IEnergyHandler {
    public EnergyStorage storage;

    public int energy(){ return storage.getEnergyStored(); }

    public int maxEnergy(){ return storage.getMaxEnergyStored(); }

    protected void sendEnergy(){
        int xCoord = getPos().getX();
        int yCoord = getPos().getY();
        int zCoord = getPos().getZ();

        if ((storage.getEnergyStored() > 0)) {
            for (int i = 0; i < 6; i++){
                TileEntity tile = getWorld().getTileEntity(
                        new BlockPos(xCoord + ForgeDirection.getOrientation(i).offsetX,
                                yCoord + ForgeDirection.getOrientation(i).offsetY,
                                zCoord + ForgeDirection.getOrientation(i).offsetZ)
                );

                if ( tile == null )
                    continue;

                //Using FE
                if (tile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.byIndex(i).getOpposite())){
                    IEnergyStorage target = tile.getCapability(CapabilityEnergy.ENERGY,EnumFacing.byIndex(i));

                    assert target != null;
                    if(target.canReceive()){
                        storage.extractEnergy(
                                target.receiveEnergy(
                                        storage.extractEnergy(storage.getMaxExtract(), true),
                                        false),
                                false
                        );
                        this.markDirty();
                        continue;
                    }
                }

                //Using RF
                if (tile instanceof IEnergyReceiver) {

                    storage.extractEnergy(
                            ((IEnergyReceiver) tile).receiveEnergy(
                                    ForgeDirection.cast(ForgeDirection.getOrientation(i).getOpposite()),
                                    storage.extractEnergy(storage.getMaxExtract(), true),
                                    false),
                            false);
                    this.markDirty();
                }
            }
        }
    }
}
