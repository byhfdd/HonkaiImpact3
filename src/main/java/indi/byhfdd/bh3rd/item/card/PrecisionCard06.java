package indi.byhfdd.bh3rd.item.card;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import indi.byhfdd.bh3rd.loader.WeaponLoader;

public class PrecisionCard06 extends CardCore {

    public PrecisionCard06() {
        super(1, "PrecisionCard06");
    }

    @Override
    protected List<ItemStack> getWeaponItems() {
        return Arrays.asList(new ItemStack(WeaponLoader.YYQW), new ItemStack(WeaponLoader.JRTG));
    }

    @Override
    protected List<Integer> getWeaponProbabilities() {
        return Arrays.asList(5, 5);
    }

}
