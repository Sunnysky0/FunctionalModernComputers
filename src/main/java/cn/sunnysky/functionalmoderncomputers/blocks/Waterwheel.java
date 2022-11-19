package cn.sunnysky.functionalmoderncomputers.blocks;

import cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers;
import cn.sunnysky.functionalmoderncomputers.GuiProxy;
import cn.sunnysky.functionalmoderncomputers.api.BlockWithDirection;
import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWaterwheel;
import cn.sunnysky.functionalmoderncomputers.items.itemblocks.ItemWaterwheel;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import cn.sunnysky.functionalmoderncomputers.registry.ItemHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class Waterwheel extends BlockWithDirection implements IWithTileEntity<TileWaterwheel> {
    private static final String name = "waterwheel";

    public Waterwheel() {
        super(Material.IRON, name);
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
        this.setHardness(22);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public void registerModel() {
        proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Nonnull
    @Override
    public Class<TileWaterwheel> tileClass() {
        return TileWaterwheel.class;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        final TileWaterwheel waterwheel = new TileWaterwheel();
        waterwheel.setWorld(worldIn);
        return waterwheel;
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(FunctionalModernComputers.INSTANCE, GuiProxy.WATERWHEEL_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    protected void registerItemBlock(String name) {
        ItemHandler.register(new ItemWaterwheel(this).setRegistryName(name));
    }
}
