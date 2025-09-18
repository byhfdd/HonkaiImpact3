package indi.byhfdd.bh3rd.craft;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.core.Icommodity;
import project.studio.manametalmod.rpg.BossStore;

public class BossStoreAdd {// 魔王商店

    public static void init() {
        BossStore.Items1.add(new Icommodity(new ItemStack(ItemLoader.EinsteinRingMagnet), 100));
        BossStore.Items1.add(new Icommodity(new ItemStack(ItemLoader.SuperconductingMetallicHydrogen), 24));
        BossStore.Items1.add(new Icommodity(new ItemStack(ItemLoader.FluidAlloyBlock), 50));
        BossStore.Items2.add(new Icommodity(new ItemStack(ItemLoader.EinsteinRingMagnet), 100));
        BossStore.Items2.add(new Icommodity(new ItemStack(ItemLoader.SuperconductingMetallicHydrogen), 24));
        BossStore.Items2.add(new Icommodity(new ItemStack(ItemLoader.FluidAlloyBlock), 50));
    }
}
