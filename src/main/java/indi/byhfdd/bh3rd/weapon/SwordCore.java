package indi.byhfdd.bh3rd.weapon;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import project.studio.manametalmod.items.ItemToolSwordBase;
import project.studio.manametalmod.utils.ToolCore;

public class SwordCore extends ItemToolSwordBase {

    public final String name;

    public SwordCore(String name, double attack, ToolMaterial ToolMaterial, int needLV) {
        super(name, attack, ToolMaterial);
        this.name = name;
        this.attack = attack;
        this.needLV = needLV;
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        this.setUnlocalizedName(name);
        this.ToolMaterial = ToolMaterial;
        ToolCore.listAllSword.remove(this);
        this.setTextureName("bh3rd:" + name);

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
