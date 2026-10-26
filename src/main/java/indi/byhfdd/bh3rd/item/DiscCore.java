package indi.byhfdd.bh3rd.item;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

public class DiscCore extends ItemRecord {

    public DiscCore(String name) {
        super(name);
        this.setUnlocalizedName(name);// 未本地化名字
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
        GameRegistry.registerItem(this, name, "bh3rd");
    }

    public ResourceLocation getRecordResource(String name) {
        return new ResourceLocation("bh3rd", name);
    }// 资源
}
