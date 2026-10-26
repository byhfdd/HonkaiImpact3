package indi.byhfdd.bh3rd;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import indi.byhfdd.bh3rd.loader.ClientEffectLoader;
import indi.byhfdd.bh3rd.loader.ClientGuiLoader;
import indi.byhfdd.bh3rd.loader.RendererLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RendererLoader.init();
        ClientEffectLoader.init();
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
    protected void initGuiLoader() {
        ClientGuiLoader.init();
    }
}
