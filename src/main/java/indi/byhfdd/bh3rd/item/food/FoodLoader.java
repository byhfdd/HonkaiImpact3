package indi.byhfdd.bh3rd.item.food;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.cuisine.FoodCore;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import project.studio.manametalmod.Lapuda.LapudaCore;
import project.studio.manametalmod.ManaMetalAPI;
import project.studio.manametalmod.ManaMetalMod;
import project.studio.manametalmod.api.Quality;
import project.studio.manametalmod.bosssummon.BossSummonCore;
import project.studio.manametalmod.food_collection.FoodCollectionCore;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft10;
import project.studio.manametalmod.magic.magicItem.MagicItemType;
import project.studio.manametalmod.produce.beekeeping.BeekeepingCore;
import project.studio.manametalmod.produce.cuisine.FoodType;
import project.studio.manametalmod.produce.cuisine.PotRecipe;
import project.studio.manametalmod.produce.farming.CropDifficult;
import project.studio.manametalmod.produce.farming.FarmCore;
import project.studio.manametalmod.produce.farming.IWorldGenPlant;
import project.studio.manametalmod.produce.farming.PlantType;
import project.studio.manametalmod.produce.fishing.FishPrey;
import project.studio.manametalmod.produce.fishing.FishSizeType;
import project.studio.manametalmod.produce.fishing.FishingCore;
import project.studio.manametalmod.produce.fishing.WaterType;

public class FoodLoader {

    public static Item FoodMaterials4 = new FItem(1, 1, "FoodMaterials4");

    public static Item FoodMaterials6 = new FItem(1, 1, "FoodMaterials6");

    public static Item FoodMaterials10 = new FItem(1, 1, "FoodMaterials10");

    public static Item FoodMaterials12 = new FItem(1, 1, "FoodMaterials12");
    public static Item FoodMaterials13 = new FItem(1, 1, "FoodMaterials13");
    public static Item FoodMaterials14 = new FItem(1, 1, "FoodMaterials14");
    public static Item FoodMaterials15 = new FItem(1, 1, "FoodMaterials15");
    public static Item FoodMaterials16 = new FItem(1, 1, "FoodMaterials16");
    public static Item FoodMaterials17 = new FItem(1, 1, "FoodMaterials17");
    public static Item FoodMaterials18 = new FItem(1, 1, "FoodMaterials18");

    public static Item FoodCoreLV1 = new FoodCore(5, 3, "FoodCoreLv1");
    public static Item FoodCoreLV2 = new FoodCore(10, 16, "FoodCoreLv2");
    public static Item FoodCoreLV3 = new FoodCore(20, 17, "FoodCoreLv3");
    public static Item FoodCoreLV4 = new FoodCore(25, 9, "FoodCoreLv4");


    public static ItemCropBh3rd FoodMaterials1;
    public static ItemCropBh3rd FoodMaterials2;
    public static ItemCropBh3rd FoodMaterials3;
    public static ItemCropBh3rd FoodMaterials5;
    public static ItemCropBh3rd FoodMaterials7;
    public static ItemCropBh3rd FoodMaterials8;
    public static ItemCropBh3rd FoodMaterials9;
    public static ItemCropBh3rd FoodMaterials11;

    int FishCount = ManaMetalAPI.FishingList.size();

    public FoodLoader() {
        init();
        recipe();
        effect();
    }

