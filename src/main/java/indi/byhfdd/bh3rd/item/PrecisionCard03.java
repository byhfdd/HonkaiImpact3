package indi.byhfdd.bh3rd.item;

import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import indi.byhfdd.bh3rd.loader.ItemLoader;
import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.ManaMetalMod;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft10;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft2;
import project.studio.manametalmod.items.itemBag.ItemBasicBagWeight;

import java.util.ArrayList;
import java.util.List;

public class PrecisionCard03 extends ItemBasicBagWeight {
    public PrecisionCard03( ) {
        super(1, "PrecisionCard03");
        this.setTextureName("bh3rd:PrecisionCard03");//材质路径
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);//物品栏
    }
    public List getBagItem() {
        List list = new ArrayList();
        list.add(new ItemStack(WeaponLoader.WSDFH));
        list.add(new ItemStack(WeaponLoader.ZWZY));
        list.add(new ItemStack(ItemCraft10.testItemBagRoll));//卷轴随机包
        list.add(new ItemStack(ItemCraft2.Itemblack_ball));//黑色珍珠
        list.add(new ItemStack(ItemCraft2.PlatinumCube));//白金魔方
        list.add(new ItemStack(ItemCraft2.SilversmithHammer));//纯银神匠之锤
        list.add(new ItemStack(ManaMetalMod.MetalEnergy03));//高级金属能量
        list.add(new ItemStack(ItemLoader.StaminaPotion));//体力药水
        list.add(new ItemStack(ItemLoader.SodaPop));//汽水
        list.add(new ItemStack(ItemLoader.Watermelon));//西瓜
        list.add(new ItemStack(ItemLoader.SoulShards));//灵魂碎片
        list.add(new ItemStack(ItemLoader.SoulGeminiShards));//双子灵魂碎片
        list.add(new ItemStack(ItemLoader.SoulCrystallization));//灵魂结晶
        list.add(new ItemStack(ItemLoader.SoulGeminiCrystallization));//双子灵魂结晶
        list.add(new ItemStack(ItemLoader.GoldBox));//金币礼盒
        list.add(new ItemStack(ItemLoader.BaseChip));//基础学习芯片
        list.add(new ItemStack(ItemLoader.AdvancedChips));//进阶学习芯片
        list.add(new ItemStack(ItemLoader.SeniorChips));//高级学习芯片
        list.add(new ItemStack(ItemLoader.PremiumChips));//特级学习芯片
        list.add(new ItemStack(ItemLoader.DiscCard));//唱片包
        return list;
    }
    public List<Integer> getItemProbability(){
        List<Integer> list = new ArrayList();
        list.add(5);
        list.add(5);

        list.add(10);
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(20);
        list.add(30);

        list.add(50);
        list.add(40);
        list.add(30);
        list.add(20);
        list.add(50);
        list.add(50);
        list.add(40);
        list.add(30);
        list.add(20);
        list.add(40);
        return list;
    }
}
