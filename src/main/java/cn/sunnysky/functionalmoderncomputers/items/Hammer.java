package cn.sunnysky.functionalmoderncomputers.items;

import cn.sunnysky.functionalmoderncomputers.api.References;
import cn.sunnysky.functionalmoderncomputers.items.itemgroups.FMCMainGroup;
import cn.sunnysky.functionalmoderncomputers.util.ItemNBTUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;

import javax.annotation.Nullable;
import java.util.Objects;

public class Hammer extends ItemStaticRender{
    private final int durability = 64;

    public Hammer() {
        super("hammer");
        setMaxStackSize(1);
        setCreativeTab(FMCMainGroup.FMC_MAIN_GROUP);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return ItemNBTUtil.getInt(stack, References.NBT_DAMAGE) > 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return (double) (ItemNBTUtil.getInt(stack,References.NBT_DAMAGE)) / (double) this.durability;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack container = itemStack.copy();
        damage(container,1,null);
        return container;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return stack.getItem() instanceof Hammer && !stack.isEmpty();
    }

    private void damage(ItemStack stack, int amount, @Nullable EntityPlayer player)
    {
        if(amount <= 0)
            return;

        int curDamage = ItemNBTUtil.getInt(stack, References.NBT_DAMAGE);
        curDamage += amount;

        if(curDamage >= this.durability)
        {
            if(player!=null)
            {
                player.renderBrokenItemStack(stack);
                player.addStat(Objects.requireNonNull(StatList.getObjectBreakStats(this)));
            }
            stack.shrink(1);
            return;
        }
        ItemNBTUtil.setInt(stack, References.NBT_DAMAGE, curDamage);
    }
}
