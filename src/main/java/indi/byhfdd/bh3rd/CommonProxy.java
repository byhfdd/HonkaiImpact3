package indi.byhfdd.bh3rd;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import indi.byhfdd.bh3rd.craft.*;
import indi.byhfdd.bh3rd.loader.*;


public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        new CreativeTabsLoader();
        ItemLoader.init();
        WeaponLoader.init();
        BlockLoader.init();
    }

    public void init(FMLInitializationEvent event) {
        OreLoader.init();
        ManaCraftTableRecipes.init();
        MetalCraftTableRecipes.init();
        AddRecipe.init();
        CastingRecipes.init();
        BossStoreAdd.init();
        ManaGravityWellDataAdd.init();
        DarkMagicCoreAdd.init();
//        new FoodLoader();
    }

    public void postInit(FMLPostInitializationEvent event) { }
}
