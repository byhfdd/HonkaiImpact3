package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.cuisine.FoodCore;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.ManaMetalAPI;
import project.studio.manametalmod.food_collection.FoodCollectionCore;
import project.studio.manametalmod.magic.magicItem.MagicItemType;
import project.studio.manametalmod.produce.cuisine.PotRecipe;

public class FoodLoader {
    public static Item ItemFoodCore = new FoodCore();
    int i=0;
    public FoodLoader() {
        GameRegistry.registerItem(ItemFoodCore, "ItemFoodCore");
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(ItemFoodCore, 1, i++), new int[]{0, 2, 0, 0, 0, 0, 0, 0}, 30, new Object[]{Items.potato, Items.carrot}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(ItemFoodCore, 1, i++), new int[]{0, 2, 0, 0, 0, 0, 0, 0}, 30, new Object[]{Items.potato, Items.carrot}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(ItemFoodCore, 1, i++), new int[]{0, 2, 0, 0, 0, 0, 0, 0}, 30, new Object[]{Items.potato, Items.carrot}));
        FoodCollectionCore.addItemEffect(new ItemStack(ItemFoodCore, 1, 0), MagicItemType.manaMax, 50.0F, (short)5);

    }
}
