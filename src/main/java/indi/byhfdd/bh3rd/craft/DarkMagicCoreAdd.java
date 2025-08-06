package indi.byhfdd.bh3rd.craft;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import project.studio.manametalmod.ManaMetalAPI;

public class DarkMagicCoreAdd {//黑暗复制
    public static  void init(){
        ManaMetalAPI.addDarkItem(ItemLoader.IllusoryRubine, 1024);
        ManaMetalAPI.addDarkItem(ItemLoader.IllusoryAmethyst, 1024);
        ManaMetalAPI.addDarkItem(ItemLoader.IllusoryAshcrystal, 1024);
        ManaMetalAPI.addDarkItem(ItemLoader.IllusoryCyanite, 1024);
        ManaMetalAPI.addDarkItem(ItemLoader.EtherPhlogiston,32);
    }
}
