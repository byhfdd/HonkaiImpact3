package indi.byhfdd.bh3rd.item.card;

import indi.byhfdd.bh3rd.item.food.FoodLoader;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.items.itemBag.ItemBasicBagWeight;

import java.util.ArrayList;
import java.util.List;

public class SeedCard extends ItemBasicBagWeight {

    public SeedCard() {
        super(1, "SeedCard");
        this.setTextureName("bh3rd:SeedCard");// 材质路径
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
    }

    public List<ItemStack> getBagItem() {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(FoodLoader.FoodMaterials1));
        list.add(new ItemStack(FoodLoader.FoodMaterials2));
        list.add(new ItemStack(FoodLoader.FoodMaterials3));
        list.add(new ItemStack(FoodLoader.FoodMaterials5));
        list.add(new ItemStack(FoodLoader.FoodMaterials7));
        list.add(new ItemStack(FoodLoader.FoodMaterials8));
        list.add(new ItemStack(FoodLoader.FoodMaterials9));
        list.add(new ItemStack(FoodLoader.FoodMaterials11));
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
        return list;
    }
}
