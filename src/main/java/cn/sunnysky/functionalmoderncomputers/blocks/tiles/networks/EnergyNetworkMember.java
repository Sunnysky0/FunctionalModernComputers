package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileElectricAppliance;
import cn.sunnysky.functionalmoderncomputers.util.ForgeDirection;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.HashSet;
import java.util.Set;

public class EnergyNetworkMember extends TileElectricAppliance implements NetworkMember, IEnergyReceiver, IEnergyProvider, ITickable {
    private Set<TileEntity> receivers = new HashSet<>();
    private Set<TileEntity> senders = new HashSet<>();

    private EnergyNetwork affiliation;

    public EnergyNetworkMember(){

    }

    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        if (TileElectricAppliance.canReceiveEnergy(world, world.getBlockState(neighbor),neighbor, EnumFacing.EAST ))
            receivers.add(world.getTileEntity(neighbor));
        if (TileElectricAppliance.canSendEnergy(world, world.getBlockState(neighbor),neighbor, EnumFacing.EAST ))
            senders.add(world.getTileEntity(neighbor));

        if (affiliation != null && affiliation instanceof EnergyNetwork){
            affiliation.receivers.addAll(receivers);
            affiliation.senders.addAll(senders);
        }


    }

    @Override
    public int getEnergyStored(EnumFacing enumFacing) {
        if (affiliation == null) return 0;
        return affiliation.getStorage().getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing enumFacing) {
        if (affiliation == null) return 0;
        return affiliation.getStorage().getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing enumFacing) {
        return true;
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int i, boolean b) {
        if (affiliation == null) return 0;
        return affiliation.getStorage().receiveEnergy(i,b);
    }

    @Override
    public int extractEnergy(EnumFacing enumFacing, int i, boolean b) {
        if (affiliation == null) return 0;
        return affiliation.getStorage().extractEnergy(i,b);
    }

    @Override
    public void update() {
        if (affiliation == null){
            int xCoord = getPos().getX();
            int yCoord = getPos().getY();
            int zCoord = getPos().getZ();

            boolean merge = false;

            for (int i = 0; i < 6; i++){
                TileEntity tile = getWorld().getTileEntity(
                        new BlockPos(xCoord + ForgeDirection.getOrientation(i).offsetX,
                                yCoord + ForgeDirection.getOrientation(i).offsetY,
                                zCoord + ForgeDirection.getOrientation(i).offsetZ)
                );

                if ( tile == null )
                    continue;

                if (tile instanceof EnergyNetworkMember)
                    if (((EnergyNetworkMember) tile).affiliation() != null)
                        if(!merge){
                            this.affiliation = ((EnergyNetworkMember) tile).affiliation;
                            merge = true;
                        }else {
                            if (this.affiliation != null)
                                this.affiliation.merge(((EnergyNetworkMember) tile).affiliation);
                        }
            }

            if (affiliation == null)
                affiliation = new EnergyNetwork();
            return;
        }
        this.sendEnergy(affiliation.getStorage());
    }

    @Override
    public BlockNetwork affiliation() {
        return affiliation;
    }
}
