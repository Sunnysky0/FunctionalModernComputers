package cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks;

import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

public interface NetworkMember extends ITickable {
    List<NetworkMember> connectors = new ArrayList<>(6);

    BlockNetwork<? extends NetworkMember> affiliation();

    void changeAffiliation(BlockNetwork<? extends NetworkMember> newAffiliation);
    void disconnect();
}
