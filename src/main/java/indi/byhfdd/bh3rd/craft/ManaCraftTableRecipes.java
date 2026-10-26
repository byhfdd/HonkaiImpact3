package indi.byhfdd.bh3rd.craft;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.Lapuda.LapudaCore;
import project.studio.manametalmod.ManaMetalAPI;
import project.studio.manametalmod.ManaMetalMod;
import project.studio.manametalmod.core.RecipesOreTable;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft10;

public class ManaCraftTableRecipes {// 魔法工作台

    public static final ItemStack EP = new ItemStack(ItemLoader.EtherPhlogiston);

    public static void init() {
        ManaMetalAPI.ManaCraftTableRecipes.add(
            new RecipesOreTable(
                EP,
                EP,
                EP,
                EP,
                new ItemStack(LapudaCore.ItemPhoenixItems),
                EP,
                EP,
                EP,
                EP,
                new ItemStack(ItemLoader.IllusoryAshcrystal)));
        ManaMetalAPI.ManaCraftTableRecipes.add(
            new RecipesOreTable(
                EP,
                EP,
                EP,
                EP,
                new ItemStack(LapudaCore.ItemDarkDragonSKIN),
                EP,
                EP,
                EP,
                EP,
                new ItemStack(ItemLoader.IllusoryAmethyst)));
        ManaMetalAPI.ManaCraftTableRecipes.add(
            new RecipesOreTable(
                EP,
                EP,
                EP,
                EP,
                new ItemStack(LapudaCore.ItemBossStartDragons),
                EP,
                EP,
                EP,
                EP,
                new ItemStack(ItemLoader.IllusoryCyanite)));
        ManaMetalAPI.ManaCraftTableRecipes.add(
            new RecipesOreTable(
                EP,
                EP,
                EP,
                EP,
                new ItemStack(LapudaCore.ItemBossBloodDragonsSkin),
                EP,
                EP,
                EP,
                EP,
                new ItemStack(ItemLoader.IllusoryRubine)));
        ManaMetalAPI.ManaCraftTableRecipes.add(
            new RecipesOreTable(
                new ItemStack(ManaMetalMod.UnlimitedRing, 1),
                new ItemStack(ManaMetalMod.UnlimitedRing, 1),
                new ItemStack(ManaMetalMod.Antimatter, 1),
                new ItemStack(ItemCraft10.ItemAlloys, 1, 2),
                new ItemStack(ManaMetalMod.MetalEnergy03, 1),
                new ItemStack(ItemCraft10.ItemAlloys, 1, 2),
                new ItemStack(ManaMetalMod.Antimatter, 1),
                new ItemStack(ManaMetalMod.UnlimitedRing, 1),
                new ItemStack(ManaMetalMod.UnlimitedRing, 1),
                new ItemStack(ItemLoader.EinsteinRingMagnet, 2)));
    }

}
