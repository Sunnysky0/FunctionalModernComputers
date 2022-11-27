package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class BlockNetwork<TE extends NetworkMember> {
    protected final List<TE> members;
    protected final List<Set<TileEntity>> interfaces;

    protected BlockNetwork() {
        this.members = new ArrayList<>();
        this.interfaces = new ArrayList<>();
    }

    protected void invalidate(){
        members.forEach(te -> te.changeAffiliation(null));
        members.forEach(te -> {
            if (te instanceof TileEntity)
                ((TileEntity) te).invalidate();
        });
        members.clear();
        interfaces.clear();
    }

    public abstract BlockNetwork<TE> newInstance();

    protected abstract void refresh();

    public void join(TE tile){
        members.add(tile);
        refresh();
    }

    public void joinAll(Collection<TE> tiles){ members.addAll(tiles); refresh();}

    public void remove(TE tile){
        members.remove(tile);
        refresh();
    }

    public boolean contains(TE tile){
        return members.contains(tile);
    }

    public void merge(BlockNetwork<TE> target){
        BlockNetwork<TE> newNetwork = newInstance();

        newNetwork.joinAll(members);
        newNetwork.joinAll(target.members);

        for (TE te : members)
            te.changeAffiliation(newNetwork);

        for (TE te : target.members)
            te.changeAffiliation(newNetwork);
    }

    //TODO: Split a network by certain regex
    public void split(TE regex){
        invalidate();
    }
}
