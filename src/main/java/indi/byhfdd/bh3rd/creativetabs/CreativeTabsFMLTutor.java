package indi.byhfdd.bh3rd.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import indi.byhfdd.bh3rd.loader.ItemLoader;

public class CreativeTabsFMLTutor extends CreativeTabs {

    public CreativeTabsFMLTutor() {
        super("Bh3rd");
    }

    @Override
    public Item getTabIconItem() {
        return ItemLoader.Bh3rd;
    }
}
