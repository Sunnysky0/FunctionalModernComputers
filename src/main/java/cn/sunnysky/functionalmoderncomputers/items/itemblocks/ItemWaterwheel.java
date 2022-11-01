package cn.sunnysky.functionalmoderncomputers.items.itemblocks;

import cn.sunnysky.functionalmoderncomputers.CommonProxy;
import cn.sunnysky.functionalmoderncomputers.blocks.Waterwheel;
import cn.sunnysky.functionalmoderncomputers.blocks.tiles.TileWaterwheel;
import cn.sunnysky.functionalmoderncomputers.util.EnumColor;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemWaterwheel extends ItemBlock {

    public ItemWaterwheel(Waterwheel block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(EnumColor.GREY + CommonProxy.localize("tooltip.waterwheel"));
        tooltip.add(EnumColor.AQUA + CommonProxy.localize("tooltip.power_generation") + ": " +
                TileWaterwheel.maxExtract + " rf/t");
    }
}
