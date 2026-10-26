package indi.byhfdd.bh3rd.item.card;

import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PrecisionCard05 extends CardCore {

    public PrecisionCard05() {
        super(1, "PrecisionCard05");

    }

    @Override
    protected List<ItemStack> getWeaponItems() {
        return Arrays.asList(
            new ItemStack(WeaponLoader.FLD),
            new ItemStack(WeaponLoader.TJZY),
            new ItemStack(WeaponLoader.CRY),
            new ItemStack(WeaponLoader.CBZ),
            new ItemStack(WeaponLoader.DZQL),
            new ItemStack(WeaponLoader.CNZY));
    }

    @Override
    protected List<Integer> getWeaponProbabilities() {
        return Arrays.asList(5, 5, 5, 5, 5, 5);
    }

}
