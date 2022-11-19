package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.tileentity.TileEntity;

import java.util.HashSet;
import java.util.Set;

public class EnergyNetwork extends BlockNetwork<EnergyNetworkMember>{
    public Set<TileEntity> receivers = new HashSet<>();
    public Set<TileEntity> senders = new HashSet<>();

    private EnergyStorage storage = new EnergyStorage((int) Math.pow(2,25),1024);

    protected EnergyNetwork() {
        super();

        this.interfaces.add(receivers);
        this.interfaces.add(senders);
    }

    public EnergyStorage getStorage() {
        return storage;
    }
}
