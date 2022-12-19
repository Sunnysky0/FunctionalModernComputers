package cn.sunnysky.functionalmoderncomputers.blocks;

import cn.sunnysky.functionalmoderncomputers.api.BlockWithDirection;
import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWindmillBase;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class WindmillBase extends BlockWithDirection implements IWithTileEntity<TileWindmillBase> {
    public WindmillBase() {
        super(Material.IRON, "windmill_base");
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    @Override
    public void registerModel() {
        proxy.registerItemModelRenderer(
                Item.getItemFromBlock(this),
                0,
                "inventory");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    public Class<TileWindmillBase> tileClass() {
        return TileWindmillBase.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileWindmillBase();
    }
}
