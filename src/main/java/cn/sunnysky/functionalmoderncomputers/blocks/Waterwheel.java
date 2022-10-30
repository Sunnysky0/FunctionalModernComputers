package cn.sunnysky.functionalmoderncomputers.blocks;

import cn.sunnysky.functionalmoderncomputers.api.BlockWithDirection;
import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWaterwheel;
import cn.sunnysky.functionalmoderncomputers.items.itemblocks.ItemWaterwheel;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import cn.sunnysky.functionalmoderncomputers.registry.ItemHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class Waterwheel extends BlockWithDirection implements IWithTileEntity<TileWaterwheel> {
    private static final String name = "waterwheel";

    public Waterwheel() {
        super(Material.IRON, name);
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
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

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileWaterwheel tile = (TileWaterwheel) world.getTileEntity(pos);
            player.sendMessage(new TextComponentString("Stored energy: " + tile.getEnergyStored(side) + " RF"));
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        final TileWaterwheel waterwheel = new TileWaterwheel();
        waterwheel.setWorld(worldIn);
        return waterwheel;
    }

    @Override
    protected void registerItemBlock(String name) {
        ItemHandler.register(new ItemWaterwheel(this).setRegistryName(name));
    }
}
