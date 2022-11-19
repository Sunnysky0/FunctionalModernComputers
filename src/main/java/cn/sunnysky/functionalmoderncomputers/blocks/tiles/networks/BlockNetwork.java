package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class BlockNetwork<TE extends NetworkMember> {
    protected final List<TE> members;
    protected final List<Set<TileEntity>> interfaces;

    protected BlockNetwork() {
        this.members = new ArrayList<>();
        this.interfaces = new ArrayList<>();
    }

    public void join(TE tile){
        members.add(tile);
    }

    public void remove(TE tile){
        members.remove(tile);
    }

    public void merge(BlockNetwork<TE> target){

    }
}
