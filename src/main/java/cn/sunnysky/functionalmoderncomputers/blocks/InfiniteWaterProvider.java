package cn.sunnysky.functionalmoderncomputers.blocks;

import cn.sunnysky.functionalmoderncomputers.api.BlockBase;
import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWaterProvider;
import cn.sunnysky.functionalmoderncomputers.items.itemblocks.ItemInfiniteWaterProvider;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import cn.sunnysky.functionalmoderncomputers.registry.ItemHandler;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class InfiniteWaterProvider extends BlockBase implements IWithTileEntity<TileWaterProvider> {

    public InfiniteWaterProvider() {
        super(Material.IRON, "water_provider");
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    @Override
    public void registerModel() {
        proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    protected void registerItemBlock(String name) {
        ItemHandler.register(new ItemInfiniteWaterProvider(this).setRegistryName("water_provider"));
    }

    @Nonnull
    @Override
    public Class<TileWaterProvider> tileClass() {
        return TileWaterProvider.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileWaterProvider();
    }
}
