package indi.byhfdd.bh3rd.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import project.studio.manametalmod.Lapuda.ItemToolSickleLapuda;
import project.studio.manametalmod.utils.ToolCore;

import java.util.List;

public class SickleCore extends ItemToolSickleLapuda {
    public final String name;
    public SickleCore(String name, float attack, ToolMaterial ToolMaterial, int needLV) {
        super(ToolMaterial, attack,name );
        this.name = name;
        this.attack = attack;
        this.needLV = needLV;
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        this.setUnlocalizedName(name);
        ToolCore.listAllSickle.remove(this);

    }
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer Player, List List, boolean par4) {
        List.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("tips."+name));
        super.addInformation(item, Player, List, par4);
    }
}
