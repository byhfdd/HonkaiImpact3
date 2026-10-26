package indi.byhfdd.bh3rd.shop;

import java.util.Calendar;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import indi.byhfdd.bh3rd.item.card.CardLoader;

public final class DailySupply {

    private DailySupply() {}

    public static int getCurrentOfferIndex() {
        return getOfferIndex(Calendar.getInstance());
    }

    public static int getOfferIndex(Calendar date) {
        return (date.get(Calendar.DAY_OF_WEEK) + 5) % 7;
    }

    public static ItemStack getOfferStack(int index) {
        Item card;
        switch (index) {
            case 0:
                card = CardLoader.PrecisionCard01;
                break;
            case 1:
                card = CardLoader.PrecisionCard02;
                break;
            case 2:
                card = CardLoader.PrecisionCard03;
                break;
            case 3:
                card = CardLoader.PrecisionCard04;
                break;
            case 4:
                card = CardLoader.PrecisionCard05;
                break;
            case 5:
                card = CardLoader.PrecisionCard06;
                break;
            case 6:
                card = CardLoader.PrecisionCard07;
                break;
            default:
                return null;
        }
        return new ItemStack(card);
    }
}
