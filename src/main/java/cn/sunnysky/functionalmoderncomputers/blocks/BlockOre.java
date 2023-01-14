package cn.sunnysky.functionalmoderncomputers.blocks;

import cn.sunnysky.functionalmoderncomputers.api.BlockBase;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class BlockOre extends BlockBase {
    public BlockOre(String name) {
        super(Material.ROCK,name);
        setHardness(3F);
        setResistance(5F);
        setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    @Override
    public void registerModel() {
        proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
