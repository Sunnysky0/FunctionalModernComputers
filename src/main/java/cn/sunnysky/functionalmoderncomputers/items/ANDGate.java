package cn.sunnysky.functionalmoderncomputers.items;


import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.creativetab.CreativeTabs;

public class ANDGate extends ItemStaticRender {
    private static final String name = "andgate";

    public ANDGate() {
        super(name);
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

}
