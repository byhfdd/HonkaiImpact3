package indi.byhfdd.bh3rd;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
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
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import indi.byhfdd.bh3rd.loader.ItemLoader;
import indi.byhfdd.bh3rd.loader.OreLoader;
import indi.byhfdd.bh3rd.loader.WeaponLoader;
import indi.byhfdd.bh3rd.shop.ContainerCrystalShop;

public class CommonProxy implements IGuiHandler {

    public void preInit(FMLPreInitializationEvent event) {
        new CreativeTabsLoader();
        ItemLoader.init();
        WeaponLoader.init();
        CardLoader.main();
        BlockLoader.init();
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, this);
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

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == ContainerCrystalShop.GUI_ID && world.getBlock(x, y, z) == BlockLoader.CrystalShop) {
            return new ContainerCrystalShop(player, world, x, y, z);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
