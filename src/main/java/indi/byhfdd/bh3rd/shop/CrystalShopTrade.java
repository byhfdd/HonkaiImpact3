package indi.byhfdd.bh3rd.shop;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** Builds an inventory snapshot so payment and delivery can be committed together by the server. */
public final class CrystalShopTrade {

    public static final int PRICE = 280;
    public static final int SUCCESS = 1;
    public static final int INSUFFICIENT_CRYSTALS = 2;
    public static final int INVENTORY_FULL = 3;

    private static final int INVENTORY_STACK_LIMIT = 64;

    private CrystalShopTrade() {}

    public static int countCrystals(ItemStack[] inventory, Item one, Item five, Item thirtyFive) {
        long total = 0;
        for (ItemStack stack : inventory) {
            total += (long) denomination(stack, one, five, thirtyFive) * positiveSize(stack);
        }
        return (int) Math.min(total, Integer.MAX_VALUE);
    }

    /** Neither the original inventory nor the product is changed, including when delivery fails. */
    public static Result purchase(ItemStack[] inventory, ItemStack product, Item one, Item five, Item thirtyFive) {
        if (product == null || product.getItem() == null || product.stackSize <= 0) {
            throw new IllegalArgumentException("The shop product must be a nonempty item stack");
        }
        if (countCrystals(inventory, one, five, thirtyFive) < PRICE) {
            return new Result(INSUFFICIENT_CRYSTALS, null);
        }

        ItemStack[] result = copyInventory(inventory);
        // All denominations divide 280, and 35 is a multiple of 5, so no change is needed.
        if (!removePayment(result, PRICE, one, five, thirtyFive)) {
            return new Result(INSUFFICIENT_CRYSTALS, null);
        }
        if (insertProduct(result, product)) {
            return new Result(SUCCESS, result);
        }

        // A different payment can empty a currency slot even when the largest-first payment cannot.
        for (int slot = 0; slot < inventory.length; slot++) {
            long slotValue = (long) denomination(inventory[slot], one, five, thirtyFive)
                * positiveSize(inventory[slot]);
            if (slotValue <= 0 || slotValue > PRICE) {
                continue;
            }
            result = copyInventory(inventory);
            result[slot] = null;
            if (removePayment(result, PRICE - (int) slotValue, one, five, thirtyFive)
                && insertProduct(result, product)) {
                return new Result(SUCCESS, result);
            }
        }
        return new Result(INVENTORY_FULL, null);
    }

    private static boolean removePayment(ItemStack[] inventory, int remaining, Item one, Item five, Item thirtyFive) {
        remaining = removeCurrency(inventory, thirtyFive, 35, remaining);
        remaining = removeCurrency(inventory, five, 5, remaining);
        return removeCurrency(inventory, one, 1, remaining) == 0;
    }

    private static boolean insertProduct(ItemStack[] inventory, ItemStack product) {
        ItemStack delivery = product.copy();
        for (ItemStack stack : inventory) {
            if (positiveSize(stack) > 0 && stack.getItem() == delivery.getItem()
                && stack.getItemDamage() == delivery.getItemDamage()
                && ItemStack.areItemStackTagsEqual(stack, delivery)) {
                int capacity = Math.min(INVENTORY_STACK_LIMIT, stack.getMaxStackSize()) - stack.stackSize;
                int inserted = Math.min(Math.max(capacity, 0), delivery.stackSize);
                stack.stackSize += inserted;
                delivery.stackSize -= inserted;
                if (delivery.stackSize == 0) {
                    return true;
                }
            }
        }

        int stackLimit = Math.min(INVENTORY_STACK_LIMIT, delivery.getMaxStackSize());
        if (stackLimit <= 0) {
            return false;
        }
        for (int slot = 0; slot < inventory.length; slot++) {
            if (positiveSize(inventory[slot]) == 0) {
                ItemStack inserted = delivery.copy();
                inserted.stackSize = Math.min(stackLimit, delivery.stackSize);
                inventory[slot] = inserted;
                delivery.stackSize -= inserted.stackSize;
                if (delivery.stackSize == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int removeCurrency(ItemStack[] inventory, Item currency, int value, int remaining) {
        for (int slot = 0; slot < inventory.length && remaining >= value; slot++) {
            ItemStack stack = inventory[slot];
            if (positiveSize(stack) > 0 && currency != null
                && stack.getItem() == currency
                && stack.getItemDamage() == 0) {
                int removed = Math.min(stack.stackSize, remaining / value);
                stack.stackSize -= removed;
                remaining -= removed * value;
                if (stack.stackSize == 0) {
                    inventory[slot] = null;
                }
            }
        }
        return remaining;
    }

    private static int denomination(ItemStack stack, Item one, Item five, Item thirtyFive) {
        if (positiveSize(stack) == 0 || stack.getItemDamage() != 0 || stack.getItem() == null) {
            return 0;
        }
        if (stack.getItem() == thirtyFive) {
            return 35;
        }
        if (stack.getItem() == five) {
            return 5;
        }
        return stack.getItem() == one ? 1 : 0;
    }

    private static int positiveSize(ItemStack stack) {
        return stack == null ? 0 : Math.max(0, stack.stackSize);
    }

    private static ItemStack[] copyInventory(ItemStack[] inventory) {
        ItemStack[] copy = new ItemStack[inventory.length];
        for (int slot = 0; slot < inventory.length; slot++) {
            copy[slot] = ItemStack.copyItemStack(inventory[slot]);
        }
        return copy;
    }

    public static final class Result {

        private final int status;
        private final ItemStack[] inventory;

        private Result(int status, ItemStack[] inventory) {
            this.status = status;
            this.inventory = inventory;
        }

        public int getStatus() {
            return status;
        }

        public ItemStack[] getInventory() {
            return inventory;
        }
    }
}
