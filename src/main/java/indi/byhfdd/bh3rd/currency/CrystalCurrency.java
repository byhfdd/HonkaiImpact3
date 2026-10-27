package indi.byhfdd.bh3rd.currency;

import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.item.ItemStack;

/** Crystal denominations remain separate from ManaMetal's ICoins and its ordinary money balance. */
public final class CrystalCurrency {

    private CrystalCurrency() {}

    public static int denomination(ItemStack stack) {
        if (stack == null || stack.stackSize <= 0 || stack.getItemDamage() != 0 || stack.getItem() == null) return 0;
        if (stack.getItem() == ItemLoader.crystal35) return 35;
        if (stack.getItem() == ItemLoader.crystal5) return 5;
        return stack.getItem() == ItemLoader.crystal1 ? 1 : 0;
    }

    public static long value(ItemStack stack) {
        return (long) denomination(stack) * (stack == null ? 0 : Math.max(0, stack.stackSize));
    }
}
