package indi.byhfdd.bh3rd.item.card;

import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PrecisionCard02 extends CardCore {

    public PrecisionCard02() {
        super(1, "PrecisionCard02");
    }

    @Override
    protected List<ItemStack> getWeaponItems() {
        return Arrays.asList(
            new ItemStack(WeaponLoader.ZXX),
            new ItemStack(WeaponLoader.YJZHLE),
            new ItemStack(WeaponLoader.HYBH),

            new ItemStack(WeaponLoader.ZYDHG)
        );
    }
    @Override
    protected List<Integer> getWeaponProbabilities() {
        return Arrays.asList(5, 5, 5, 5);
    }
}
