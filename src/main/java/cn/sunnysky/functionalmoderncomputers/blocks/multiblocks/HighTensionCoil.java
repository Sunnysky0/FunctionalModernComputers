package cn.sunnysky.functionalmoderncomputers.blocks.multiblocks;

import cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks.DummyStructureTile;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class HighTensionCoil extends StructureBlock<DummyStructureTile>{
    public HighTensionCoil() {
        super(Material.IRON, "high_tension_coil");
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    @Override
    public void onForm(World world, BlockPos pos, IBlockState state) {
        world.setBlockState(pos, state.withProperty(IS_FORMED, true));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        if(state.getValue(IS_FORMED))
            return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
        else return EnumBlockRenderType.MODEL;
    }


    @Override
    public void registerModel() {
        proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Nonnull
    @Override
    public Class<DummyStructureTile> tileClass() {
        return DummyStructureTile.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new DummyStructureTile();
    }
}
