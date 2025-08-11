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
import project.studio.manametalmod.api.ICoins;
import project.studio.manametalmod.api.MoneySourceType;
import project.studio.manametalmod.entity.nbt.ManaMetalModRoot;

public class GoldCore extends Item implements ICoins {

    int money;

    public GoldCore(String name, int money) {
        super();
        this.money = money;
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setUnlocalizedName(name);// 未本地化名字
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
        GameRegistry.registerItem(this, name, Main.MODID);
    }

    public void addSound(World world, EntityPlayer player) {
        int c = world.rand.nextInt(3);
        switch (c) {
            case 0:
                world.playSoundAtEntity(
                    player,
                    "manametalmod:item.coin1",
                    1.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
                break;
            case 1:
                world.playSoundAtEntity(
                    player,
                    "manametalmod:item.coin2",
                    1.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
                break;
            case 2:
                world.playSoundAtEntity(
                    player,
                    "manametalmod:item.coin3",
                    1.0F,
                    (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
        }

    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (!world.isRemote) {
            ManaMetalModRoot root = MMM.getEntityNBT(player);
            if (root != null && root.money.addMoney(this.money, MoneySourceType.UseItem)) {
                this.addSound(world, player);
                MMM.removePlayerCurrentItem(player);
            }
        }

        return item;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack ItemStack, EntityPlayer EntityPlayer, List List, boolean booleans) {
        List.add("$ " + this.money);
    }

    @Override
    public long getCoinCount() {
        return money;
    }
}
