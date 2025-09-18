package indi.byhfdd.bh3rd.craft;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Calendar;

public class AddRecipe {// 合成

    public static final Item crystal35 = ItemLoader.crystal35;
    public static final Item ShapedSpaceLens = ItemLoader.ShapedSpaceLens;
    public static final Item StigmataResonance = ItemLoader.StigmataResonance;

    public static Item[] cardDecks = { ItemLoader.PrecisionCard01, ItemLoader.PrecisionCard02,
        ItemLoader.PrecisionCard03, ItemLoader.PrecisionCard04, ItemLoader.PrecisionCard05, ItemLoader.PrecisionCard06,
        ItemLoader.PrecisionCard07 };

    public static void init() {
        // G4
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.ImmaculateFairy),
            " SD",
            "SDS",
            "DS ",
            'D',
            ItemLoader.DistilledAshcrystal,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.BaroqueDramaSaint),
            " SD",
            "SDS",
            "DS ",
            'D',
            ItemLoader.DistilledAmethyst,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.TinyMoments),
            " SD",
            "SDS",
            "DS ",
            'D',
            ItemLoader.DistilledRubine,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.SingSoftly),
            " SD",
            "SDS",
            "DS ",
            'D',
            ItemLoader.DistilledCyanite,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.ImmaculateFairy),
            "SDS",
            "DQD",
            "SDS",
            'Q',
            ItemLoader.QuantumWaltz,
            'D',
            ItemLoader.DistilledAshcrystal,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.BaroqueDramaSaint),
            "SDS",
            "DXD",
            "SDS",
            'X',
            ItemLoader.SwordHeart,
            'D',
            ItemLoader.DistilledAmethyst,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.TinyMoments),
            "SDS",
            "DLD",
            "SDS",
            'L',
            ItemLoader.PrincessLilliput,
            'D',
            ItemLoader.DistilledRubine,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.SingSoftly),
            "SDS",
            "DPD",
            "SDS",
            'P',
            ItemLoader.LawIce,
            'D',
            ItemLoader.DistilledCyanite,
            'S',
            StigmataResonance);
        // G3
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.QuantumWaltz),
            " SP",
            "SPS",
            "PS ",
            'P',
            ItemLoader.ProfoundAshcrystal,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.SwordHeart),
            " SP",
            "SPS",
            "PS ",
            'P',
            ItemLoader.ProfoundAmethyst,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.LawIce),
            " SP",
            "SPS",
            "PS ",
            'P',
            ItemLoader.ProfoundCyanite,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.PrincessLilliput),
            " SP",
            "SPS",
            "PS ",
            'P',
            ItemLoader.ProfoundRubine,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.QuantumWaltz),
            "SPS",
            "PRP",
            "SPS",
            'R',
            ItemLoader.Renaissance,
            'P',
            ItemLoader.ProfoundAshcrystal,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.SwordHeart),
            "SPS",
            "PAP",
            "SPS",
            'A',
            ItemLoader.Artist,
            'P',
            ItemLoader.ProfoundAmethyst,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.LawIce),
            "SPS",
            "PAP",
            "SPS",
            'A',
            ItemLoader.ArcticExplorer,
            'P',
            ItemLoader.ProfoundCyanite,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.PrincessLilliput),
            "SPS",
            "PXP",
            "SPS",
            'X',
            ItemLoader.SunProphet,
            'P',
            ItemLoader.ProfoundRubine,
            'S',
            StigmataResonance);
        // G2
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.Renaissance),
            " SD",
            "SDS",
            "DS ",
            'D',
            ItemLoader.DarkAshcrystal,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.Artist),
            " SD",
            "SDS",
            "DS ",
            'D',
            ItemLoader.DarkAmethyst,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.ArcticExplorer),
            " SD",
            "SDS",
            "DS ",
            'D',
            ItemLoader.DarkCyanite,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.SunProphet),
            " SD",
            "SDS",
            "DS ",
            'D',
            ItemLoader.DarkRubine,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.Renaissance),
            "SDS",
            "DRD",
            "SDS",
            'R',
            ItemLoader.RussianCzar,
            'D',
            ItemLoader.DarkAshcrystal,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.Artist),
            "SDS",
            "DKD",
            "SDS",
            'K',
            ItemLoader.KnifeLegend,
            'D',
            ItemLoader.DarkAmethyst,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.ArcticExplorer),
            "SDS",
            "DLD",
            "SDS",
            'L',
            ItemLoader.LceShadowButterfly,
            'D',
            ItemLoader.DarkCyanite,
            'S',
            StigmataResonance);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.SunProphet),
            "SDS",
            "DAD",
            "SDS",
            'A',
            ItemLoader.AncientWill,
            'D',
            ItemLoader.DarkRubine,
            'S',
            StigmataResonance);
        // G1
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.RussianCzar),
            " SI",
            "SIS",
            "IS ",
            'I',
            ItemLoader.IllusoryAshcrystal,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.KnifeLegend),
            " SI",
            "SIS",
            "IS ",
            'I',
            ItemLoader.IllusoryAmethyst,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.LceShadowButterfly),
            " SI",
            "SIS",
            "IS ",
            'I',
            ItemLoader.IllusoryCyanite,
            'S',
            ShapedSpaceLens);
        GameRegistry.addShapedRecipe(
            new ItemStack(ItemLoader.AncientWill),
            " SI",
            "SIS",
            "IS ",
            'I',
            ItemLoader.IllusoryRubine,
            'S',
            ShapedSpaceLens);
        // 水晶
        GameRegistry.addShapelessRecipe(new ItemStack(ItemLoader.crystal1, 4), Items.diamond);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.crystal5),
            ItemLoader.crystal1,
            ItemLoader.crystal1,
            ItemLoader.crystal1,
            ItemLoader.crystal1,
            ItemLoader.crystal1);
        GameRegistry.addShapelessRecipe(
            new ItemStack(crystal35),
            ItemLoader.crystal5,
            ItemLoader.crystal5,
            ItemLoader.crystal5,
            ItemLoader.crystal5,
            ItemLoader.crystal5,
            ItemLoader.crystal5,
            ItemLoader.crystal5);
        //
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.DarkRubine),
            ItemLoader.IllusoryRubine,
            ItemLoader.IllusoryRubine,
            ItemLoader.IllusoryRubine,
            ItemLoader.IllusoryRubine,
            ItemLoader.IllusoryRubine,
            ItemLoader.IllusoryRubine,
            ItemLoader.IllusoryRubine,
            ItemLoader.IllusoryRubine,
            ItemLoader.IllusoryRubine);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.ProfoundRubine),
            ItemLoader.DarkRubine,
            ItemLoader.DarkRubine,
            ItemLoader.DarkRubine,
            ItemLoader.DarkRubine,
            ItemLoader.DarkRubine,
            ItemLoader.DarkRubine,
            ItemLoader.DarkRubine,
            ItemLoader.DarkRubine,
            ItemLoader.DarkRubine);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.DistilledRubine),
            ItemLoader.ProfoundRubine,
            ItemLoader.ProfoundRubine,
            ItemLoader.ProfoundRubine,
            ItemLoader.ProfoundRubine,
            ItemLoader.ProfoundRubine,
            ItemLoader.ProfoundRubine,
            ItemLoader.ProfoundRubine,
            ItemLoader.ProfoundRubine,
            ItemLoader.ProfoundRubine);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.DarkAmethyst),
            ItemLoader.IllusoryAmethyst,
            ItemLoader.IllusoryAmethyst,
            ItemLoader.IllusoryAmethyst,
            ItemLoader.IllusoryAmethyst,
            ItemLoader.IllusoryAmethyst,
            ItemLoader.IllusoryAmethyst,
            ItemLoader.IllusoryAmethyst,
            ItemLoader.IllusoryAmethyst,
            ItemLoader.IllusoryAmethyst);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.ProfoundAmethyst),
            ItemLoader.DarkAmethyst,
            ItemLoader.DarkAmethyst,
            ItemLoader.DarkAmethyst,
            ItemLoader.DarkAmethyst,
            ItemLoader.DarkAmethyst,
            ItemLoader.DarkAmethyst,
            ItemLoader.DarkAmethyst,
            ItemLoader.DarkAmethyst,
            ItemLoader.DarkAmethyst);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.DistilledAmethyst),
            ItemLoader.ProfoundAmethyst,
            ItemLoader.ProfoundAmethyst,
            ItemLoader.ProfoundAmethyst,
            ItemLoader.ProfoundAmethyst,
            ItemLoader.ProfoundAmethyst,
            ItemLoader.ProfoundAmethyst,
            ItemLoader.ProfoundAmethyst,
            ItemLoader.ProfoundAmethyst,
            ItemLoader.ProfoundAmethyst);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.DarkAshcrystal),
            ItemLoader.IllusoryAshcrystal,
            ItemLoader.IllusoryAshcrystal,
            ItemLoader.IllusoryAshcrystal,
            ItemLoader.IllusoryAshcrystal,
            ItemLoader.IllusoryAshcrystal,
            ItemLoader.IllusoryAshcrystal,
            ItemLoader.IllusoryAshcrystal,
            ItemLoader.IllusoryAshcrystal,
            ItemLoader.IllusoryAshcrystal);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.ProfoundAshcrystal),
            ItemLoader.DarkAshcrystal,
            ItemLoader.DarkAshcrystal,
            ItemLoader.DarkAshcrystal,
            ItemLoader.DarkAshcrystal,
            ItemLoader.DarkAshcrystal,
            ItemLoader.DarkAshcrystal,
            ItemLoader.DarkAshcrystal,
            ItemLoader.DarkAshcrystal,
            ItemLoader.DarkAshcrystal);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.DistilledAshcrystal),
            ItemLoader.ProfoundAshcrystal,
            ItemLoader.ProfoundAshcrystal,
            ItemLoader.ProfoundAshcrystal,
            ItemLoader.ProfoundAshcrystal,
            ItemLoader.ProfoundAshcrystal,
            ItemLoader.ProfoundAshcrystal,
            ItemLoader.ProfoundAshcrystal,
            ItemLoader.ProfoundAshcrystal,
            ItemLoader.ProfoundAshcrystal);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.DarkCyanite),
            ItemLoader.IllusoryCyanite,
            ItemLoader.IllusoryCyanite,
            ItemLoader.IllusoryCyanite,
            ItemLoader.IllusoryCyanite,
            ItemLoader.IllusoryCyanite,
            ItemLoader.IllusoryCyanite,
            ItemLoader.IllusoryCyanite,
            ItemLoader.IllusoryCyanite,
            ItemLoader.IllusoryCyanite);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.ProfoundCyanite),
            ItemLoader.DarkCyanite,
            ItemLoader.DarkCyanite,
            ItemLoader.DarkCyanite,
            ItemLoader.DarkCyanite,
            ItemLoader.DarkCyanite,
            ItemLoader.DarkCyanite,
            ItemLoader.DarkCyanite,
            ItemLoader.DarkCyanite,
            ItemLoader.DarkCyanite);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ItemLoader.DistilledCyanite),
            ItemLoader.ProfoundCyanite,
            ItemLoader.ProfoundCyanite,
            ItemLoader.ProfoundCyanite,
            ItemLoader.ProfoundCyanite,
            ItemLoader.ProfoundCyanite,
            ItemLoader.ProfoundCyanite,
            ItemLoader.ProfoundCyanite,
            ItemLoader.ProfoundCyanite,
            ItemLoader.ProfoundCyanite);
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int adjustedDayOfWeek = (dayOfWeek == 1) ? 7 : dayOfWeek - 1;
        int index = adjustedDayOfWeek - 1;
        if (index >= 0 && index < cardDecks.length) {
            addCardRecipes(cardDecks[index], crystal35);
        }
    }

    private static void addCardRecipes(Item card, Item crystal35) {
        GameRegistry.addShapelessRecipe(
            new ItemStack(card),
            crystal35,
            crystal35,
            crystal35,
            crystal35,
            crystal35,
            crystal35,
            crystal35,
            crystal35);
    }
}
