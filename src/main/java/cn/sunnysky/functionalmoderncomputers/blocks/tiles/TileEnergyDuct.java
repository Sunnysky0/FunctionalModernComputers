package cn.sunnysky.functionalmoderncomputers.blocks.tiles;

import cn.sunnysky.functionalmoderncomputers.util.CapabilityUtil;
import cn.sunnysky.functionalmoderncomputers.util.ForgeDirection;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class TileEnergyDuct extends TileElectricAppliance implements IEnergyReceiver, IEnergyProvider, ITickable {

    private ArrayList<TileEnergyDuct> energy_provider_ducts = new ArrayList<>(6);

    public TileEnergyDuct(){
        super();
        this.storage = new EnergyStorage(1024);
        this.energyCap = CapabilityUtil.newEnergyProcessorCap(storage);
    }

    @Override
    public int getEnergyStored(EnumFacing enumFacing) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing enumFacing) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing enumFacing) {
        return true;
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int i, boolean b) {
        return storage.receiveEnergy(i, b);
    }

    @Override
    public int extractEnergy(EnumFacing enumFacing, int i, boolean b) {
        return storage.extractEnergy(i,b);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage .readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage .writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        this.passEnergy();
        this.sendEnergy(storage,TileEnergyDuct.class);
    }

    protected int onEnergyPassed(int i,TileEnergyDuct provider){
        if(!this.energy_provider_ducts.contains(provider))
            this.energy_provider_ducts.add(provider);
        return storage.receiveEnergy(i,false);
    }

    private void passEnergy(){
        int xCoord = getPos().getX();
        int yCoord = getPos().getY();
        int zCoord = getPos().getZ();
        if ((storage.getEnergyStored() > 0)) {
            for (int i = 0; i < 6; i++) {
                TileEntity tile = getWorld().getTileEntity(
                        new BlockPos(xCoord + ForgeDirection.getOrientation(i).offsetX,
                                yCoord + ForgeDirection.getOrientation(i).offsetY,
                                zCoord + ForgeDirection.getOrientation(i).offsetZ)
                );

                if (tile instanceof TileEnergyDuct && !this.energy_provider_ducts.contains(tile))
                    storage.extractEnergy(
                            ((TileEnergyDuct) tile).onEnergyPassed(
                                    storage.getMaxExtract() / 2,
                                    this),
                            false
                    );
            }
        }
    }


}
