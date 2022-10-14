package cn.sunnysky.functionalmoderncomputers.items.itemgroups;

import cn.sunnysky.functionalmoderncomputers.CommonProxy;
import cn.sunnysky.functionalmoderncomputers.registry.ItemHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FMCMainGroup extends CreativeTabs {
    public static final FMCMainGroup FMC_MAIN_GROUP = new FMCMainGroup("fmc.main");

    public FMCMainGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return ItemHandler.AND_GATE.getDefaultInstance();
    }

    @Override
    public boolean hasSearchBar() {
        return false;
    }
}
