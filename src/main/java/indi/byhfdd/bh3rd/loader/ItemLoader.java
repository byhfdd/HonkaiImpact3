package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.item.*;
import net.minecraft.item.Item;
import project.studio.manametalmod.items.itemBag.ItemBasicBagWeight;
import project.studio.manametalmod.magic.magicItem.IMagicEffect;
import project.studio.manametalmod.magic.magicItem.MagicItemType;

public class ItemLoader {

    public static final Item Bh3rd = (new Item()).setTextureName("bh3rd:Bh3rd");
    public static final ItemBasicBagWeight PrecisionCard01 = new PrecisionCard01();
    public static final ItemBasicBagWeight PrecisionCard02 = new PrecisionCard02();
    public static final ItemBasicBagWeight PrecisionCard03 = new PrecisionCard03();
    public static final ItemBasicBagWeight PrecisionCard04 = new PrecisionCard04();
    public static final ItemBasicBagWeight PrecisionCard05 = new PrecisionCard05();
    public static final ItemBasicBagWeight PrecisionCard06 = new PrecisionCard06();
    public static final ItemBasicBagWeight PrecisionCard07 = new PrecisionCard07();
    public static final ItemBasicBagWeight DiscCard = new DiscCard();

    public static final ItemCore EinsteinRingMagnet = new ItemCore("EinsteinRingMagnet");
    public static final ItemCore FluidAlloyBlock = new ItemCore("FluidAlloyBlock");
    public static final ItemCore SuperconductingMetallicHydrogen = new ItemCore("SuperconductingMetallicHydrogen");
    public static final ItemCore crystal1 = new ItemCore("crystal1");
    public static final ItemCore crystal5 = new ItemCore("crystal5");
    public static final ItemCore crystal35 = new ItemCore("crystal35");
    public static final ItemCore EtherPhlogiston = new ItemCore("EtherPhlogiston");
    public static final ItemCore IllusoryRubine = new ItemCore("IllusoryRubine");
    public static final ItemCore IllusoryAmethyst = new ItemCore("IllusoryAmethyst");
    public static final ItemCore IllusoryAshcrystal = new ItemCore("IllusoryAshcrystal");
    public static final ItemCore IllusoryCyanite = new ItemCore("IllusoryCyanite");
    public static final ItemCore DarkRubine = new ItemCore("DarkRubine");
    public static final ItemCore DarkAmethyst = new ItemCore("DarkAmethyst");
    public static final ItemCore DarkAshcrystal = new ItemCore("DarkAshcrystal");
    public static final ItemCore DarkCyanite = new ItemCore("DarkCyanite");
    public static final ItemCore ProfoundRubine = new ItemCore("ProfoundRubine");
    public static final ItemCore ProfoundAmethyst = new ItemCore("ProfoundAmethyst");
    public static final ItemCore ProfoundAshcrystal = new ItemCore("ProfoundAshcrystal");
    public static final ItemCore ProfoundCyanite = new ItemCore("ProfoundCyanite");
    public static final ItemCore DistilledRubine = new ItemCore("DistilledRubine");
    public static final ItemCore DistilledAmethyst = new ItemCore("DistilledAmethyst");
    public static final ItemCore DistilledAshcrystal = new ItemCore("DistilledAshcrystal");
    public static final ItemCore DistilledCyanite = new ItemCore("DistilledCyanite");
    public static final ItemCore StigmataResonance = new ItemCore("StigmataResonance");
    public static final ItemCore ShapedSpaceLens = new ItemCore("ShapedSpaceLens");
    // 体力药水
    public static final StaminaCore StaminaPotion = new StaminaCore("StaminaPotion", 300);
    public static final StaminaCore Watermelon = new StaminaCore("Watermelon", 200);
    public static final StaminaCore SodaPop = new StaminaCore("SodaPop", 100);
    // 灵魂
    public static final SoulCore SoulShards = new SoulCore("SoulShards", 150);
    public static final SoulCore SoulGeminiShards = new SoulCore("SoulGeminiShards", 375);
    public static final SoulCore SoulCrystallization = new SoulCore("SoulCrystallization", 750);
    public static final SoulCore SoulGeminiCrystallization = new SoulCore("SoulGeminiCrystallization", 1500);
    // 经验
    public static final ChipCore BaseChip = new ChipCore("BaseChip", 120, 60);
    public static final ChipCore AdvancedChips = new ChipCore("AdvancedChips", 600, 300);
    public static final ChipCore SeniorChips = new ChipCore("SeniorChips", 3000, 1500);
    public static final ChipCore PremiumChips = new ChipCore("PremiumChips", 14000, 7500);
    // 金币
    public static final GoldCore GoldBox = new GoldCore("GoldBox", 14000);
    // 唱片
    public static final DiscCore Reburn = new DiscCore("Reburn");
    public static final DiscCore Befall = new DiscCore("Befall");
    public static final DiscCore DualEgo = new DiscCore("DualEgo");
    public static final DiscCore Cyberangel = new DiscCore("Cyberangel");
    public static final DiscCore IRAS17514 = new DiscCore("IRAS17514");
    public static final DiscCore MoonHalo = new DiscCore("MoonHalo");
    public static final DiscCore Nightglow = new DiscCore("Nightglow");
    public static final DiscCore Regression = new DiscCore("Regression");
    public static final DiscCore Rubia = new DiscCore("Rubia");
    public static final DiscCore Starfall = new DiscCore("Starfall");
    public static final DiscCore TruE = new DiscCore("TruE");
    public static final DiscCore DaCapo = new DiscCore("DaCapo");
    public static final DiscCore BCY = new DiscCore("BCY");
    public static final DiscCore BHSJDGJ = new DiscCore("BHSJDGJ");
    public static final DiscCore GirlInside = new DiscCore("GirlInside");
    public static final DiscCore GZJSZ = new DiscCore("GZJSZ");
    public static final DiscCore Oaths = new DiscCore("Oaths");
    public static final DiscCore LZ = new DiscCore("LZ");
    public static final DiscCore XYNXSZR = new DiscCore("XYNXSZR");
    public static final DiscCore WLLK = new DiscCore("WLLK");
    public static final DiscCore WLZJ = new DiscCore("WLZJ");
    public static final DiscCore ReOracle = new DiscCore("ReOracle");
    public static final DiscCore QYX = new DiscCore("QYX");
    public static final DiscCore QSZZ = new DiscCore("QSZZ");
    public static final DiscCore QNZY = new DiscCore("QNZY");
    public static final DiscCore ZCFZQ = new DiscCore("ZCFZQ");
    public static final DiscCore NoCeiling = new DiscCore("NoCeiling");

