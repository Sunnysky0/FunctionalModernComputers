package cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks;

import cn.sunnysky.functionalmoderncomputers.blocks.multiblocks.StructureBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

//TODO Multiblock structure
public abstract class StructureCoreTile extends StructureTile implements ITickable {

    public boolean isFormed = false;

    public boolean needsToResume() {
        return resumeFlag;
    }

    private boolean resumeFlag = true;

    public abstract boolean formStructure();

    public abstract void deformStructure();

    public void changeDirection(EnumFacing facing){

    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (storage != null)
            storage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (storage != null)
            storage.writeToNBT(compound);
        return compound;
    }

    public StructureBlock<?> getBlock(){return (StructureBlock<?>) getBlockType();}

    @Override
    public void update() {
        isFormed = formStructure();

        if (!isFormed)
            deformStructure();
    }

    @Override
    public boolean isAffiliatedTo(StructureCoreTile coreTile) {
        return affiliation.get() == coreTile;
    }

    @Override
    public void setAffiliation(StructureCoreTile coreTile) {
        if (coreTile == null) {
            isFormed = false;
            return;
        }
        if (coreTile != this)
            throw new RuntimeException("Cannot add a core to a structure that already has one");
        super.setAffiliation(coreTile);
    }
}
