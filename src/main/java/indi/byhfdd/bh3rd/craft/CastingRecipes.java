package indi.byhfdd.bh3rd.craft;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.items.craftingRecipes.CastingData;

public class CastingRecipes {// 锻造

    public static final ItemStack FK = new ItemStack(ItemLoader.FluidAlloyBlock, 18);
    public static final ItemStack SN = new ItemStack(ItemLoader.SuperconductingMetallicHydrogen, 220);
    public static final ItemStack ET = new ItemStack(ItemLoader.EinsteinRingMagnet, 100);

    @SuppressWarnings("unchecked")
    public static void init() {
        Object[][] PAIRS = {
            {WeaponLoader.LSJB, WeaponLoader.JRTG},
            {WeaponLoader.CGWQX, WeaponLoader.CGWQ},
            {WeaponLoader.XSZJ, WeaponLoader.XSZY},
            {WeaponLoader.SYZ, WeaponLoader.BAX},
            {WeaponLoader.XYZMY, WeaponLoader.XYZM},
            {WeaponLoader.BAZFL, WeaponLoader.BAZF},
            {WeaponLoader.TSZS, WeaponLoader.HSZS},
            {WeaponLoader.MFXX, WeaponLoader.AXYW},
            {WeaponLoader.RMZ, WeaponLoader.ZXX},
            {WeaponLoader.ZJZHLE, WeaponLoader.YJZHLE},
            {WeaponLoader.HYBHC, WeaponLoader.HYBH},
            {WeaponLoader.ZYDATJN, WeaponLoader.ZYDHG},
            {WeaponLoader.WSDFHA, WeaponLoader.WSDFH},
            {WeaponLoader.ZWZJ, WeaponLoader.ZWZY},
            {WeaponLoader.JMWJ, WeaponLoader.JM},
            {WeaponLoader.NYJ, WeaponLoader.SYJ},
            {WeaponLoader.YLD, WeaponLoader.FLD},
            {WeaponLoader.TJZJ, WeaponLoader.TJZY},
            {WeaponLoader.HYD, WeaponLoader.CRY},
            {WeaponLoader.ZCB, WeaponLoader.CBZ},
            {WeaponLoader.DZQLM, WeaponLoader.DZQL},
            {WeaponLoader.CNZJ, WeaponLoader.CNZY},
            {WeaponLoader.YYQWX, WeaponLoader.YYQW},
            {WeaponLoader.FNMZC, WeaponLoader.FNMZ}
        };

        for (Object[] pair : PAIRS) {
            CastingData.getRecipes.add(new Object[]{
                100000,
                new ItemStack((Item) pair[0]),
                ET, SN, FK,
                new ItemStack((Item) pair[1])
            });
        }
    }
}
