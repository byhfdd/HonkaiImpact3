package indi.byhfdd.bh3rd.item.food;

import net.minecraft.block.Block;

import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import project.studio.manametalmod.produce.farming.CropDifficult;
import project.studio.manametalmod.produce.farming.ItemSeedsSpecial;
import project.studio.manametalmod.produce.farming.PlantType;

public class ItemSeedsBh3rd extends ItemSeedsSpecial {

    public ItemSeedsBh3rd(int LV, String name, Block crop, PlantType type, CropDifficult head) {
        super(LV, name, crop, type, head);
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 设置创造模式物品栏分类
        this.setTextureName("bh3rd:Aeasea_seed"); // 自定义纹理路径
        this.setUnlocalizedName(name + "_seed"); // 自定义语言键
    }

}
