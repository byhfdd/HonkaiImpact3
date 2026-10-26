package indi.byhfdd.bh3rd.item.card;

import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PrecisionCard07 extends CardCore {

    public PrecisionCard07() {
        super(1, "PrecisionCard07");

    }

    @Override
    protected List<ItemStack> getWeaponItems() {
        return Arrays.asList(new ItemStack(WeaponLoader.FNMZ), new ItemStack(WeaponLoader.CGWQ));
    }

    @Override
    protected List<Integer> getWeaponProbabilities() {
        return Arrays.asList(5, 5);
    }

}
