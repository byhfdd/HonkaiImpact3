package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.block.ActivityBlockBh3rd;
import indi.byhfdd.bh3rd.block.BlockCore;

public class BlockLoader {

    public static BlockCore EtherPhlogistonore01, EtherPhlogistonore02, EtherPhlogistonore03;
    public static ActivityBlockBh3rd HiLoveELF;
    public static ActivityBlockBh3rd SchicksalImperative;
    public static ActivityBlockBh3rd ReignSolaris;
    public static void init() {
        HiLoveELF = new ActivityBlockBh3rd("HiLoveELF");
        SchicksalImperative = new ActivityBlockBh3rd("SchicksalImperative");
        ReignSolaris = new ActivityBlockBh3rd("ReignSolaris");
        GameRegistry.registerBlock(HiLoveELF, "HiLoveELF");
        GameRegistry.registerBlock(SchicksalImperative, "SchicksalImperative");
        GameRegistry.registerBlock(ReignSolaris, "ReignSolaris");

        EtherPhlogistonore01 = new BlockCore("EtherPhlogistonore01", 20, 20, ItemLoader.EtherPhlogiston);
        EtherPhlogistonore02 = new BlockCore("EtherPhlogistonore02", 20, 20, ItemLoader.EtherPhlogiston);
        EtherPhlogistonore03 = new BlockCore("EtherPhlogistonore03", 20, 20, ItemLoader.EtherPhlogiston);
    }
}
