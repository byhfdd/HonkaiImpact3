package indi.byhfdd.bh3rd.item;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.Main;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import project.studio.manametalmod.MMM;
import project.studio.manametalmod.entity.nbt.ManaMetalModRoot;

public class ChipCore extends Item {

    int CExps;
    int Exps;

    public ChipCore(String name, int CExps, int Exps) {
        super();
        this.CExps = CExps;
        this.Exps = Exps;
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setUnlocalizedName(name);// 未本地化名字
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
        GameRegistry.registerItem(this, name, Main.MODID);
    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (!world.isRemote) {
            ManaMetalModRoot root = MMM.getEntityNBT(player);
            if (root != null) {
                if (root.carrer.getLv() > 100) {
                    player.addChatComponentMessage(new ChatComponentTranslation("MMM.info.cantgetXP", new Object[0]));
                    root.carrer.addEXP(5L);
                    player.addExperience(5);
                    MMM.removePlayerCurrentItem(player);
                    return item;
                }
                root.carrer.addEXP(CExps);
                player
                    .addChatComponentMessage(new ChatComponentTranslation("MMM.info.getEXPs2", new Object[] { CExps }));
            }

            player.addExperience(Exps);
            player.addChatComponentMessage(
                new ChatComponentTranslation("MMM.info.getEXPs1", new Object[] { Integer.valueOf(Exps) }));
        }

        MMM.removePlayerCurrentItem(player);
        world.playSoundAtEntity(player, "random.drink", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        return item;
    }
}
