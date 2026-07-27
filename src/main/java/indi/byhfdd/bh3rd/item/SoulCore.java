package indi.byhfdd.bh3rd.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.Main;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import project.studio.manametalmod.MMM;
import project.studio.manametalmod.entity.nbt.ManaMetalModRoot;
import project.studio.manametalmod.entity.nbt.NbtCareer;

public class SoulCore extends Item {

    int SPC;

    public SoulCore(String name, int SPC) {
        super();
        this.SPC = SPC;
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setUnlocalizedName(name);// 未本地化名字
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
        GameRegistry.registerItem(this, name, Main.MODID);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack ItemStack, EntityPlayer EntityPlayer, List List, boolean booleans) {
        List.add(MMM.getTranslateText("ItemDimensionCrystal.data." + SPC));
    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (!world.isRemote) {
            ManaMetalModRoot root = MMM.getEntityNBT(player);
            if (root != null) {
                NbtCareer var10000 = root.carrer;
                var10000.SpiritualPowerCount += SPC;
                root.carrer.send2();
                MMM.removePlayerCurrentItem(player);
                player.worldObj.playSoundAtEntity(player, MMM.getMODID() + ":bow2", 1.0F, 1.0F);
            }
        }

        return item;
    }
}
