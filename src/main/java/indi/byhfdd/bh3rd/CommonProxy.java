package indi.byhfdd.bh3rd;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import indi.byhfdd.bh3rd.craft.AddRecipe;
import indi.byhfdd.bh3rd.craft.BossStoreAdd;
import indi.byhfdd.bh3rd.craft.CastingRecipes;
import indi.byhfdd.bh3rd.craft.DarkMagicCoreAdd;
import indi.byhfdd.bh3rd.craft.ManaCraftTableRecipes;
import indi.byhfdd.bh3rd.craft.ManaGravityWellDataAdd;
import indi.byhfdd.bh3rd.craft.MetalCraftTableRecipes;
import indi.byhfdd.bh3rd.event.EventLeafDrop;
import indi.byhfdd.bh3rd.item.card.CardLoader;
import indi.byhfdd.bh3rd.item.food.FoodLoader;
import indi.byhfdd.bh3rd.loader.BlockLoader;
import indi.byhfdd.bh3rd.loader.CombatEffectLoader;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import indi.byhfdd.bh3rd.loader.CrystalCurrencyLoader;
import indi.byhfdd.bh3rd.loader.GuiLoader;
import indi.byhfdd.bh3rd.loader.ItemLoader;
import indi.byhfdd.bh3rd.loader.OreLoader;
import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        new CreativeTabsLoader();
        ItemLoader.init();
        WeaponLoader.init();
        CardLoader.main();
        BlockLoader.init();
        CombatEffectLoader.init();
        CrystalCurrencyLoader.init();
    }

    public void init(FMLInitializationEvent event) {
        initGuiLoader();
        OreLoader.init();
        ManaCraftTableRecipes.init();
        MetalCraftTableRecipes.init();
        AddRecipe.init();
        CastingRecipes.init();
        BossStoreAdd.init();
        ManaGravityWellDataAdd.init();
        DarkMagicCoreAdd.init();
        new FoodLoader();
        MinecraftForge.EVENT_BUS.register(new EventLeafDrop());

    }

    public void postInit(FMLPostInitializationEvent event) {}

    protected void initGuiLoader() {
        GuiLoader.init();
    }
}
