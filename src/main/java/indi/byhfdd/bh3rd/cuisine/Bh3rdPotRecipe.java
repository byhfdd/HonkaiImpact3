package indi.byhfdd.bh3rd.cuisine;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import project.studio.manametalmod.produce.cuisine.PotRecipe;

import java.util.List;
import java.util.function.Function;

/** Adds the ore dictionary ingredient matching missing from ManaMetal's cooking recipe implementation. */
public final class Bh3rdPotRecipe extends PotRecipe {

    private final Function<String, List<ItemStack>> oreLookup;

    public Bh3rdPotRecipe(ItemStack output, int[] foodData, int weight, Object[] keyFood) {
        this(output, foodData, weight, keyFood, name -> OreDictionary.getOres(name, false));
    }

    Bh3rdPotRecipe(ItemStack output, int[] foodData, int weight, Object[] keyFood,
        Function<String, List<ItemStack>> oreLookup) {
        super(output, foodData, weight, keyFood);
        this.oreLookup = oreLookup;
    }

    @Override
    public boolean hasKeyFood(ItemStack[] ingredients) {
        if (keyFood == null || ingredients == null) return false;

        for (Object key : keyFood) {
            if (key instanceof String) {
                if (!hasOre((String) key, ingredients)) return false;
            } else {
                ItemStack required = toStack(key);
                if (required == null || !hasExactItem(required, ingredients)) return false;
            }
        }
        return true;
    }

    private boolean hasOre(String oreName, ItemStack[] ingredients) {
        for (ItemStack candidate : oreLookup.apply(oreName)) {
            if (candidate == null || candidate.getItem() == null) continue;
            for (ItemStack ingredient : ingredients) {
                // Equivalent to OreDictionary.itemMatches(candidate, ingredient, false).
                if (ingredient != null && ingredient.getItem() == candidate.getItem()
                    && (candidate.getItemDamage() == OreDictionary.WILDCARD_VALUE
                        || candidate.getItemDamage() == ingredient.getItemDamage())) return true;
            }
        }
        return false;
    }

    private static ItemStack toStack(Object key) {
        if (key instanceof ItemStack) return (ItemStack) key;
        if (key instanceof Item) return new ItemStack((Item) key);
        if (key instanceof Block) return new ItemStack((Block) key);
        return null;
    }

    private static boolean hasExactItem(ItemStack required, ItemStack[] ingredients) {
        // ManaMetal compares item identity and exact metadata, ignoring NBT and ingredient order.
        // Each key is an existence check; repeated keys do not consume or reserve an input slot.
        for (ItemStack ingredient : ingredients) {
            if (ingredient != null && ingredient.getItem() == required.getItem()
                && ingredient.getItemDamage() == required.getItemDamage()) return true;
        }
        return false;
    }
}
