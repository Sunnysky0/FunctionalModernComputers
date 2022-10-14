package cn.sunnysky.functionalmoderncomputers.api;

import cn.sunnysky.functionalmoderncomputers.registry.BlockHandler;
import cn.sunnysky.functionalmoderncomputers.registry.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.MOD_ID;

public abstract class BlockBase extends Block implements IWithModel{
    public final String registryName;
    public BlockBase(Material materialIn, String name) {
        super(materialIn);
        registryName = name;
        this.setTranslationKey(name);
        this.setRegistryName(MOD_ID,name);
        BlockHandler.register(this);
        registerItemBlock(name);
    }

    protected void registerItemBlock(String name){
        ItemHandler.register(new ItemBlock(this).setRegistryName(name));
    }
}
