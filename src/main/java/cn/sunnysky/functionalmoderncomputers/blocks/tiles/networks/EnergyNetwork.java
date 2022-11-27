package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.tileentity.TileEntity;

import java.util.HashSet;
import java.util.Set;

public class EnergyNetwork extends BlockNetwork<EnergyNetworkMember>{
    public Set<TileEntity> receivers = new HashSet<>();
    public Set<TileEntity> senders = new HashSet<>();

    private EnergyStorage storage = new EnergyStorage(Integer.MAX_VALUE,256);

    protected EnergyNetwork() {
        super();

        this.interfaces.add(receivers);
        this.interfaces.add(senders);
    }

    protected void addEnergy(int energy){
        storage.setEnergyStored(storage.getEnergyStored() + energy);
    }

    @Override
    public BlockNetwork<EnergyNetworkMember> newInstance() {
        return new EnergyNetwork();
    }

    @Override
    protected void refresh() {
        storage = storage.setCapacity(members.size() * 1024);
    }

    public EnergyStorage getStorage() {
        return storage;
    }

    @Override
    public void merge(BlockNetwork<EnergyNetworkMember> target) {
        super.merge(target);
        final EnergyNetwork affiliation = (EnergyNetwork) members.get(0).affiliation();
        if (affiliation != null) {
            affiliation.addEnergy(this.storage.getEnergyStored());
            affiliation.addEnergy(((EnergyNetwork) target).storage.getEnergyStored());
        }

    }


}
