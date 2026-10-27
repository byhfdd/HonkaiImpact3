package indi.byhfdd.bh3rd.shop;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** Builds a wallet balance and inventory snapshot for one server-side transaction. */
public final class CrystalShopTrade {

    public static final int PRICE = 280;
    public static final int WITHDRAW_VALUE = 35;
    public static final int SUCCESS = 1;
    public static final int INSUFFICIENT_CRYSTALS = 2;
    public static final int INVENTORY_FULL = 3;
    public static final int NO_CRYSTALS = 5;
    public static final int BALANCE_FULL = 6;

    private static final int INVENTORY_STACK_LIMIT = 64;

    private CrystalShopTrade() {}

    /** Neither the original inventory nor the product is changed, including when delivery fails. */
    public static Result purchase(ItemStack[] inventory, ItemStack product, long balance) {
        requireBalance(balance);
        if (product == null || product.getItem() == null || product.stackSize <= 0) {
            throw new IllegalArgumentException("The shop product must be a nonempty item stack");
        }
        if (balance < PRICE) {
            return failure(INSUFFICIENT_CRYSTALS, balance);
        }
        ItemStack[] result = copyInventory(inventory);
        return insertProduct(result, product) ? new Result(SUCCESS, result, balance - PRICE)
            : failure(INVENTORY_FULL, balance);
    }

    /** Deposits every supported inventory crystal, or leaves everything unchanged on overflow. */
    public static Result deposit(ItemStack[] inventory, long balance, Item one, Item five, Item thirtyFive) {
        requireBalance(balance);
        long total = 0;
        for (ItemStack stack : inventory) {
            long value = (long) denomination(stack, one, five, thirtyFive) * positiveSize(stack);
            if (value > Long.MAX_VALUE - balance - total) {
                return failure(BALANCE_FULL, balance);
            }
            total += value;
        }
        if (total == 0) {
            return failure(NO_CRYSTALS, balance);
        }
        ItemStack[] result = copyInventory(inventory);
        for (int slot = 0; slot < result.length; slot++) {
            if (denomination(result[slot], one, five, thirtyFive) != 0) {
                result[slot] = null;
            }
        }
        return new Result(SUCCESS, result, balance + total);
    }

    /** Restores an ordinary 35-crystal item so the original crafting recipes remain usable. */
    public static Result withdraw(ItemStack[] inventory, long balance, Item thirtyFive) {
        requireBalance(balance);
        if (thirtyFive == null) {
            throw new IllegalArgumentException("The withdrawal item must be registered");
        }
        if (balance < WITHDRAW_VALUE) {
            return failure(INSUFFICIENT_CRYSTALS, balance);
        }
        ItemStack[] result = copyInventory(inventory);
        return insertProduct(result, new ItemStack(thirtyFive))
            ? new Result(SUCCESS, result, balance - WITHDRAW_VALUE) : failure(INVENTORY_FULL, balance);
    }

    private static void requireBalance(long balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("A crystal balance cannot be negative");
        }
    }

    private static Result failure(int status, long balance) {
        return new Result(status, null, balance);
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
        private final long balance;

        private Result(int status, ItemStack[] inventory, long balance) {
            this.status = status;
            this.inventory = inventory;
            this.balance = balance;
        }

        public int getStatus() {
            return status;
        }

        public ItemStack[] getInventory() {
            return inventory;
        }

        public long getBalance() {
            return balance;
        }
    }
}
