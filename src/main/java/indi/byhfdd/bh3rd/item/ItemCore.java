package indi.byhfdd.bh3rd.item;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.Main;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.api.IQualityItem;
import project.studio.manametalmod.api.Quality;

public class ItemCore extends Item implements IQualityItem {

    public ItemCore(String name) {
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setUnlocalizedName(name);// 未本地化名字
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
        GameRegistry.registerItem(this, name, Main.MODID);
    }

    public Quality getQuality(ItemStack item) {
        return Quality.Epic;
    }
}
