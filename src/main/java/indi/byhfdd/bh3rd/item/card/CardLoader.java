package indi.byhfdd.bh3rd.item.card;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.item.DiscCard;
import net.minecraft.item.Item;

public class CardLoader {

    public static final Item PrecisionCard01 = new PrecisionCard01();
    public static final Item PrecisionCard02 = new PrecisionCard02();
    public static final Item PrecisionCard03 = new PrecisionCard03();
    public static final Item PrecisionCard04 = new PrecisionCard04();
    public static final Item PrecisionCard05 = new PrecisionCard05();
    public static final Item PrecisionCard06 = new PrecisionCard06();
    public static final Item PrecisionCard07 = new PrecisionCard07();

    public static final Item DiscCard = new DiscCard();
    public static final Item SeedCard = new SeedCard();

    public CardLoader() {}

    public static void main() {
        init();
    }

    public static void init() {
        GameRegistry.registerItem(PrecisionCard01, "PrecisionCard01");
        GameRegistry.registerItem(PrecisionCard02, "PrecisionCard02");
        GameRegistry.registerItem(PrecisionCard03, "PrecisionCard03");
        GameRegistry.registerItem(PrecisionCard04, "PrecisionCard04");
        GameRegistry.registerItem(PrecisionCard05, "PrecisionCard05");
        GameRegistry.registerItem(PrecisionCard06, "PrecisionCard06");
        GameRegistry.registerItem(PrecisionCard07, "PrecisionCard07");
        GameRegistry.registerItem(DiscCard, "DiscCard");
        GameRegistry.registerItem(SeedCard, "SeedCard");
    }
}
