package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.block.BlockCore;
import net.minecraft.block.Block;

public class BlockLoader {
    public static BlockCore EtherPhlogistonore01,EtherPhlogistonore02,EtherPhlogistonore03;
    public static void init(){
        EtherPhlogistonore01 = new BlockCore("EtherPhlogistonore01",20,20,ItemLoader.EtherPhlogiston);
        EtherPhlogistonore02 = new BlockCore("EtherPhlogistonore02",20,20,ItemLoader.EtherPhlogiston);
        EtherPhlogistonore03 = new BlockCore("EtherPhlogistonore03",20,20,ItemLoader.EtherPhlogiston);
    }

    public BlockLoader() {
    }
}
