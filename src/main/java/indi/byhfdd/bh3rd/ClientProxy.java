package indi.byhfdd.bh3rd;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import indi.byhfdd.bh3rd.client.GuiCrystalShop;
import indi.byhfdd.bh3rd.loader.BlockLoader;
import indi.byhfdd.bh3rd.loader.RendererLoader;
import indi.byhfdd.bh3rd.shop.ContainerCrystalShop;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RendererLoader.init();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == ContainerCrystalShop.GUI_ID && world.getBlock(x, y, z) == BlockLoader.CrystalShop) {
            return new GuiCrystalShop(new ContainerCrystalShop(player, world, x, y, z));
        }
        return null;
    }
}
