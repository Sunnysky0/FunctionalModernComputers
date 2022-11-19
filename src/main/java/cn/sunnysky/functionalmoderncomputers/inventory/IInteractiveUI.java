package cn.sunnysky.functionalmoderncomputers.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public interface IInteractiveUI {
    Container newContainer(EntityPlayer player,int ID,int... xyz);
    GuiContainer newGui(EntityPlayer player,int ID,int... xyz);
}
