package indi.byhfdd.bh3rd.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import project.studio.manametalmod.items.ItemToolKatanaLapuda;
import project.studio.manametalmod.utils.ToolCore;

import java.util.List;

public class KatanaCore extends ItemToolKatanaLapuda {
    public final String name;
    public KatanaCore(String name, double attack, ToolMaterial ToolMaterial,int needLV) {
        super(name, attack, ToolMaterial);
        this.name = name;
        this.attack = attack;
        this.needLV = needLV;
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        this.setUnlocalizedName(name);
        this.ToolMaterial = ToolMaterial;
        ToolCore.listAllKatana.remove(this);

    }
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer Player, List List, boolean par4) {
        List.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("tips."+name));
        super.addInformation(item, Player, List, par4);
    }

}
