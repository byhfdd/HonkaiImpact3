package indi.byhfdd.bh3rd.item;

import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.items.itemBag.ItemBasicBagWeight;

import java.util.ArrayList;
import java.util.List;

public class DiscCard extends ItemBasicBagWeight {

    public DiscCard() {
        super(1, "DiscCard");
        this.setTextureName("bh3rd:DiscCard");// 材质路径
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
    }

    public List<ItemStack> getBagItem() {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(ItemLoader.BCY));
        list.add(new ItemStack(ItemLoader.Befall));
        list.add(new ItemStack(ItemLoader.BHSJDGJ));
        list.add(new ItemStack(ItemLoader.Cyberangel));
        list.add(new ItemStack(ItemLoader.DaCapo));
        list.add(new ItemStack(ItemLoader.DualEgo));
        list.add(new ItemStack(ItemLoader.GirlInside));
        list.add(new ItemStack(ItemLoader.IRAS17514));
        list.add(new ItemStack(ItemLoader.MoonHalo));
        list.add(new ItemStack(ItemLoader.Nightglow));
        list.add(new ItemStack(ItemLoader.Oaths));
        list.add(new ItemStack(ItemLoader.QNZY));
        list.add(new ItemStack(ItemLoader.QYX));
        list.add(new ItemStack(ItemLoader.Reburn));
        list.add(new ItemStack(ItemLoader.Regression));
        list.add(new ItemStack(ItemLoader.ReOracle));
        list.add(new ItemStack(ItemLoader.Rubia));
        list.add(new ItemStack(ItemLoader.Starfall));
        list.add(new ItemStack(ItemLoader.TruE));
        list.add(new ItemStack(ItemLoader.WLZJ));
        list.add(new ItemStack(ItemLoader.XYNXSZR));
        list.add(new ItemStack(ItemLoader.LZ));
        list.add(new ItemStack(ItemLoader.ZCFZQ));
        list.add(new ItemStack(ItemLoader.NoCeiling));
        return list;
    }

    public List<Integer> getItemProbability() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        return list;
    }
}
