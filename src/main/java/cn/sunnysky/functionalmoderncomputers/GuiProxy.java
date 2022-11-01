package cn.sunnysky.functionalmoderncomputers;

import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWaterwheel;
import cn.sunnysky.functionalmoderncomputers.client.gui.impl.GuiWaterwheel;
import cn.sunnysky.functionalmoderncomputers.inventory.WaterwheelContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import sun.misc.ClassLoaderUtil;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GuiProxy implements IGuiHandler {
    public static final int WATERWHEEL_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        switch (ID){
            case WATERWHEEL_ID:
                return new WaterwheelContainer(player.inventory, (TileWaterwheel) te);
            default:
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        switch (ID){
            case WATERWHEEL_ID:
                return new GuiWaterwheel(player.inventory, (TileWaterwheel) te);
            default:
                break;
        }
        return null;
    }

    public static class Tuple<V1,V2> {
        V1 var1;
        V2 var2;

        public Tuple(V1 var1, V2 var2) {
            this.var1 = var1;
            this.var2 = var2;
        }
    }
}
