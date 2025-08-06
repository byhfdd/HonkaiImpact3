package indi.byhfdd.bh3rd.loader;

import indi.byhfdd.bh3rd.creativetabs.CreativeTabsFMLTutor;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabsLoader
{
    public static CreativeTabs tabFMLTutor;

    public CreativeTabsLoader()
    {
        tabFMLTutor = new CreativeTabsFMLTutor();
    }
}

