package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BlockNetwork<TE extends NetworkMember> {
    protected final List<TE> members;
    protected final List<Set<TileEntity>> interfaces;
    protected final World world;

    protected BlockNetwork(World world) {
        this.members = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.world = world;
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

    public abstract BlockNetwork<TE> newInstance(World world);

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
        BlockNetwork<TE> newNetwork = newInstance(world);

        newNetwork.joinAll(members);
        newNetwork.joinAll(target.members);

        for (TE te : members)
            te.changeAffiliation(newNetwork);

        for (TE te : target.members)
            te.changeAffiliation(newNetwork);
    }

    //TODO: Split a network by certain regex
    public @Nullable List<BlockNetwork<TE>> split(TE regex){
        List<TE> connectedFaces = (List<TE>) regex.connectors;
        BlockPos[] connectionCache = new BlockPos[connectedFaces.size()];

        AtomicInteger ic = new AtomicInteger(-1);
        connectedFaces.forEach(e -> connectionCache[ic.incrementAndGet()] = ((TileEntity) e).getPos());

        if (!members.remove(regex))
            return null;

        BlockPos[] memCache = new BlockPos[members.size()];
        for (int i = 0;i < members.size();i++)
            if (members.get(i) instanceof TileEntity)
                memCache[i] = ((TileEntity) members.get(i)).getPos();
            else
                memCache[i] = null;



        invalidate();

        for (BlockPos o : memCache){
            TileEntity te = world.getTileEntity(o);
            if (te instanceof NetworkMember)
                ((NetworkMember) te).update();
        }


        List<BlockNetwork<TE>> result = new ArrayList<>();
        for (BlockPos bp : connectionCache)
            if (world.getTileEntity(bp) instanceof NetworkMember)
                result.add((BlockNetwork<TE>) ((NetworkMember) world.getTileEntity(bp)).affiliation());

        return result;
    }
}