    // 圣痕
    // 物理
    public static final AccessoryCore ImmaculateFairy = new AccessoryCore(
        "ImmaculateFairy",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.physicalAttack, 500f), // 物理攻击力
            new IMagicEffect(MagicItemType.attackMultiplier, 0.20f), // 造成的伤害
            new IMagicEffect(MagicItemType.crit, 20), // 暴击率
            new IMagicEffect(MagicItemType.critDamage, 0.20f), // 暴击伤害:
            new IMagicEffect(MagicItemType.penetrate, 20)// 穿透值:
        });
    public static final AccessoryCore QuantumWaltz = new AccessoryCore(
        "QuantumWaltz",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.physicalAttack, 375f), // 物理攻击力
            new IMagicEffect(MagicItemType.attackMultiplier, 0.15f), // 造成的伤害
            new IMagicEffect(MagicItemType.crit, 15), // 暴击率
            new IMagicEffect(MagicItemType.critDamage, 0.15f), // 暴击伤害:
            new IMagicEffect(MagicItemType.penetrate, 15)// 穿透值:
        });
    public static final AccessoryCore Renaissance = new AccessoryCore(
        "Renaissance",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.physicalAttack, 250), // 物理攻击力
            new IMagicEffect(MagicItemType.attackMultiplier, 0.10f), // 造成的伤害
            new IMagicEffect(MagicItemType.crit, 10), // 暴击率
            new IMagicEffect(MagicItemType.critDamage, 0.10f), // 暴击伤害:
            new IMagicEffect(MagicItemType.penetrate, 10)// 穿透值:
        });
    public static final AccessoryCore RussianCzar = new AccessoryCore(
        "RussianCzar",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.physicalAttack, 125f), // 物理攻击力
            new IMagicEffect(MagicItemType.attackMultiplier, 0.05f), // 造成的伤害
            new IMagicEffect(MagicItemType.crit, 5), // 暴击率
            new IMagicEffect(MagicItemType.critDamage, 0.05f), // 暴击伤害:
            new IMagicEffect(MagicItemType.penetrate, 5)// 穿透值:
        });
    // 魔法

    public static final AccessoryCore BaroqueDramaSaint = new AccessoryCore(
        "BaroqueDramaSaint",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.magicAttack, 500f), // 魔法攻击力:
            new IMagicEffect(MagicItemType.attackMultiplier, 0.20f), // 造成的伤害
            new IMagicEffect(MagicItemType.crit, 20), // 暴击率
            new IMagicEffect(MagicItemType.critDamage, 0.20f), // 暴击伤害:
            new IMagicEffect(MagicItemType.penetrate, 20)// 穿透值:
        });
    public static final AccessoryCore SwordHeart = new AccessoryCore(
        "SwordHeart",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.magicAttack, 375f), // 魔法攻击力:
            new IMagicEffect(MagicItemType.attackMultiplier, 0.15f), // 造成的伤害
            new IMagicEffect(MagicItemType.crit, 15), // 暴击率
            new IMagicEffect(MagicItemType.critDamage, 0.15f), // 暴击伤害:
            new IMagicEffect(MagicItemType.penetrate, 15)// 穿透值:
        });
    public static final AccessoryCore Artist = new AccessoryCore(
        "Artist",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.magicAttack, 250f), // 魔法攻击力:
            new IMagicEffect(MagicItemType.attackMultiplier, 0.10f), // 造成的伤害
            new IMagicEffect(MagicItemType.crit, 10), // 暴击率
            new IMagicEffect(MagicItemType.critDamage, 0.10f), // 暴击伤害:
            new IMagicEffect(MagicItemType.penetrate, 10)// 穿透值:
        });
    public static final AccessoryCore KnifeLegend = new AccessoryCore(
        "KnifeLegend",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.magicAttack, 125f), // 魔法攻击力:
            new IMagicEffect(MagicItemType.attackMultiplier, 0.05f), // 造成的伤害
            new IMagicEffect(MagicItemType.crit, 5), // 暴击率
            new IMagicEffect(MagicItemType.critDamage, 0.05f), // 暴击伤害:
            new IMagicEffect(MagicItemType.penetrate, 5)// 穿透值:
        });
    // 法力

    public static final AccessoryCore TinyMoments = new AccessoryCore(
        "TinyMoments",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.manaMax, 8000f), // 最大法力:
            new IMagicEffect(MagicItemType.pointAgile, 40), // 敏捷
            new IMagicEffect(MagicItemType.avoid, 20f), // 回避率:
            new IMagicEffect(MagicItemType.pointWisdom, 20f), // 智慧:
            new IMagicEffect(MagicItemType.manaReply, 3000f),// 回魔速度
        });
    public static final AccessoryCore LawIce = new AccessoryCore(
        "LawIce",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.manaMax, 6000f), // 最大法力:
            new IMagicEffect(MagicItemType.pointAgile, 30), // 敏捷
            new IMagicEffect(MagicItemType.avoid, 15f), // 回避率:
            new IMagicEffect(MagicItemType.pointWisdom, 15f), // 智慧:
            new IMagicEffect(MagicItemType.manaReply, 2250f),// 回魔速度
        });
    public static final AccessoryCore ArcticExplorer = new AccessoryCore(
        "ArcticExplorer",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.manaMax, 4000), // 最大法力:
            new IMagicEffect(MagicItemType.pointAgile, 20), // 敏捷
            new IMagicEffect(MagicItemType.avoid, 10f), // 回避率:
            new IMagicEffect(MagicItemType.pointWisdom, 10f), // 智慧:
            new IMagicEffect(MagicItemType.manaReply, 1500f),// 回魔速度
        });
    public static final AccessoryCore LceShadowButterfly = new AccessoryCore(
        "LceShadowButterfly",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.manaMax, 2000), // 最大法力:
            new IMagicEffect(MagicItemType.pointAgile, 5), // 敏捷
            new IMagicEffect(MagicItemType.avoid, 5f), // 回避率:
            new IMagicEffect(MagicItemType.pointWisdom, 5f), // 智慧:
            new IMagicEffect(MagicItemType.manaReply, 750f),// 回魔速度
        });

    // 生命

    public static final AccessoryCore SingSoftly = new AccessoryCore(
        "SingSoftly",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.HP, 20000f), // 最大生命值
            new IMagicEffect(MagicItemType.magicDefense, 500f), // 防御力:
            new IMagicEffect(MagicItemType.healthRecovery, 4), // 恢复量:
            new IMagicEffect(MagicItemType.defenseMultiplier, 0.20f), // 受到的伤害
            new IMagicEffect(MagicItemType.hpReply, 300f),// 生命恢复
        });
    public static final AccessoryCore PrincessLilliput = new AccessoryCore(
        "PrincessLilliput",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.HP, 15000f), // 最大生命值
            new IMagicEffect(MagicItemType.magicDefense, 375f), // 防御力:
            new IMagicEffect(MagicItemType.healthRecovery, 3), // 恢复量:
            new IMagicEffect(MagicItemType.defenseMultiplier, 0.15f), // 受到的伤害
            new IMagicEffect(MagicItemType.hpReply, 225f),// 生命恢复
        });
    public static final AccessoryCore SunProphet = new AccessoryCore(
        "SunProphet",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.HP, 10000f), // 最大生命值
            new IMagicEffect(MagicItemType.magicDefense, 250f), // 防御力:
            new IMagicEffect(MagicItemType.healthRecovery, 2), // 恢复量:
            new IMagicEffect(MagicItemType.defenseMultiplier, 0.10f), // 受到的伤害
            new IMagicEffect(MagicItemType.hpReply, 150f),// 生命恢复
        });
    public static final AccessoryCore AncientWill = new AccessoryCore(
        "AncientWill",
        new IMagicEffect[] { new IMagicEffect(MagicItemType.HP, 5000f), // 最大生命值
            new IMagicEffect(MagicItemType.magicDefense, 125f), // 防御力:
            new IMagicEffect(MagicItemType.healthRecovery, 1), // 恢复量:
            new IMagicEffect(MagicItemType.defenseMultiplier, 0.05f), // 受到的伤害
            new IMagicEffect(MagicItemType.hpReply, 75),// 生命恢复
        });

    public static void init() {
        GameRegistry.registerItem(Bh3rd, "Bh3rd");
        GameRegistry.registerItem(PrecisionCard01, "PrecisionCard01");
        GameRegistry.registerItem(PrecisionCard02, "PrecisionCard02");
        GameRegistry.registerItem(PrecisionCard03, "PrecisionCard03");
        GameRegistry.registerItem(PrecisionCard04, "PrecisionCard04");
        GameRegistry.registerItem(PrecisionCard05, "PrecisionCard05");
        GameRegistry.registerItem(PrecisionCard06, "PrecisionCard06");
        GameRegistry.registerItem(PrecisionCard07, "PrecisionCard07");
        GameRegistry.registerItem(DiscCard, "DiscCard");
    }
}
