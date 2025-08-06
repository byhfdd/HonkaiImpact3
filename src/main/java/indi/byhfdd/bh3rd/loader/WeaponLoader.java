package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.weapon.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.api.addon.ItemToolSlashBladeMana;
import project.studio.manametalmod.utils.ToolCore;


public class WeaponLoader {
    public static final Item.ToolMaterial BH = ToolCore.addToolMaterial("BH",30,-1,50,50,50,new ItemStack(ItemLoader.Bh3rd));
    //名字。收获。耐久。效率。伤害加成。附魔能力
    public static final SickleCore XSZJ = new SickleCore("XSZJ",441,BH,100);
    public static final SickleCore XSZY = new SickleCore("XSZY",298,BH,80);
    public static final SickleCore SYZ = new SickleCore("SYZ",414,BH,100);
    public static final SickleCore BAX = new SickleCore("BAX",299,BH,80);
    public static final SickleCore XYZMY = new SickleCore("XYZMY",444,BH,100);
    public static final SickleCore XYZM = new SickleCore("XYZM",299,BH,80);
    public static final SickleCore BAZFL = new SickleCore("BAZFL",411,BH,100);
    public static final SickleCore BAZF = new SickleCore("BAZF",277,BH,80);
    public static final SickleCore TSZS = new SickleCore("TSZS",403,BH,100);
    public static final SickleCore HSZS = new SickleCore("HSZS",285,BH,80);
    public static final SickleCore MFXX = new SickleCore("MFXX",400,BH,100);
    public static final SickleCore AXYW = new SickleCore("AXYW",275,BH,80);
    public static final HammerCore RMZ = new HammerCore("RMZ",414,BH,100);
    public static final HammerCore ZXX = new HammerCore("ZXX",286,BH,80);
    public static final HammerCore ZJZHLE = new HammerCore("ZJZHLE",415,BH,100);
    public static final HammerCore YJZHLE = new HammerCore("YJZHLE",286,BH,80);
    public static final HammerCore HYBHC = new HammerCore("HYBHC",430,BH,100);
    public static final HammerCore HYBH = new HammerCore("HYBH",298,BH,80);
    public static final HammerCore ZYDATJN = new HammerCore("ZYDATJN",436,BH,100);
    public static final HammerCore ZYDHG = new HammerCore("ZYDHG",286,BH,80);
    public static final BowCore WSDFHA = new BowCore("WSDFHA",411,BH,100);
    public static final BowCore WSDFH = new BowCore("WSDFH",286,BH,80);
    public static final BowCore ZWZJ = new BowCore("ZWZJ",441,BH,100);
    public static final BowCore ZWZY = new BowCore("ZWZY",299,BH,80);
    public static final SwordCore JMWJ = new SwordCore("JMWJ",420,BH,100);
    public static final SwordCore JM = new SwordCore("JM",298,BH,80);
    public static final SwordCore NYJ = new SwordCore("NYJ",392,BH,100);
    public static final SwordCore SYJ = new SwordCore("SYJ",275,BH,80);
    public static final KatanaCore YLD = new KatanaCore("YLD",409,BH,100);
    public static final KatanaCore FLD = new KatanaCore("FLD",264,BH,80);
    public static final KatanaCore TJZJ = new KatanaCore("TJZJ",447,BH,100);
    public static final KatanaCore TJZY = new KatanaCore("TJZY",301,BH,80);
    public static final KatanaCore HYD = new KatanaCore("HYD",392,BH,100);
    public static final KatanaCore CRY = new KatanaCore("CRY",286,BH,80);
    public static final KatanaCore ZCB = new KatanaCore("ZCB",430,BH,100);
    public static final KatanaCore CBZ = new KatanaCore("CBZ",298,BH,80);
    public static final KatanaCore DZQLM = new KatanaCore("DZQLM",452,BH,100);
    public static final KatanaCore DZQL = new KatanaCore("DZQL",284,BH,80);
    public static final KatanaCore CNZJ = new KatanaCore("CNZJ",438,BH,100);
    public static final KatanaCore CNZY = new KatanaCore("CNZY",291,BH,80);
    public static final DaggerCore YYQWX = new DaggerCore("YYQWX", 442, BH, 100);
    public static final DaggerCore YYQW = new DaggerCore("YYQW", 301, BH, 80);
    public static final JavelinCore FNMZC = new JavelinCore("FNMZC", 439, BH, 100);
    public static final JavelinCore FNMZ = new JavelinCore("FNMZ", 301, BH, 80);


    public static void init(){

        GameRegistry.registerItem(XSZJ, "XSZJ");
        GameRegistry.registerItem(XSZY, "XSZY");
        GameRegistry.registerItem(SYZ, "SYZ");
        GameRegistry.registerItem(BAX, "BAX");
        GameRegistry.registerItem(XYZMY, "XYZMY");
        GameRegistry.registerItem(XYZM, "XYZM");
        GameRegistry.registerItem(BAZFL, "BAZFL");
        GameRegistry.registerItem(BAZF, "BAZF");
        GameRegistry.registerItem(TSZS, "TSZS");
        GameRegistry.registerItem(HSZS, "HSZS");
        GameRegistry.registerItem(MFXX, "MFXX");
        GameRegistry.registerItem(AXYW, "AXYW");
        GameRegistry.registerItem(RMZ, "RMZ");
        GameRegistry.registerItem(ZXX, "ZXX");
        GameRegistry.registerItem(ZJZHLE, "ZJZHLE");
        GameRegistry.registerItem(YJZHLE, "YJZHLE");
        GameRegistry.registerItem(HYBHC, "HYBHC");
        GameRegistry.registerItem(HYBH, "HYBH");
        GameRegistry.registerItem(ZYDATJN, "ZYDATJN");
        GameRegistry.registerItem(ZYDHG, "ZYDHG");
        GameRegistry.registerItem(WSDFHA, "WSDFHA");
        GameRegistry.registerItem(WSDFH, "WSDFH");
        GameRegistry.registerItem(ZWZJ, "ZWZJ");
        GameRegistry.registerItem(ZWZY, "ZWZY");
        GameRegistry.registerItem(JMWJ, "JMWJ");
        GameRegistry.registerItem(JM, "JM");
        GameRegistry.registerItem(NYJ, "NYJ");
        GameRegistry.registerItem(SYJ, "SYJ");
        GameRegistry.registerItem(YLD, "YLD");
        GameRegistry.registerItem(FLD, "FLD");
        GameRegistry.registerItem(TJZJ, "TJZJ");
        GameRegistry.registerItem(TJZY, "TJZY");
        GameRegistry.registerItem(HYD, "HYD");
        GameRegistry.registerItem(CRY, "CRY");
        GameRegistry.registerItem(ZCB, "ZCB");
        GameRegistry.registerItem(CBZ, "CBZ");
        GameRegistry.registerItem(DZQLM, "DZQLM");
        GameRegistry.registerItem(DZQL, "DZQL");
        GameRegistry.registerItem(CNZJ, "CNZJ");
        GameRegistry.registerItem(CNZY, "CNZY");
        GameRegistry.registerItem(YYQWX, "YYQWX");
        GameRegistry.registerItem(YYQW, "YYQW");
        GameRegistry.registerItem(FNMZC, "FNMZC");
        GameRegistry.registerItem(FNMZ, "FNMZ");
    }
}
