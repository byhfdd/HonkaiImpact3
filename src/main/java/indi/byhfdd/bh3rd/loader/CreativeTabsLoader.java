package indi.byhfdd.bh3rd.loader;

import net.minecraft.creativetab.CreativeTabs;

import indi.byhfdd.bh3rd.creativetabs.CreativeTabsFMLTutor;

public class CreativeTabsLoader {

    public static CreativeTabs tabFMLTutor;

    public CreativeTabsLoader() {
        tabFMLTutor = new CreativeTabsFMLTutor();
    }
}
