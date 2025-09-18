package indi.byhfdd.bh3rd.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.Main;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import project.studio.manametalmod.MMM;
import project.studio.manametalmod.api.IPotion;
import project.studio.manametalmod.entity.nbt.ManaMetalModRoot;
import project.studio.manametalmod.potion.PotionEffectM3;
import project.studio.manametalmod.potion.PotionM3;
import project.studio.manametalmod.produce.brewing.ManaPotionType;

import java.util.List;

public class StaminaCore extends Item implements IPotion {

    int effect;

    public StaminaCore(String name, int effect) {
        this.effect = effect;
        this.setTextureName("bh3rd:" + name);// 材质路径
        this.setUnlocalizedName(name);// 未本地化名字
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
        GameRegistry.registerItem(this, name, Main.MODID);
    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (PotionEffectM3.isPotion(player, PotionM3.potionResistance)) {
            return item;
        } else {
            ManaMetalModRoot root = MMM.getEntityNBT(player);
            if (root != null) {
                if (root.carrer.getLv() >= this.getLV(item.getItemDamage())) {
                    root.mana.addOxygen(this.getEffect(root, item.getItemDamage()));
                    root.mana.addFatigue(-4 * (item.getItemDamage() + 1));
                    world.playSoundAtEntity(player, "random.drink", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                    if (!player.capabilities.isCreativeMode) {
                        --item.stackSize;
                    }
                    player.addPotionEffect(new PotionEffect(3, 2400, 2));
                    PotionEffectM3.addPotion(player, PotionM3.potionMinefood, 120, 0);// 矿工汤
                    PotionEffectM3.addPotion(player, PotionM3.potionCofe, 120, 0);// 咖啡
                } else if (!world.isRemote) {
                    player.addChatComponentMessage(new ChatComponentTranslation("MMM.info.hpwater", new Object[0]));
                }
            }

            return item;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack Item, EntityPlayer Player, List list, boolean p_77624_4_) {
        list.add(
            StatCollector.translateToLocal("item.ItemNewFatigueWater.helf")
                + this.getEffect((ManaMetalModRoot) null, Item.getItemDamage()));
        list.add(StatCollector.translateToLocal("item.hpwater.leavel") + this.getLV(Item.getItemDamage()));
        list.add(StatCollector.translateToLocal("tips.Stamina_Potion"));
    }

    public int getLV(int itemDamage) {
        return 1;
    }

    public int getEffect(ManaMetalModRoot root, int itemDamage) {
        return effect;
    }

    @Override
    public ManaPotionType getType(int i) {
        return ManaPotionType.food;
    }

}
