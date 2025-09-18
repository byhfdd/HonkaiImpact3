package indi.byhfdd.bh3rd.craft;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.ManaMetalMod;
import project.studio.manametalmod.items.craftingRecipes.ManaGravityWellData;
import project.studio.manametalmod.world.WorldBlueCave;
import project.studio.manametalmod.world.nether.NetherCore;

public class ManaGravityWellDataAdd {// 引力井

    public static void init() {
        ItemStack SE = new ItemStack(ManaMetalMod.MetalEnergy05);
        ItemStack AC = new ItemStack(WorldBlueCave.BlockAmethystClusters);
        ItemStack GC = new ItemStack(NetherCore.BlockYellowCluster);
        ManaGravityWellData.list.add(
            new ItemStack[] { AC, GC, AC, GC, GC, AC, GC, AC, SE, SE, SE, SE,
                new ItemStack(ItemLoader.StigmataResonance, 1, 0) });
    }
}
