package cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks;

import java.util.concurrent.atomic.AtomicReference;

public interface IStructureTile {
    AtomicReference<StructureCoreTile> affiliation = new AtomicReference<>();

    default boolean isAffiliated() {
        return affiliation.get() != null;
    }

    default boolean isAffiliatedTo(StructureCoreTile coreTile) {
        return getAffiliation() == coreTile;
    }

    default StructureCoreTile getAffiliation() {
        return affiliation.get();
    }

    default void setAffiliation(StructureCoreTile coreTile) throws RuntimeException{
        affiliation.set(coreTile);
    }
}
