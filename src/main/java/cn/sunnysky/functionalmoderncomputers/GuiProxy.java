package cn.sunnysky.functionalmoderncomputers;

import cn.sunnysky.functionalmoderncomputers.inventory.IInteractiveUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {
    public static final int WATERWHEEL_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IInteractiveUI)
            return ((IInteractiveUI) te).newContainer(player,ID,x,y,z);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IInteractiveUI)
            return ((IInteractiveUI) te).newGui(player,ID,x,y,z);
        return null;
    }
}
