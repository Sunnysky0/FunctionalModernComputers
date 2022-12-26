package cn.sunnysky.functionalmoderncomputers.blocks;

import cn.sunnysky.functionalmoderncomputers.api.BlockBase;
import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWindmill;
import cn.sunnysky.functionalmoderncomputers.client.render.tesr.WindmillRenderer;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class Windmill extends BlockBase implements IWithTileEntity<TileWindmill> {
    public Windmill() {
        super(Material.IRON, "windmill");
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }



    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
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
    public void registerModel() {
        proxy.registerItemModelRenderer(
                Item.getItemFromBlock(this),
                0,
                "inventory");

        ClientRegistry.bindTileEntitySpecialRenderer(TileWindmill.class, new WindmillRenderer());
    }

    @Nonnull
    @Override
    public Class<TileWindmill> tileClass() {
        return TileWindmill.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileWindmill();
    }
}
