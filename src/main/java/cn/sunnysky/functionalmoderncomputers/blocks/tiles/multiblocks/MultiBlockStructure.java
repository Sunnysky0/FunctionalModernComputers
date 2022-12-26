package cn.sunnysky.functionalmoderncomputers.blocks.tiles.multiblocks;

import cn.sunnysky.functionalmoderncomputers.blocks.multiblocks.StructureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class MultiBlockStructure {
    private Map<BlockPos, Block> structure = new HashMap<>();

    public void addComponent(Block blockType, BlockPos relativePos){
        structure.put(relativePos, blockType);
    }

    public boolean verifyStructure(StructureCoreTile core){
        World world = core.getWorld();
        BlockPos startPoint = core.getPos();

        final boolean[] isFormed = {true};

        structure.forEach(new BiConsumer<BlockPos, Block>() {
            @Override
            public void accept(BlockPos blockPos, Block block) {
                IBlockState state = world.getBlockState(startPoint.add(blockPos));
                Block localBlockType = state.getBlock();

                if (localBlockType != block){
                    isFormed[0] = false;
                }
            }
        });

        return isFormed[0];
    }

    public boolean tryFormStructure(StructureCoreTile core){
        try {
            if (!verifyStructure(core))
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        if(core.isFormed && !core.needsToResume())
            return true;

        World world = core.getWorld();
        BlockPos startPoint = core.getPos();

        for (BlockPos relativePos : structure.keySet()){
            final BlockPos localPos = startPoint.add(relativePos);
            IBlockState state = world.getBlockState(localPos);
            Block blockType = state.getBlock();

            if (blockType instanceof ITileEntityProvider){
                TileEntity te = world.getTileEntity(localPos);

                if (te instanceof IStructureTile)
                    ((IStructureTile) te).setAffiliation(core);

                if (te != null) {
                    te.markDirty();
                }
            }

            if (blockType instanceof StructureBlock)
                ((StructureBlock<?>) blockType).onForm(world,localPos,state);

        }

        return true;
    }

    public void deformStructure(StructureCoreTile core){

        World world = core.getWorld();
        BlockPos startPoint = core.getPos();

        for (BlockPos relativePos : structure.keySet()){
            final BlockPos localPos = startPoint.add(relativePos);
            IBlockState state = world.getBlockState(localPos);
            Block blockType = state.getBlock();

            if (blockType instanceof ITileEntityProvider){
                TileEntity te = world.getTileEntity(localPos);

                if (te instanceof IStructureTile)
                    ((IStructureTile) te).setAffiliation(null);

                if (te != null) {
                    te.markDirty();
                }
            }

            if (blockType instanceof StructureBlock)
                ((StructureBlock<?>) blockType).onDeform(world,localPos,state);

        }
    }


}
