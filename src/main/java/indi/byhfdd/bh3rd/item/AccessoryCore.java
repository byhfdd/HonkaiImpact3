package indi.byhfdd.bh3rd.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.Main;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import project.studio.manametalmod.api.IQualityItem;
import project.studio.manametalmod.api.Quality;
import project.studio.manametalmod.api.weapon.IMagicItem;
import project.studio.manametalmod.core.ManaItemType;
import project.studio.manametalmod.magic.magicItem.IMagicEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccessoryCore extends IMagicItem implements IQualityItem {
    public  List<List<IMagicEffect>> listEffect;
    public  List<IMagicEffect> temp;
    public  String Name;
    public IIcon icons;
    public AccessoryCore(String Name, IMagicEffect[] IMagicEffect) {
        super(Name);
        this.Name=Name;
        this.listEffect = new ArrayList<>();
        this.temp = new ArrayList<>();
        this.temp.addAll(Arrays.asList(IMagicEffect));
        this.listEffect.add(this.temp);
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);//物品栏
        GameRegistry.registerItem(this, Name, Main.MODID);
    }

    @Override
    public ManaItemType getType(ItemStack itemStack) {
        return ManaItemType.Tattoo;
    }

    @Override
    public int getNeedLV(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 50;
    }

    @Override
    public int TypeCount() {
        return 1;
    }

    @Override
    public List<IMagicEffect> getItemEffect(ItemStack itemStack) {
        return this.listEffect.get(itemStack.getItemDamage());
    }

    @Override
    public void onEquipment(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onDisrobe(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    public void onBeAttack(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLivingBase, DamageSource damageSource) {
    }

    public void onAttack(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLivingBase, DamageSource damageSource) {
    }

    public void onCrit(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLivingBase, DamageSource damageSource) {
    }

    public long getValue(ItemStack itemStack) {
        return 0;
    }

    public Quality getQuality(ItemStack item) {
        return Quality.Epic;
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.icons = reg.registerIcon(Main.MODID + ":" + this.Name);
    }
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icons;
    }


}