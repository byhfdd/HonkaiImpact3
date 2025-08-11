package indi.byhfdd.bh3rd.craft;

import net.minecraft.item.ItemStack;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import indi.byhfdd.bh3rd.loader.WeaponLoader;
import project.studio.manametalmod.items.craftingRecipes.CastingData;

public class CastingRecipes {// 锻造

    public static final ItemStack FK = new ItemStack(ItemLoader.FluidAlloyBlock, 18);
    public static final ItemStack SN = new ItemStack(ItemLoader.SuperconductingMetallicHydrogen, 220);
    public static final ItemStack ET = new ItemStack(ItemLoader.EinsteinRingMagnet, 100);

    @SuppressWarnings("unchecked")
    public static void init() {
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.XSZJ), ET, SN, FK, new ItemStack(WeaponLoader.XSZY) });
        CastingData.getRecipes
            .add(new Object[] { 100000, new ItemStack(WeaponLoader.SYZ), ET, SN, FK, new ItemStack(WeaponLoader.BAX) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.XYZMY), ET, SN, FK, new ItemStack(WeaponLoader.XYZM) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.BAZFL), ET, SN, FK, new ItemStack(WeaponLoader.BAZF) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.TSZS), ET, SN, FK, new ItemStack(WeaponLoader.HSZS) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.MFXX), ET, SN, FK, new ItemStack(WeaponLoader.AXYW) });
        CastingData.getRecipes
            .add(new Object[] { 100000, new ItemStack(WeaponLoader.RMZ), ET, SN, FK, new ItemStack(WeaponLoader.ZXX) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.ZJZHLE), ET, SN, FK,
                new ItemStack(WeaponLoader.YJZHLE) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.HYBHC), ET, SN, FK, new ItemStack(WeaponLoader.HYBH) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.ZYDATJN), ET, SN, FK,
                new ItemStack(WeaponLoader.ZYDHG) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.WSDFHA), ET, SN, FK, new ItemStack(WeaponLoader.WSDFH) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.ZWZJ), ET, SN, FK, new ItemStack(WeaponLoader.ZWZY) });
        CastingData.getRecipes
            .add(new Object[] { 100000, new ItemStack(WeaponLoader.JMWJ), ET, SN, FK, new ItemStack(WeaponLoader.JM) });
        CastingData.getRecipes
            .add(new Object[] { 100000, new ItemStack(WeaponLoader.NYJ), ET, SN, FK, new ItemStack(WeaponLoader.SYJ) });
        CastingData.getRecipes
            .add(new Object[] { 100000, new ItemStack(WeaponLoader.YLD), ET, SN, FK, new ItemStack(WeaponLoader.FLD) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.TJZJ), ET, SN, FK, new ItemStack(WeaponLoader.TJZY) });
        CastingData.getRecipes
            .add(new Object[] { 100000, new ItemStack(WeaponLoader.HYD), ET, SN, FK, new ItemStack(WeaponLoader.CRY) });
        CastingData.getRecipes
            .add(new Object[] { 100000, new ItemStack(WeaponLoader.ZCB), ET, SN, FK, new ItemStack(WeaponLoader.CBZ) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.DZQLM), ET, SN, FK, new ItemStack(WeaponLoader.DZQL) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.CNZJ), ET, SN, FK, new ItemStack(WeaponLoader.CNZY) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.YYQWX), ET, SN, FK, new ItemStack(WeaponLoader.YYQW) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.FNMZC), ET, SN, FK, new ItemStack(WeaponLoader.FNMZ) });
        CastingData.getRecipes.add(
            new Object[] { 100000, new ItemStack(WeaponLoader.FNMZC), ET, SN, FK, new ItemStack(WeaponLoader.FNMZ) });
    }
}
