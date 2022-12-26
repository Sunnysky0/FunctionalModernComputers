package cn.sunnysky.functionalmoderncomputers.blocks.multiblocks;

import cn.sunnysky.functionalmoderncomputers.api.BlockWithDirection;
import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks.StructureTile;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class StructureBlock<TE extends StructureTile> extends BlockWithDirection implements IWithTileEntity<TE> {
    public static final PropertyBool IS_FORMED = PropertyBool.create("is_formed");

    public StructureBlock(Material materialIn, String name) {
        super(materialIn, name);
        setDefaultState(blockState.getBaseState().withProperty(IS_FORMED,false));
    }

    public void onForm(World world, BlockPos pos, IBlockState state){
        world.setBlockState(pos, state.withProperty(IS_FORMED, true));
    }

    public void onDeform(World world, BlockPos pos, IBlockState state){
        world.setBlockState(pos,state.withProperty(IS_FORMED,false));
    }

    public boolean isFormed(IBlockState state){
        return state.getValue(IS_FORMED);
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,FACING,IS_FORMED);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        if(state.getValue(IS_FORMED))
            return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
        else return EnumBlockRenderType.MODEL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return !blockState.getValue(IS_FORMED);
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return !blockState.getValue(IS_FORMED);
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return !blockState.getValue(IS_FORMED);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return !state.getValue(IS_FORMED);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return !state.getValue(IS_FORMED);
    }
}
