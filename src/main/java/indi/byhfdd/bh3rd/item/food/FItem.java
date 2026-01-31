package indi.byhfdd.bh3rd.item.food;

import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.api.IQualityItem;
import project.studio.manametalmod.api.Quality;

public class FItem extends ItemFood  implements IQualityItem {
    public FItem(int food, float heal, String name) {
        super(food, heal, false);
        this.setUnlocalizedName(name);
        this.setHasSubtypes(true);
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 设置创造模式物品栏分类
    }
    public Quality getQuality(ItemStack item) {
        return Quality.VeryRare;
    }
}
