package cn.sunnysky.functionalmoderncomputers.blocks.tiles;

import cn.sunnysky.functionalmoderncomputers.client.gui.impl.GuiWaterwheel;
import cn.sunnysky.functionalmoderncomputers.inventory.IInteractiveUI;
import cn.sunnysky.functionalmoderncomputers.inventory.WaterwheelContainer;
import cn.sunnysky.functionalmoderncomputers.util.CapabilityUtil;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileWaterwheel extends TileElectricAppliance implements IEnergyProvider, ITickable, IInteractiveUI {

    public static final int maxExtract = 64;

    private final IEnergyStorage energyCap;

    public TileWaterwheel() {
        storage = new EnergyStorage((int) Math.pow(4,7),maxExtract);
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
        return energy();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return maxEnergy();
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

        this.sendEnergy();
    }

    @Override
    public Container newContainer(@Nonnull EntityPlayer player, int ID, int... xyz) {
        return new WaterwheelContainer(player.inventory,this);
    }

    @Override
    public GuiContainer newGui(@Nonnull EntityPlayer player, int ID, int... xyz) {
        return new GuiWaterwheel(player.inventory,this);
    }
}
