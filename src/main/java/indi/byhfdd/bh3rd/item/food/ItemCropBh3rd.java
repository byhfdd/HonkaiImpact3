package indi.byhfdd.bh3rd.item.food;

import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import project.studio.manametalmod.api.Quality;
import project.studio.manametalmod.produce.farming.ItemCropSpecial;

public class ItemCropBh3rd extends ItemCropSpecial {

    public ItemCropBh3rd(int food, float heal, String name, Quality quality) {
        super(food, heal, name, quality);
        this.setUnlocalizedName(name);
        this.setHasSubtypes(true);
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 设置创造模式物品栏分类
    }
}
