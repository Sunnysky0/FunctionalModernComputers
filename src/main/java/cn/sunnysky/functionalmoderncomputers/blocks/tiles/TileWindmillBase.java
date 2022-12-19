package cn.sunnysky.functionalmoderncomputers.blocks.tiles;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileWindmillBase extends TileElectricAppliance implements IEnergyProvider, ITickable {
    public TileWindmillBase() {
        storage = new EnergyStorage((int) Math.pow(4,8),64);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract,simulate);
    }

    @Override
    public void update() {
        int xCoord = getPos().getX();
        int yCoord = getPos().getY();
        int zCoord = getPos().getZ();

        if (getWorld().getTileEntity(new BlockPos(xCoord,yCoord + 1,zCoord))
                instanceof TileWindmill)
            storage.receiveEnergy(storage.getMaxReceive(),false);

        this.sendEnergy();
    }
}
