package cn.sunnysky.functionalmoderncomputers.api;


import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import javax.annotation.Nonnull;

public interface IWithTileEntity<TE extends TileEntity> extends ITileEntityProvider {
    @Nonnull
    Class<TE> tileClass();
}
