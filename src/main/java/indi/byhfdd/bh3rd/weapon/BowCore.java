package indi.byhfdd.bh3rd.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import project.studio.manametalmod.Lapuda.ItemToolBowLaputa;
import project.studio.manametalmod.utils.ToolCore;

import java.util.List;

public class BowCore extends ItemToolBowLaputa {

    public final String name;

    public BowCore(String name, double BowAttack, ToolMaterial ToolMaterial, int needLV) {
        super(ToolMaterial, name, BowAttack);
        this.name = name;
        this.attack = BowAttack;
        this.needLV = needLV;
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        this.setUnlocalizedName(name);
        this.setTextureName("bh3rd:" + name);
        ToolCore.listAllBow.remove(this);

    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer Player, List List, boolean par4) {
        List.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("tips." + name));
        super.addInformation(item, Player, List, par4);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(this.getIconString());
    }
}
