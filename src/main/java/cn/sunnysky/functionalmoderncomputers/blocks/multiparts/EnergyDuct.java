package cn.sunnysky.functionalmoderncomputers.blocks.multiparts;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class EnergyDuct extends BlockDuct{
    public static final String name = "energy_duct";

    public EnergyDuct() {
        super(Material.IRON, true, name);
    }

    @Override
    public void registerModel() {
        proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public boolean isCapableWith(IBlockAccess blockAccess, IBlockState state, BlockPos pos, EnumFacing facing) {
        return state.getBlock() instanceof EnergyDuct;
    }
}
