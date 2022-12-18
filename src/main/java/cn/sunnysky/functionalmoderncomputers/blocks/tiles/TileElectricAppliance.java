package cn.sunnysky.functionalmoderncomputers.blocks.tiles;

import cn.sunnysky.functionalmoderncomputers.blocks.multiparts.EnergyDuct;
import cn.sunnysky.functionalmoderncomputers.util.CapabilityUtil;
import cn.sunnysky.functionalmoderncomputers.util.ForgeDirection;
import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public abstract class TileElectricAppliance extends TileEntity implements IEnergyHandler {
    public EnergyStorage storage;

    protected IEnergyStorage energyCap;

    protected TileElectricAppliance() {
        energyCap = CapabilityUtil.newEnergyProviderCap(storage);
    }

    public int energy(){
        if (storage != null)
            return storage.getEnergyStored();
        return 0;
    }

    public int maxEnergy(){
        if (storage != null)
            return storage.getMaxEnergyStored();
        return 0;
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

    public static boolean canConnectEnergy(IBlockAccess blockAccess, IBlockState state, BlockPos pos, EnumFacing facing){
        if (state.getBlock() instanceof EnergyDuct) return true;
        else if (blockAccess.getTileEntity(pos) instanceof IEnergyHandler) return true;
        else {
            final TileEntity tile = blockAccess.getTileEntity((pos));
            return tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
        }
    }

    public static boolean canSendEnergy(IBlockAccess blockAccess, IBlockState state, BlockPos pos, EnumFacing facing){
        if (blockAccess.getTileEntity(pos) instanceof IEnergyProvider) return true;
        else {
            final TileEntity tile = blockAccess.getTileEntity((pos));
            if (tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
                if (tile.getCapability(CapabilityEnergy.ENERGY,facing.getOpposite()).canExtract())
                    return true;
                else
                    return false;
            else return false;
        }
    }

    public static boolean canReceiveEnergy(IBlockAccess blockAccess, IBlockState state, BlockPos pos, EnumFacing facing){
        if (blockAccess.getTileEntity(pos) instanceof IEnergyReceiver) return true;
        else {
            final TileEntity tile = blockAccess.getTileEntity((pos));
            if (tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
                if (tile.getCapability(CapabilityEnergy.ENERGY,facing.getOpposite()).canReceive())
                    return true;
                else
                    return false;
            else return false;
        }
    }

    protected void sendEnergy(){
        sendEnergy(this.storage);
    }
    protected void sendEnergy(EnergyStorage storage,Class<?>... excludedTypes){
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

                boolean flag = false;
                for (Class<?> type:
                     excludedTypes) {
                    if (type.isInstance(tile)) {
                        flag = true;
                        break;
                    }
                }

                if (flag)
                    continue;

                //Using FE
                if (tile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.byIndex(i).getOpposite())){
                    IEnergyStorage target = tile.getCapability(CapabilityEnergy.ENERGY,EnumFacing.byIndex(i));

                    assert target != null;
                    if(target.canReceive()){
                        storage.extractEnergy(
                                target.receiveEnergy(
                                        storage.extractEnergy(storage.getMaxExtract() / 6, true),
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
                                    storage.extractEnergy(storage.getMaxExtract() / 6, true),
                                    false),
                            false);
                    this.markDirty();
                }
            }
        }
    }
}
