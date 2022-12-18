package cn.sunnysky.functionalmoderncomputers.util;

import cn.sunnysky.functionalmoderncomputers.CommonProxy;
import cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

//from Mekansim
public class GeneralUtil {
    /**
     * Gets a ResourceLocation with a defined resource type and name.
     *
     * @param type - type of resource to retrieve
     * @param name - simple name of file to retrieve as a ResourceLocation
     *
     * @return the corresponding ResourceLocation
     */
    public static ResourceLocation getResource(ResourceType type, String name) {
        return new ResourceLocation(FunctionalModernComputers.MOD_ID, type.getPrefix() + name);
    }

    /**
     * Marks the chunk this TileEntity is in as modified. Call this method to be sure NBT is written by the defined tile entity.
     *
     * @param tileEntity - TileEntity to save
     */
    public static void saveChunk(TileEntity tileEntity) {
        if (tileEntity == null || tileEntity.isInvalid() || tileEntity.getWorld() == null) {
            return;
        }
        tileEntity.getWorld().markChunkDirty(tileEntity.getPos(), tileEntity);
    }
    /**
     * Gets a rounded energy display of a defined amount of energy.
     *
     * @param energy - energy to display
     *
     * @return rounded energy display
     */
    public static String getEnergyDisplay(double energy) {
        if (energy == Double.MAX_VALUE) {
            return CommonProxy.localize("gui.infinite");
        }
        return energy + " " + "RF";
    }

    public static String getEnergyDisplay(double energy, double max) {
        if (energy == Double.MAX_VALUE) {
            return CommonProxy.localize("gui.infinite");
        }
        String energyString = getEnergyDisplay(energy);
        String maxString = getEnergyDisplay(max);
        return energyString + "/" + maxString;
    }

    public static TileEntity getTileEntitySafe(IBlockAccess worldIn, BlockPos pos) {
        return worldIn instanceof ChunkCache ? ((ChunkCache) worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
    }

    public static <T extends TileEntity> T getTileEntitySafe(IBlockAccess worldIn, BlockPos pos, Class<T> expectedClass) {
        TileEntity te = getTileEntitySafe(worldIn, pos);
        if (expectedClass.isInstance(te)){
            return expectedClass.cast(te);
        }
        return null;
    }

    /**
     * Gets a tile entity if the location is loaded
     *
     * @param world - world
     * @param pos   - position
     *
     * @return tile entity if found, null if either not found or not loaded
     */
    @Nullable
    public static TileEntity getTileEntity(World world, BlockPos pos) {
        if (world != null && world.isBlockLoaded(pos)) {
            return world.getTileEntity(pos);
        }
        return null;
    }


    /**
     * Dismantles a block, dropping it and removing it from the world.
     */
    public static void dismantleBlock(Block block, IBlockState state, World world, BlockPos pos) {
        block.dropBlockAsItem(world, pos, state, 0);
        world.setBlockToAir(pos);
    }

    /**
     * @param amount   Amount currently stored
     * @param capacity Total amount that can be stored.
     *
     * @return A redstone level based on the percentage of the amount stored.
     */
    public static int redstoneLevelFromContents(double amount, double capacity) {
        double fractionFull = capacity == 0 ? 0 : amount / capacity;
        return MathHelper.floor((float) (fractionFull * 14.0F)) + (fractionFull > 0 ? 1 : 0);
    }

    /**
     * Clamp a double to int without using Math.min due to double representation issues. Primary use: power systems that use int, where Mek uses doubles internally
     *
     * <code>
     * double d = 1e300; // way bigger than longs, so the long should always be what's returned by Math.min System.out.println((long)Math.min(123456781234567812L, d)); //
     * result is 123456781234567808 - 4 less than what you'd expect System.out.println((long)Math.min(123456789012345678L, d)); // result is 123456789012345680 - 2 more
     * than what you'd expect
     * </code>
     *
     * @param d double to clamp
     *
     * @return an int clamped to Integer.MAX_VALUE
     *
     * @see <a href="https://github.com/aidancbrady/Mekanism/pull/5203">Original PR</a>
     */
    public static int clampToInt(double d) {
        if (d < Integer.MAX_VALUE) {
            return (int) d;
        }
        return Integer.MAX_VALUE;
    }

    public enum ResourceType {
        GUI("gui"),
        GUI_ELEMENT("gui/elements"),
        SOUND("sound"),
        RENDER("render"),
        TEXTURE_BLOCKS("textures/blocks"),
        TEXTURE_ITEMS("textures/items"),
        MODEL("models"),
        INFUSE("infuse");

        private String prefix;

        ResourceType(String s) {
            prefix = s;
        }

        public String getPrefix() {
            return prefix + "/";
        }
    }
}
