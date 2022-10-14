package cn.sunnysky.functionalmoderncomputers.items;

import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.creativetab.CreativeTabs;

public class ORGate extends ItemStaticRender{
    private static final String name = "orgate";

    public ORGate() {
        super(name);
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }
}
