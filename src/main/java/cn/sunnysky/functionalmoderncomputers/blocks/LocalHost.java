package cn.sunnysky.functionalmoderncomputers.blocks;

import cn.sunnysky.functionalmoderncomputers.api.BlockWithDirection;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class LocalHost extends BlockWithDirection {
    public LocalHost() {
        super(Material.IRON, "localhost");
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    @Override
    public void registerModel() {
        proxy.registerItemModelRenderer(
                Item.getItemFromBlock(this),
                0,
                "inventory");
    }

}
