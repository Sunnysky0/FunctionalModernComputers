package cn.sunnysky.functionalmoderncomputers.blocks.multiparts;

import cn.sunnysky.functionalmoderncomputers.api.BlockBase;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockDuct extends BlockBase
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    private final boolean canDrop;

    protected BlockDuct(Material materialIn, boolean canDrop, String name)
    {
        super(materialIn,name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
        this.canDrop = canDrop;
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(NORTH, canDuctConnectTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(SOUTH, canDuctConnectTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST,  canDuctConnectTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(EAST,  canDuctConnectTo(worldIn, pos, EnumFacing.EAST));
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return !this.canDrop ? Items.AIR : super.getItemDropped(state, rand, fortune);
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockAccess.getBlockState(pos.offset(side)).getBlock() != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    public abstract boolean isCapableWith(IBlockAccess blockAccess, IBlockState state, BlockPos pos, EnumFacing facing);

    protected boolean canSilkHarvest()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case CLOCKWISE_180:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        switch (mirrorIn)
        {
            case LEFT_RIGHT:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
            default:
                return super.withMirror(state, mirrorIn);
        }
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos offset = pos.offset(facing);
        return isCapableWith(world, world.getBlockState(offset), offset, facing.getOpposite());
    }

    public boolean canDuctConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir)
    {
        BlockPos other = pos.offset(dir);
        IBlockState state = world.getBlockState(other);
        return state.getBlock().canBeConnectedTo(world, other, dir.getOpposite()) || isCapableWith(world, state, other, dir.getOpposite());
    }
}

