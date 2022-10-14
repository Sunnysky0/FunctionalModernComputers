package cn.sunnysky.functionalmoderncomputers.items.itemblocks;

import cn.sunnysky.functionalmoderncomputers.CommonProxy;
import cn.sunnysky.functionalmoderncomputers.util.EnumColor;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemInfiniteWaterProvider extends ItemBlock {

    public ItemInfiniteWaterProvider(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(EnumColor.GREY + CommonProxy.localize("tooltip.water_provider"));
        tooltip.add(EnumColor.AQUA + CommonProxy.localize("tooltip.usage") + ": " +
            CommonProxy.localize("tooltip.water_provider.usage"));
    }
}
