package indi.byhfdd.bh3rd.item.card;

import indi.byhfdd.bh3rd.loader.BlockLoader;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft10;
import project.studio.manametalmod.items.itemBag.ItemBasicBagWeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 抽象基类：所有 PrecisionCard 的父类
public abstract class CardCore extends ItemBasicBagWeight {

    public CardCore(int weight, String name) {
        super(weight, name);
        this.setTextureName("bh3rd:" + name);
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
    }

    // 子类必须实现：提供自己的武器列表
    protected abstract List<ItemStack> getWeaponItems();

    protected abstract List<Integer> getWeaponProbabilities();

    // 每次调用时动态构建 commonItems（确保所有物品已注册）
    private List<ItemStack> getCommonItems() {
        return Arrays.asList(
            new ItemStack(ItemCraft10.testItemBagRoll), // 卷轴随机包
            new ItemStack(CardLoader.SeedCard), // 种子包
            new ItemStack(ItemLoader.StaminaPotion), // 体力药水
            new ItemStack(ItemLoader.SodaPop), // 汽水
            new ItemStack(ItemLoader.Watermelon), // 西瓜
            new ItemStack(ItemLoader.SoulShards), // 灵魂碎片
            new ItemStack(ItemLoader.SoulGeminiShards), // 双子灵魂碎片
            new ItemStack(ItemLoader.SoulCrystallization), // 灵魂结晶
            new ItemStack(ItemLoader.SoulGeminiCrystallization), // 双子灵魂结晶
            new ItemStack(ItemLoader.GoldBox), // 金币礼盒
            new ItemStack(ItemLoader.BaseChip), // 基础学习芯片
            new ItemStack(ItemLoader.AdvancedChips), // 进阶学习芯片
            new ItemStack(ItemLoader.SeniorChips), // 高级学习芯片
            new ItemStack(ItemLoader.PremiumChips), // 特级学习芯片
            new ItemStack(BlockLoader.HiLoveELF), // 方块
            new ItemStack(BlockLoader.SchicksalImperative), // 方块
            new ItemStack(BlockLoader.ReignSolaris), // 方块
            new ItemStack(CardLoader.DiscCard)// 唱片包
        );
    }

    private List<Integer> getCommonProbabilities() {
        return Arrays.asList(
            70, // 卷轴
            70, // 种子包
            20, // 体力药水
            30, // 汽水
            50, // 西瓜
            40, // 灵魂碎片
            30, // 双子灵魂碎片
            20, // 灵魂结晶
            10, // 双子灵魂结晶
            70, // 金币礼盒
            40, // 基础学习芯片
            30, // 进阶学习芯片
            20, // 高级学习芯片
            10, // 特级学习芯片
            10, // 方块
            10, // 方块
            10, // 方块
            50// 唱片包
        );
    }

    @Override
    public List<ItemStack> getBagItem() {
        List<ItemStack> result = new ArrayList<>();
        result.addAll(getWeaponItems());
        result.addAll(getCommonItems()); // 动态构建，安全！
        return result;
    }

    @Override
    public List<Integer> getItemProbability() {
        List<Integer> result = new ArrayList<>();
        result.addAll(getWeaponProbabilities());
        result.addAll(getCommonProbabilities());
        return result;
    }
}
