package cn.sunnysky.functionalmoderncomputers.blocks.multiparts;

import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileElectricAppliance;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileEnergyDuct;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class EnergyDuct extends BlockMultipart implements IWithTileEntity<TileEnergyDuct> {
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
        return TileElectricAppliance.canConnectEnergy(blockAccess,state,pos,facing);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEnergyDuct)
            playerIn.sendMessage(
                    new TextComponentString(
                            "Energy stored: " + ((TileEnergyDuct) te).getEnergyStored(facing) + " / " + ((TileEnergyDuct) te).getMaxEnergyStored(facing)
                    )
            );
        return true;
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
        super.onPlayerDestroy(worldIn, pos, state);
    }

    @Nonnull
    @Override
    public Class<TileEnergyDuct> tileClass() {
        return TileEnergyDuct.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEnergyDuct();
    }
}
