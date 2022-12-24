package cn.sunnysky.functionalmoderncomputers.blocks.multiblocks;

import cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks.MultiBlockHandler;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks.StructureCoreTile;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks.TileTeslaCoil;
import cn.sunnysky.functionalmoderncomputers.client.render.tesr.TeslaCoilRenderer;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.proxy;

public class TeslaCoilCore extends StructureBlock<TileTeslaCoil> {
    public TeslaCoilCore() {
        super(Material.IRON, "tesla_coil");
        this.setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean b = MultiBlockHandler.TESLA_COIL.tryFormStructure((StructureCoreTile) worldIn.getTileEntity(pos));
        if (!b)
            playerIn.sendMessage(new TextComponentString("Invalid structure"));
        return true;
    }

    @Override
    public void registerModel() {
        proxy.registerItemModelRenderer(
                Item.getItemFromBlock(this),
                0,
                "inventory");

        ClientRegistry.bindTileEntitySpecialRenderer(tileClass(),new TeslaCoilRenderer());
    }



    @Nonnull
    @Override
    public Class<TileTeslaCoil> tileClass() {
        return TileTeslaCoil.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileTeslaCoil();
    }
}
