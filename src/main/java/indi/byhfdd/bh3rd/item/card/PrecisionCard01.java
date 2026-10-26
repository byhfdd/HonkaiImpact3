package indi.byhfdd.bh3rd.item.card;

import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PrecisionCard01 extends CardCore {

    public PrecisionCard01() {
        super(1, "PrecisionCard01");
    }

    @Override
    protected List<ItemStack> getWeaponItems() {
        return Arrays.asList(
            new ItemStack(WeaponLoader.XSZY),
            new ItemStack(WeaponLoader.BAX),
            new ItemStack(WeaponLoader.XYZM),
            new ItemStack(WeaponLoader.BAZF),
            new ItemStack(WeaponLoader.HSZS),
            new ItemStack(WeaponLoader.AXYW));
    }

    @Override
    protected List<Integer> getWeaponProbabilities() {
        return Arrays.asList(5, 5, 5, 5, 5, 5);
    }
}
