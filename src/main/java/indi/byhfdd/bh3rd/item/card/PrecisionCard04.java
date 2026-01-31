package indi.byhfdd.bh3rd.item.card;

import indi.byhfdd.bh3rd.loader.WeaponLoader;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PrecisionCard04 extends CardCore {

    public PrecisionCard04() {
        super(1, "PrecisionCard04");

    }

    @Override
    protected List<ItemStack> getWeaponItems() {
        return Arrays.asList(
            new ItemStack(WeaponLoader.JM),
            new ItemStack(WeaponLoader.SYJ)
        );
    }

    @Override
    protected List<Integer> getWeaponProbabilities() {
        return Arrays.asList(5, 5);
    }
}
