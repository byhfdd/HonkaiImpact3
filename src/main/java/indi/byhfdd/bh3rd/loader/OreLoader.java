package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.ore.AncientGenerate;
import indi.byhfdd.bh3rd.ore.HeavenGenerate;

public class OreLoader {

    public static void init() {
        GameRegistry.registerWorldGenerator(new HeavenGenerate(), 0);
        GameRegistry.registerWorldGenerator(new AncientGenerate(), 0);
    }
}
