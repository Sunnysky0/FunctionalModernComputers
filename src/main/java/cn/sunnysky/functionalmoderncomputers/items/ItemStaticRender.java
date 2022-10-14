package cn.sunnysky.functionalmoderncomputers.items;

import cn.sunnysky.functionalmoderncomputers.FunctionalModernComputers;
import cn.sunnysky.functionalmoderncomputers.api.ItemBase;

public class ItemStaticRender extends ItemBase {

    public ItemStaticRender(String name) {
        super(name);
    }

    @Override
    public void registerModel() {
        FunctionalModernComputers.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
