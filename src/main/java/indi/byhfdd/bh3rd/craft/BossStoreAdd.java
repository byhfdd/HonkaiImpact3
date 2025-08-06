package indi.byhfdd.bh3rd.craft;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.core.Icommodity;
import project.studio.manametalmod.rpg.BossStore;

public class BossStoreAdd extends BossStore {//魔王商店
    public static void main() {
        Items1.add(new Icommodity(new ItemStack(ItemLoader.EinsteinRingMagnet), 100));
        Items1.add(new Icommodity(new ItemStack(ItemLoader.SuperconductingMetallicHydrogen), 24));
        Items1.add(new Icommodity(new ItemStack(ItemLoader.FluidAlloyBlock), 50));
        Items2.add(new Icommodity(new ItemStack(ItemLoader.EinsteinRingMagnet), 100));
        Items2.add(new Icommodity(new ItemStack(ItemLoader.SuperconductingMetallicHydrogen), 24));
        Items2.add(new Icommodity(new ItemStack(ItemLoader.FluidAlloyBlock), 50));
    }
}
