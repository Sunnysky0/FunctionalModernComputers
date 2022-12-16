package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class EnergyNetwork extends BlockNetwork<EnergyNetworkMember>{
    public Set<TileEntity> receivers = new HashSet<>();
    public Set<TileEntity> senders = new HashSet<>();

    private EnergyStorage storage = new EnergyStorage(Integer.MAX_VALUE,256);

    protected EnergyNetwork(World world) {
        super(world);

        this.interfaces.add(receivers);
        this.interfaces.add(senders);
    }

    protected void addEnergy(int energy){
        storage.setEnergyStored(storage.getEnergyStored() + energy);
    }

    @Override
    public BlockNetwork<EnergyNetworkMember> newInstance(World world) {
        return new EnergyNetwork(world);
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

    @Override
    public List<BlockNetwork<EnergyNetworkMember>> split(EnergyNetworkMember regex) {
        int energy = storage.getEnergyStored();
        AtomicInteger total_size = new AtomicInteger();
        List<BlockNetwork<EnergyNetworkMember>> nts = super.split(regex);

        if (nts == null) {
            FunctionalModernComputers.log.info("Null split list");
            return null;
        }

        nts.forEach(n -> {total_size.addAndGet(n.members.size());});
        nts.forEach(n -> {
            if (n instanceof EnergyNetwork)
                ((EnergyNetwork) n).getStorage().setEnergyStored(n.members.size() / total_size.get() * energy);
        });

        return nts;
    }
}
