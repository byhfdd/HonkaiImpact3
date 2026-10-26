package indi.byhfdd.bh3rd.item.card;

import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PrecisionCard03 extends CardCore {

    public PrecisionCard03() {
        super(1, "PrecisionCard03");
    }

    @Override
    protected List<ItemStack> getWeaponItems() {
        return Arrays.asList(
            new ItemStack(WeaponLoader.WSDFH),
            new ItemStack(WeaponLoader.ZWZY),
            new ItemStack(WeaponLoader.WXZJ));
    }

    @Override
    protected List<Integer> getWeaponProbabilities() {
        return Arrays.asList(5, 5, 5);
    }
}
