package cn.sunnysky.functionalmoderncomputers.api;

import cn.sunnysky.functionalmoderncomputers.registry.ItemHandler;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers.MOD_ID;

public abstract class ItemBase extends Item implements IWithModel{
    public final String registryName;
    public ItemBase(String name){
        registryName = name;
        ItemHandler.register(this);
        this.setTranslationKey(name);
        this.setRegistryName(new ResourceLocation(MOD_ID, name));
    }

}
