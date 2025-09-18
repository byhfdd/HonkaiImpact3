package indi.byhfdd.bh3rd.cuisine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import project.studio.manametalmod.ManaMetalMod;
import project.studio.manametalmod.api.IFood;
import project.studio.manametalmod.core.NBTHelp;
import project.studio.manametalmod.produce.cuisine.FoodTaste;
import project.studio.manametalmod.produce.cuisine.ICuisine;
import project.studio.manametalmod.produce.cuisine.ItemCuisine;

import java.util.List;

public class FoodCore  extends ItemFood implements IFood, ICuisine {
    public static IIcon[] icons = new IIcon[10];
    public FoodCore() {
        super(1, 1.0F, false);
        this.setMaxStackSize(1);
        this.setAlwaysEdible();
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
    }
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
        player.getFoodStats().func_151686_a(this, item);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(item, world, player);
        ItemCuisine.addItemCuisineEffect(item, world, player, false);
        return item;
    }
    @Override
    public boolean canRoot(ItemStack food) {
        if (food.hasTagCompound()) {
            NBTTagCompound tag = food.getTagCompound();
            if (tag.hasKey("FoodTaste", 3)) {
                int data = NBTHelp.getIntSafe("FoodTaste", tag, -1);
                if (data != -1) {
                    FoodTaste ft = FoodTaste.values()[data];
                    if (ft == FoodTaste.GoldDust) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    public int func_150905_g(ItemStack item) {
        return 20;
    }

    public float func_150906_h(ItemStack item) {
        return 15.0F;
    }
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return super.hasEffect(par1ItemStack, pass);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return icons[meta];
    }

    public int getMetadata(int meta) {
        return meta;
    }

    public String getUnlocalizedName(ItemStack is) {
        return "item.FoodCore." + is.getItemDamage();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        for(int i = 0; i < 10; ++i) {
            icons[i] = reg.registerIcon("bh3rd:FoodCore." + i);
        }

    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itm, CreativeTabs tab, List lst) {
        for(int i = 0; i < 10; ++i) {
            lst.add(new ItemStack(itm, 1, i));
        }

    }

    @Override
    public int maxRoot(ItemStack food) {
        return 2400;
    }

    @Override
    public ItemStack getRootItem(ItemStack food) {
        return new ItemStack(ManaMetalMod.bigbowl);
    }
}
