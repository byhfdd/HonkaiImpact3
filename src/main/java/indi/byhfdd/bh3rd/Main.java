package indi.byhfdd.bh3rd;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = Main.MODID,
    name = Main.NAME,
    version = Main.VERSION,
    dependencies = Main.dependencies,
    acceptedMinecraftVersions = "[1.7.10]")

public class Main {

    public static final String MODID = "bh3rd";
    public static final String NAME = "Honkai Impact 3";
    public static final String VERSION = "2.3.4";
    public static final String dependencies = "required-after:manametalmod;after:Muya";
    @SidedProxy(clientSide = "indi.byhfdd.bh3rd.ClientProxy", serverSide = "indi.byhfdd.bh3rd.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance(Main.MODID)
    public static Main instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
