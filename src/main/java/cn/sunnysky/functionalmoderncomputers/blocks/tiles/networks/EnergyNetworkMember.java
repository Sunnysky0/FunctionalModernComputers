package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileElectricAppliance;
import cn.sunnysky.functionalmoderncomputers.util.ForgeDirection;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class EnergyNetworkMember extends TileElectricAppliance implements NetworkMember, IEnergyReceiver, IEnergyProvider, ITickable {
    private Set<TileEntity> receivers = new HashSet<>();
    private Set<TileEntity> senders = new HashSet<>();

    private EnergyNetwork affiliation;

    public EnergyNetworkMember(){
        super();
    }

    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        if (world.getTileEntity(neighbor) instanceof EnergyNetworkMember)
            connectors.add(((EnergyNetworkMember) world.getTileEntity(pos)));

        AtomicReference<EnergyNetworkMember> waitingForDisconnection = new AtomicReference<>();

        connectors.forEach(e -> {
            if (e instanceof EnergyNetworkMember)
                if (((EnergyNetworkMember) e).getPos().compareTo(neighbor) == 0)
                    if (!(world.getTileEntity(neighbor) instanceof EnergyNetworkMember))
                        waitingForDisconnection.set((EnergyNetworkMember) e);
        });

        final EnergyNetworkMember energyNetworkMember = waitingForDisconnection.get();
        if (energyNetworkMember != null)
            connectors.remove(energyNetworkMember);

        if (TileElectricAppliance.canReceiveEnergy(world, world.getBlockState(neighbor),neighbor, EnumFacing.EAST ))
            receivers.add(world.getTileEntity(neighbor));
        if (TileElectricAppliance.canSendEnergy(world, world.getBlockState(neighbor),neighbor, EnumFacing.EAST ))
            senders.add(world.getTileEntity(neighbor));

        if (affiliation != null){
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

    //TODO: Write & read NBT data for energy storage

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        //affiliation.getStorage().readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        //affiliation.getStorage().writeToNBT(compound);
        return super.writeToNBT(compound);
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
                            affiliation.join(this);
                            merge = true;
                        }else {
                            if (this.affiliation != null)
                                this.affiliation.merge(((EnergyNetworkMember) tile).affiliation);
                        }
            }

            if (affiliation == null) {
                affiliation = new EnergyNetwork();
                affiliation.join(this);
            }
            return;
        }
        this.affiliation.refresh();
        this.sendEnergy(affiliation.getStorage());
    }

    @Override
    public BlockNetwork affiliation() {
        return affiliation;
    }

    @Override
    public void disconnect() {
        if (affiliation != null)
            affiliation.remove(this);

        if (connectors.size() > 1)
            affiliation.split(this);

        connectors.clear();
        affiliation = null;
    }

    @Override
    public void changeAffiliation(BlockNetwork newAffiliation) {
        if (newAffiliation instanceof EnergyNetwork || newAffiliation == null)
        this.affiliation = (EnergyNetwork) newAffiliation;
    }
}