    private void init() {
        GameRegistry.registerItem(FoodCoreLV1, "FoodCoreLv1");
        GameRegistry.registerItem(FoodCoreLV2, "FoodCoreLV2");
        GameRegistry.registerItem(FoodCoreLV3, "FoodCoreLV3");
        GameRegistry.registerItem(FoodCoreLV4, "FoodCoreLV4");

        GameRegistry.registerItem(FoodMaterials4, "FoodMaterials4");
        GameRegistry.registerItem(FoodMaterials6, "FoodMaterials6");
        GameRegistry.registerItem(FoodMaterials10, "FoodMaterials10");
        GameRegistry.registerItem(FoodMaterials12, "FoodMaterials12");
        GameRegistry.registerItem(FoodMaterials13, "FoodMaterials13");
        GameRegistry.registerItem(FoodMaterials14, "FoodMaterials14");
        GameRegistry.registerItem(FoodMaterials15, "FoodMaterials15");
        GameRegistry.registerItem(FoodMaterials16, "FoodMaterials16");
        GameRegistry.registerItem(FoodMaterials17, "FoodMaterials17");
        GameRegistry.registerItem(FoodMaterials18, "FoodMaterials18");


    }

    private void recipe() {
        OreDictionary.registerOre(FoodType.grain.getName(), FoodMaterials4);
        OreDictionary.registerOre(FoodType.meatraw.getName(), FoodMaterials6);
        OreDictionary.registerOre(FoodType.fishraw.getName(), FoodMaterials10);
        OreDictionary.registerOre(FoodType.meatraw.getName(), FoodMaterials12);
        OreDictionary.registerOre(FoodType.meatraw.getName(), FoodMaterials13);
        OreDictionary.registerOre(FoodType.egg.getName(), FoodMaterials14);
        OreDictionary.registerOre(FoodType.meatraw.getName(), FoodMaterials15);
        OreDictionary.registerOre(FoodType.meatraw.getName(), FoodMaterials16);
        OreDictionary.registerOre(FoodType.meatraw.getName(), FoodMaterials17);
        OreDictionary.registerOre(FoodType.grain.getName(), FoodMaterials18);


        //食材-种植
        FoodMaterials1 = addCropBh3rd(3, CropDifficult.Base, "FoodMaterials1", 1.0F, FoodType.veggie, 0, 200, Quality.VeryRare);
        FoodMaterials2 = addCropBh3rd(3, CropDifficult.Base, "FoodMaterials2", 1.0F, FoodType.veggie, 0, 200, Quality.VeryRare);
        FoodMaterials3 = addCropBh3rd(3, CropDifficult.Base, "FoodMaterials3", 1.0F, FoodType.veggie, 0, 200, Quality.VeryRare);
        FoodMaterials5 = addCropBh3rd(3, CropDifficult.Base, "FoodMaterials5", 1.0F, FoodType.veggie, 0, 200, Quality.VeryRare);
        FoodMaterials7 = addCropBh3rd(3, CropDifficult.Base, "FoodMaterials7", 1.0F, FoodType.veggie, 0, 200, Quality.VeryRare);
        FoodMaterials8 = addCropBh3rd(3, CropDifficult.Base, "FoodMaterials8", 1.0F, FoodType.veggie, 0, 200, Quality.VeryRare);
        FoodMaterials9 = addCropBh3rd(3, CropDifficult.Base, "FoodMaterials9", 1.0F, FoodType.veggie, 0, 200, Quality.VeryRare);
        FoodMaterials11 = addCropBh3rd(3, CropDifficult.Base, "FoodMaterials11", 1.0F, FoodType.veggie, 0, 200, Quality.VeryRare);
        //LV1
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV1, 1, 0), new int[]{0, 4, 0, 0, 0, 0, 0, 0}, 30, new Object[]{FarmCore.Rice, FarmCore.Rice, FarmCore.Rice, FarmCore.Rice}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV1, 1, 1), new int[]{0, 2, 0, 0, 2, 0, 0, 0}, 30, new Object[]{Items.egg, Items.egg, FarmCore.Rice, FarmCore.Rice}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV1, 1, 2), new int[]{0, 0, 2, 2, 0, 0, 0, 0}, 30, new Object[]{FoodMaterials1, FoodMaterials1, Items.chicken, Items.chicken}));
        //LV2
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 0), new int[]{0, 0, 0, 4, 0, 0, 0, 0}, 30, new Object[]{FoodMaterials2, FoodMaterials2, FoodMaterials2, FoodMaterials2}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 1), new int[]{0, 0, 0, 2, 2, 0, 0, 0}, 30, new Object[]{FoodMaterials3, FoodMaterials3, Items.egg, Items.egg}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 2), new int[]{0, 0, 0, 2, 2, 0, 0, 0}, 30, new Object[]{FarmCore.Tomato, FarmCore.Tomato, Items.egg, Items.egg}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 3), new int[]{0, 2, 2, 0, 0, 0, 0, 0}, 30, new Object[]{FarmCore.Rice, FarmCore.Rice, Items.porkchop, Items.porkchop}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 4), new int[]{0, 2, 0, 2, 0, 0, 0, 0}, 30, new Object[]{FoodMaterials1, FoodMaterials1, FoodMaterials4, FoodMaterials4}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 5), new int[]{0, 0, 2, 2, 0, 0, 0, 0}, 30, new Object[]{FoodMaterials3, FoodMaterials3, Items.porkchop, Items.porkchop}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 6), new int[]{0, 2, 2, 0, 0, 0, 0, 0}, 30, new Object[]{FoodMaterials18, FoodMaterials18, Items.porkchop, Items.porkchop}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 7), new int[]{0, 0, 2, 2, 0, 0, 0, 0}, 30, new Object[]{FarmCore.WhiteRadish, FarmCore.WhiteRadish, Items.porkchop, Items.porkchop}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 8), new int[]{0, 2, 0, 1, 1, 0, 0, 0}, 30, new Object[]{FarmCore.Rice, FarmCore.Rice, Items.egg, FarmCore.Tomato}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 9), new int[]{0, 2, 0, 0, 0, 0, 2, 0}, 30, new Object[]{FoodMaterials18, FoodMaterials18, new ItemStack(FishingCore.fish_M3_Dead, 1, 40), new ItemStack(FishingCore.fish_M3_Dead, 1, 40)}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 10), new int[]{0, 2, 1, 1, 0, 0, 0, 0}, 30, new Object[]{FoodMaterials3, FoodMaterials4, FoodMaterials4, Items.porkchop}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 11), new int[]{0, 2, 0, 0, 1, 0, 0, 1}, 30, new Object[]{FoodMaterials18, FoodMaterials18, Items.egg, Items.milk_bucket}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 12), new int[]{0, 0, 0, 4, 0, 0, 0, 0}, 30, new Object[]{FoodMaterials2, FoodMaterials2, FoodMaterials3, Items.potato}));
        //查看
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 13), new int[]{0, 0, 0, 2, 0, 0, 2, 0}, 30, new Object[]{FoodMaterials1, FoodMaterials3}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 14), new int[]{0, 0, 0, 2, 0, 0, 2, 0}, 30, new Object[]{ new ItemStack(FishingCore.fish_M3_Dead, 1, 118), new ItemStack(FishingCore.fish_M3_Dead, 1, 118), FoodMaterials1, FarmCore.WhiteRadish}));
        //查看
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV2, 1, 15), new int[]{0, 1, 0, 1, 0, 0, 2, 0}, 30, new Object[]{FarmCore.Tomato, FoodMaterials18}));
        //LV3
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 0), new int[]{0, 0, 0, 4, 0, 0, 0, 0}, 40, new Object[]{FoodMaterials5, FoodMaterials5, FoodMaterials5, FoodMaterials5}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 1), new int[]{0, 0, 0, 0, 0, 0, 4, 0}, 40, new Object[]{new ItemStack(FishingCore.fish_M3_Dead, 1, 25), new ItemStack(FishingCore.fish_M3_Dead, 1, 25), new ItemStack(FishingCore.fish_M3_Dead, 1, 25), new ItemStack(FishingCore.fish_M3_Dead, 1, 25)}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 2), new int[]{0, 0, 2, 2, 0, 0, 0, 0}, 40, new Object[]{FoodMaterials6, FoodMaterials6, FoodMaterials3, FoodMaterials3}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 3), new int[]{0, 0, 2, 0, 0, 2, 0, 0}, 40, new Object[]{Items.porkchop, Items.mushroom_stew}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 4), new int[]{0, 2, 1, 1, 0, 0, 0, 0}, 40, new Object[]{FarmCore.Rice, FarmCore.Rice, FoodMaterials1, Items.porkchop}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 5), new int[]{0, 2, 0, 2, 0, 0, 0, 0}, 40, new Object[]{FarmCore.Rice, FarmCore.Rice, ManaMetalMod.Lotus, ManaMetalMod.Lotus}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 6), new int[]{0, 2, 1, 1, 0, 0, 0, 0}, 40, new Object[]{FarmCore.Rice, FarmCore.Rice, Items.porkchop, FarmCore.Onion}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 7), new int[]{0, 2, 0, 0, 0, 0, 2, 0}, 40, new Object[]{new ItemStack(FishingCore.fish_M3_Dead, 1, 25), new ItemStack(FishingCore.fish_M3_Dead, 1, 25), FoodMaterials4, FoodMaterials4}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 8), new int[]{0, 0, 0, 2, 0, 0, 2, 0}, 40, new Object[]{FoodMaterials3, new ItemStack(FishingCore.fish_M3_Dead, 1, 118), new ItemStack(FishingCore.fish_M3_Dead, 1, 118), FoodMaterials7}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 9), new int[]{0, 1, 2, 1, 0, 0, 0, 0}, 40, new Object[]{Items.porkchop, Items.porkchop, FoodMaterials18, FarmCore.Onion}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 10), new int[]{0, 0, 3, 1, 0, 0, 0, 0}, 40, new Object[]{FoodMaterials7, FoodMaterials6, Items.chicken, Items.chicken}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 11), new int[]{0, 0, 2, 2, 0, 0, 0, 0}, 40, new Object[]{FarmCore.Onion, Items.potato, Items.porkchop, Items.porkchop}));
        //查看
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 12), new int[]{0, 2, 0, 1, 1, 0, 0, 0}, 40, new Object[]{FoodMaterials18, FoodMaterials18, Items.egg, "foodSweetBerrie"}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 13), new int[]{0, 0, 1, 3, 0, 0, 0, 0}, 40, new Object[]{Items.porkchop, FoodMaterials2, FoodMaterials2, FoodMaterials7}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 14), new int[]{0, 0, 2, 2, 0, 0, 0, 0}, 40, new Object[]{Items.porkchop, Items.porkchop, FoodMaterials7, "foodBamboo"}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 15), new int[]{0, 2, 0, 2, 0, 0, 0, 0}, 40, new Object[]{"foodSweetBerrie", FarmCore.Rice, FarmCore.Rice, FoodMaterials8}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 15), new int[]{0, 0, 0, 2, 0, 0, 0, 2}, 40, new Object[]{Items.milk_bucket, Items.milk_bucket, FoodMaterials9, FoodMaterials5}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV3, 1, 16), new int[]{0, 2, 0, 2, 0, 0, 0, 0}, 40, new Object[]{FarmCore.Rice, FarmCore.Rice, FoodMaterials8, "foodSweetBerrie"}));
        //LV4
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 0), new int[]{0, 0, 0, 2, 0, 0, 2, 0}, 60, new Object[]{FoodMaterials10, FoodMaterials10, "foodSweetBerrie", "foodSweetBerrie"}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 1), new int[]{0, 0, 0, 3, 1, 0, 0, 0}, 60, new Object[]{FoodMaterials11, FoodMaterials11, "foodBamboo", Items.egg}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 2), new int[]{0, 2, 2, 0, 0, 0, 0, 0}, 60, new Object[]{FoodMaterials12, FoodMaterials12, FarmCore.Rice,FarmCore.Rice}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 3), new int[]{0, 1, 1, 0, 1, 0, 1, 0}, 60, new Object[]{FoodMaterials13, Items.egg, FarmCore.Rice, new ItemStack(FishingCore.fish_M3_Dead, 1, 40)}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 4), new int[]{0, 2, 0, 1, 1, 0, 0, 0}, 60, new Object[]{ManaMetalMod.Lotus, FarmCore.Rice, FarmCore.Rice, FoodMaterials14}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 5), new int[]{0, 0, 2, 2, 0, 0, 0, 0}, 60, new Object[]{FoodMaterials15, FoodMaterials15, FoodMaterials9, FoodMaterials5}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 6), new int[]{0, 0, 1, 3, 0, 0, 0, 0}, 60, new Object[]{FoodMaterials16, FoodMaterials3, FoodMaterials7, FarmCore.Onion}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 7), new int[]{0, 0, 2, 2, 0, 0, 0, 0}, 60, new Object[]{FoodMaterials17, FoodMaterials17, FoodMaterials8, "foodSweetBerrie"}));
        ManaMetalAPI.PotFoodRecipe.add(new PotRecipe(new ItemStack(FoodCoreLV4, 1, 8), new int[]{0, 0, 4, 0, 0, 0, 0, 0}, 60, new Object[]{FoodMaterials12, FoodMaterials15, FoodMaterials16, FoodMaterials17}));
        //食材
        GameRegistry.addShapelessRecipe(new ItemStack(FoodMaterials4), FarmCore.Soy, FarmCore.Soy, FarmCore.Soy);
        GameRegistry.addShapelessRecipe(new ItemStack(FoodMaterials6, 2), Items.chicken);
        GameRegistry.addShapelessRecipe(new ItemStack(FoodMaterials13), Items.gold_nugget, Items.gold_nugget, Items.gold_nugget, Items.gold_nugget);
        ManaMetalAPI.FishingList.add(new FishPrey(new ItemStack(FishingCore.fish_M3_Life, 1, FishCount + 2), FishSizeType.Medium, WaterType.river, 30, false));
        GameRegistry.addShapelessRecipe(new ItemStack(FoodMaterials16), new ItemStack(BossSummonCore.bossItemM, 1, 62), new ItemStack(BossSummonCore.bossItemM, 1, 51));
        GameRegistry.addShapelessRecipe(new ItemStack(FoodMaterials17), Items.glass_bottle, BeekeepingCore.BlockHoney, BeekeepingCore.BlockHoney, BeekeepingCore.BlockHoney, BeekeepingCore.BlockHoney, BeekeepingCore.BlockHoney, BeekeepingCore.BlockHoney, BeekeepingCore.BlockHoney, BeekeepingCore.BlockHoney);
        GameRegistry.addShapelessRecipe(new ItemStack(FoodMaterials15), Blocks.packed_ice, Blocks.packed_ice, Blocks.packed_ice, Blocks.packed_ice, Blocks.packed_ice, Blocks.packed_ice, Blocks.packed_ice, Blocks.packed_ice, ItemCraft10.Biliha.block);
        GameRegistry.addShapelessRecipe(new ItemStack(FoodMaterials12), LapudaCore.ItemBossBloodDragonsSkin, LapudaCore.ItemBossBloodDragonsSkin, LapudaCore.ItemBossBloodDragonsSkin);



    }

    private void effect() {
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV1, 1, 0), MagicItemType.HP, 25.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV1, 1, 1), MagicItemType.magicAttack, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV1, 1, 2), MagicItemType.fatigue, 10.0F, (short) 2);

        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 0), MagicItemType.physicalAttack, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 1), MagicItemType.magicAttack, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 2), MagicItemType.crit, 5.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 3), MagicItemType.avoid, 5.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 4), MagicItemType.magicDefense, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 5), MagicItemType.critDamage, 0.1F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 6), MagicItemType.dropRate, 5.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 7), MagicItemType.healthRecovery, 0.10F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 8), MagicItemType.maxMagicShield, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 9), MagicItemType.maxSP, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 10), MagicItemType.hpReply, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 11), MagicItemType.HP, 100.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 12), MagicItemType.water, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 13), MagicItemType.fatigue, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 14), MagicItemType.health, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV2, 1, 15), MagicItemType.HP, 100.0F, (short) 2);

        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 0), MagicItemType.HP, 100.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 1), MagicItemType.physicalAttack, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 2), MagicItemType.magicAttack, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 3), MagicItemType.crit, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 4), MagicItemType.avoid, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 5), MagicItemType.attackSpeed, 0.1F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 6), MagicItemType.magicDefense, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 7), MagicItemType.critDamage, 0.2F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 8), MagicItemType.dropRate, 10.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 9), MagicItemType.healthRecovery, 0.20F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 10), MagicItemType.maxMagicShield, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 11), MagicItemType.maxSP, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 12), MagicItemType.hpReply, 20.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 13), MagicItemType.HP, 200.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 14), MagicItemType.water, 30.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 15), MagicItemType.fatigue, 30.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 16), MagicItemType.health, 30.0F, (short) 2);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV3, 1, 17), MagicItemType.HP, 200.0F, (short) 2);

        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 0), MagicItemType.pointPower, 10.0F, (short) 1);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 1), MagicItemType.pointAgile, 10.0F, (short) 1);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 2), MagicItemType.pointWisdom, 10.0F, (short) 1);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 3), MagicItemType.pointWis, 10.0F, (short) 1);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 4), MagicItemType.pointCon, 10.0F, (short) 1);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 5), MagicItemType.penetrate, 5.0F, (short) 1);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 6), MagicItemType.penetrate, 5.0F, (short) 1);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 7), MagicItemType.penetrate, 5.0F, (short) 1);
        FoodCollectionCore.addItemEffect(new ItemStack(FoodCoreLV4, 1, 8), MagicItemType.penetrate, 5.0F, (short) 1);
    }

    public static ItemCropBh3rd addCropBh3rd(int LV, CropDifficult type, String name, float food, FoodType foodtype, int water, int money, Quality quality) {
        // 1. 创建方块
        BlockCropBh3rd cropBlock = new BlockCropBh3rd(type, name, LV);
        GameRegistry.registerBlock(cropBlock, "bh3rd:" + name + "_cropBlock");

        // 2. 创建种子
        ItemSeedsBh3rd seed = new ItemSeedsBh3rd(LV, name, cropBlock, PlantType.Crop, type);
        GameRegistry.registerItem(seed, "bh3rd:" + name + "_seed");

        // 3. 创建作物
        float fooddata = 1.0F + (float) LV * 0.5F;
        ItemCropBh3rd crop = new ItemCropBh3rd((int) fooddata, fooddata * 0.5F, name, quality);
        crop.seeds = seed; // 保持兼容
        GameRegistry.registerItem(crop, "bh3rd:" + name + "_crop");

        // 4. OreDict、配方、水、商店
        OreDictionary.registerOre("listAllseed", seed);
        OreDictionary.registerOre("crop" + name, crop);
        OreDictionary.registerOre("food" + name, crop);
        OreDictionary.registerOre("seed" + name, seed);
        if (foodtype != null) OreDictionary.registerOre(foodtype.getName(), crop);
        if (water > 0) ManaMetalAPI.addItemWater(crop, water);
        ManaMetalAPI.WorldGenPlantList.add(new IWorldGenPlant(cropBlock, 8, seed));
        ManaMetalAPI.setItemValue(crop, money);
        FarmCore.SeedList.add(seed);
        GameRegistry.addShapelessRecipe(new ItemStack(seed), new Object[]{crop});

        // 5. 修复 cropBlock 引用
        cropBlock.itemSeed = seed;
        cropBlock.itemCrop = crop;

        return crop;
    }
}
