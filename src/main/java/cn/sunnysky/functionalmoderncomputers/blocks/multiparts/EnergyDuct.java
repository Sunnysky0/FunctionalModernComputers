package cn.sunnysky.functionalmoderncomputers.blocks.multiparts;

import cn.sunnysky.functionalmoderncomputers.api.IWithTileEntity;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileElectricAppliance;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.networks.EnergyNetworkMember;
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

public class EnergyDuct extends BlockMultipart implements IWithTileEntity {
    public static final String name = "energy_duct";
    private EnergyNetworkMember tileEntity;

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
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof EnergyNetworkMember)
            ((EnergyNetworkMember) te).onNeighborChange(world, pos, neighbor);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof EnergyNetworkMember)
            playerIn.sendMessage(
                    new TextComponentString(
                            "Energy stored: " + ((EnergyNetworkMember) te).getEnergyStored(facing) + " / " + ((EnergyNetworkMember) te).getMaxEnergyStored(facing)
                    )
            );
        return true;
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
        if (tileEntity != null)
            tileEntity.disconnect();
        super.onPlayerDestroy(worldIn, pos, state);
    }

    @Nonnull
    @Override
    public Class tileClass() {
        return EnergyNetworkMember.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        tileEntity = new EnergyNetworkMember();
        return tileEntity;
    }
}
