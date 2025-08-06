package indi.byhfdd.bh3rd.craft;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.Lapuda.LapudaCore;
import project.studio.manametalmod.ManaMetalAPI;
import project.studio.manametalmod.ManaMetalMod;
import project.studio.manametalmod.core.RecipesOreTable;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft10;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft2;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft9;

public class MetalCraftTableRecipes {//金属融合
    public static void steRecipes() {
        ManaMetalAPI.MetalCraftTableRecipes.add(new RecipesOreTable(new ItemStack(ItemCraft10.UltimatePrism),new ItemStack(ItemCraft10.UltimatePrism),new ItemStack(LapudaCore.ingotSkyPower),new ItemStack(ManaMetalMod.MetalEnergy06),new ItemStack(ItemCraft2.ColorPrism),new ItemStack(ManaMetalMod.MetalEnergy06),new ItemStack(LapudaCore.ingotSkyPower),new ItemStack(ItemCraft10.UltimatePrism),new ItemStack(ItemCraft10.UltimatePrism),new ItemStack(ItemLoader.ShapedSpaceLens)));
        ManaMetalAPI.MetalCraftTableRecipes.add(new RecipesOreTable(new ItemStack(ManaMetalMod.MetalEnergy05, 1),new ItemStack(ManaMetalMod.MetalEnergy05, 1),new ItemStack(ManaMetalMod.ingotTitanium, 1),new ItemStack(ItemCraft9.ingotEnderGold,1),new ItemStack(ItemCraft10.ItemGlassBottles,1,0),new ItemStack(ItemCraft9.ingotEnderGold,1),new ItemStack(ManaMetalMod.ingotTitanium, 1),new ItemStack(ManaMetalMod.MetalEnergy05, 1),new ItemStack(ManaMetalMod.MetalEnergy05, 1),new ItemStack(ItemLoader.SuperconductingMetallicHydrogen,2)));
        ManaMetalAPI.MetalCraftTableRecipes.add(new RecipesOreTable(new ItemStack(ItemCraft10.ItemAlloys, 1, 2), new ItemStack(ItemCraft10.ItemAlloys, 1, 3),"ingotMana",new ItemStack(ManaMetalMod.MetalEnergy03, 1),new ItemStack(ItemCraft10.ItemAlloys, 1, 1),new ItemStack(ManaMetalMod.MetalEnergy03, 1),"ingotMana",new ItemStack(ItemCraft10.ItemAlloys, 1, 3),new ItemStack(ItemCraft10.ItemAlloys, 1, 2),new ItemStack(ItemLoader.FluidAlloyBlock,1)));
    }
}
