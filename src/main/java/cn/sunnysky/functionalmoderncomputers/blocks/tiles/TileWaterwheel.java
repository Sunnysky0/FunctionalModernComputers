package cn.sunnysky.functionalmoderncomputers.blocks.tiles;

import cn.sunnysky.functionalmoderncomputers.blocks.Waterwheel;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class TileWaterwheel extends TileEntity implements IEnergyProvider, ITickable {

    public EnergyStorage storage = new EnergyStorage((int) Math.pow(2,15));

    public static final int maxExtract = 1024;

    private final IEnergyStorage energyCap;

    public TileWaterwheel() {
        energyCap = CapabilityUtil.newEnergyProviderCap(storage);
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
        return storage.extractEnergy(maxExtract, simulate);
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
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(energyCap);
        return super.getCapability(capability,facing);
    }

    @Override
    public void update() {
        int xCoord = getPos().getX();
        int yCoord = getPos().getY();
        int zCoord = getPos().getZ();

        if (getWorld().getTileEntity(new BlockPos(xCoord,yCoord + 1,zCoord))
                instanceof TileWaterProvider)
            storage.receiveEnergy(TileWaterwheel.maxExtract,false);

        if ((storage.getEnergyStored() > 0)) {
            for (int i = 0; i < 6; i++){
                TileEntity tile = getWorld().getTileEntity(
                        new BlockPos(xCoord + ForgeDirection.getOrientation(i).offsetX,
                                yCoord + ForgeDirection.getOrientation(i).offsetY,
                                zCoord + ForgeDirection.getOrientation(i).offsetZ)
                );
                if (tile instanceof IEnergyReceiver) {
                    storage.extractEnergy(
                            ((IEnergyReceiver) tile).receiveEnergy(
                                    ForgeDirection.cast(ForgeDirection.getOrientation(i).getOpposite()),
                                    storage.extractEnergy(storage.getMaxExtract(), true),
                                    false),
                            false);
                }
            }
        }
    }
}
