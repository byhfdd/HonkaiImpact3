package indi.byhfdd.bh3rd.item;

import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.item.ItemFood;

public class FItem extends ItemFood {
    public FItem(int food, float heal, String name) {
        super(food, heal, false);
        this.setUnlocalizedName(name);
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 设置创造模式物品栏分类
    }
}
