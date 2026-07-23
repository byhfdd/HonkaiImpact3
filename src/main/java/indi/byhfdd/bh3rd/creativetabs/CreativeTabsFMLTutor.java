package indi.byhfdd.bh3rd.creativetabs;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabsFMLTutor extends CreativeTabs {

    public CreativeTabsFMLTutor() {
        super("Bh3rd");
    }

    @Override
    public Item getTabIconItem() {
        return ItemLoader.Bh3rd;
    }
}
