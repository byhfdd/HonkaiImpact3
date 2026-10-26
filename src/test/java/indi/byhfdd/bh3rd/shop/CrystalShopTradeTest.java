package indi.byhfdd.bh3rd.shop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.junit.Test;

public class CrystalShopTradeTest {

    private final Item one = new Item();
    private final Item five = new Item();
    private final Item thirtyFive = new Item();
    private final Item card = new Item().setMaxStackSize(40);
    private final Item other = new Item();

    @Test
    public void insufficientBalanceLeavesEveryInputUnchanged() {
        ItemStack crystals = tagged(new ItemStack(thirtyFive, 7), "currency");
        ItemStack product = tagged(new ItemStack(card), "product");
        ItemStack[] inventory = { crystals, null };

        CrystalShopTrade.Result result = purchase(inventory, product);

        assertEquals(CrystalShopTrade.INSUFFICIENT_CRYSTALS, result.getStatus());
        assertNull(result.getInventory());
        assertSame(crystals, inventory[0]);
        assertEquals(7, crystals.stackSize);
        assertEquals(
            "currency",
            crystals.getTagCompound()
                .getString("test"));
        assertNull(inventory[1]);
        assertEquals(1, product.stackSize);
        assertEquals(
            "product",
            product.getTagCompound()
                .getString("test"));
    }

    @Test
    public void eightThirtyFiveCrystalsPayForOneCard() {
        ItemStack original = new ItemStack(thirtyFive, 8);
        ItemStack product = new ItemStack(card);
        ItemStack[] inventory = { original };

        CrystalShopTrade.Result result = purchase(inventory, product);

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(0, count(result.getInventory()));
        assertSame(card, result.getInventory()[0].getItem());
        assertEquals(1, result.getInventory()[0].stackSize);
        assertNotSame(product, result.getInventory()[0]);
        assertSame(original, inventory[0]);
        assertEquals(8, original.stackSize);
    }

    @Test
    public void mixedDenominationsPayExactly280FromLargestFirst() {
        ItemStack[] inventory = { new ItemStack(one, 5), new ItemStack(five, 7), new ItemStack(thirtyFive, 7), null };

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(card));

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(5, count(result.getInventory()));
        assertSame(one, result.getInventory()[0].getItem());
        assertEquals(5, result.getInventory()[0].stackSize);
        assertEquals(7, inventory[1].stackSize);
        assertEquals(7, inventory[2].stackSize);
    }

    @Test
    public void allThreeDenominationsCanBeUsedInOnePayment() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, 7), new ItemStack(five, 6), new ItemStack(one, 5) };

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(card));

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(0, count(result.getInventory()));
        assertSame(card, result.getInventory()[0].getItem());
        assertNull(result.getInventory()[1]);
        assertNull(result.getInventory()[2]);
    }

    @Test
    public void paymentCanFreeTheOnlyDeliverySlot() {
        ItemStack[] inventory = { new ItemStack(other, 64), new ItemStack(thirtyFive, 8) };

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(card));

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertSame(other, result.getInventory()[0].getItem());
        assertSame(card, result.getInventory()[1].getItem());
        assertEquals(8, inventory[1].stackSize);
    }

    @Test
    public void fullInventoryRejectsDeliveryWithoutCharging() {
        ItemStack[] inventory = { tagged(new ItemStack(thirtyFive, 9), "retained"), new ItemStack(other, 64) };
        ItemStack original = inventory[0];

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(card));

        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertNull(result.getInventory());
        assertSame(original, inventory[0]);
        assertEquals(9, original.stackSize);
        assertEquals(
            "retained",
            original.getTagCompound()
                .getString("test"));
    }

    @Test
    public void smallerDenominationsCanBePreferredToFreeADeliverySlot() {
        ItemStack[] inventory = fullInventory();
        inventory[0] = tagged(new ItemStack(thirtyFive, 9), "large");
        inventory[1] = tagged(new ItemStack(five, 56), "small");

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(card));

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(315, count(result.getInventory()));
        assertSame(thirtyFive, result.getInventory()[0].getItem());
        assertEquals(9, result.getInventory()[0].stackSize);
        assertEquals(
            "large",
            result.getInventory()[0].getTagCompound()
                .getString("test"));
        assertSame(card, result.getInventory()[1].getItem());
        assertEquals(1, result.getInventory()[1].stackSize);
        assertEquals(9, inventory[0].stackSize);
        assertEquals(56, inventory[1].stackSize);
        assertEquals(
            "small",
            inventory[1].getTagCompound()
                .getString("test"));
    }

    @Test
    public void inexactPaymentAfterFreeingACandidateSlotLeavesInputsUnchanged() {
        ItemStack[] inventory = fullInventory();
        inventory[0] = tagged(new ItemStack(thirtyFive, 9), "large");
        inventory[1] = tagged(new ItemStack(one), "candidate");
        ItemStack originalCandidate = inventory[1];

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(card));

        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertNull(result.getInventory());
        assertEquals(316, count(inventory));
        assertSame(originalCandidate, inventory[1]);
        assertEquals(9, inventory[0].stackSize);
        assertEquals(
            "large",
            inventory[0].getTagCompound()
                .getString("test"));
        assertEquals(1, originalCandidate.stackSize);
        assertEquals(
            "candidate",
            originalCandidate.getTagCompound()
                .getString("test"));
    }

    @Test
    public void aFailedCandidateDoesNotChangeTheNextPaymentAttempt() {
        ItemStack[] inventory = fullInventory();
        inventory[0] = tagged(new ItemStack(one), "retained");
        inventory[1] = new ItemStack(thirtyFive, 9);
        inventory[2] = new ItemStack(five, 56);

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(card));

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(316, count(result.getInventory()));
        assertSame(one, result.getInventory()[0].getItem());
        assertEquals(1, result.getInventory()[0].stackSize);
        assertEquals(
            "retained",
            result.getInventory()[0].getTagCompound()
                .getString("test"));
        assertSame(card, result.getInventory()[2].getItem());
        assertEquals(9, inventory[1].stackSize);
        assertEquals(56, inventory[2].stackSize);
    }

    @Test
    public void matchingProductCanMergeIntoExistingStack() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, 9), tagged(new ItemStack(card, 39, 2), "same") };
        ItemStack product = tagged(new ItemStack(card, 1, 2), "same");

        CrystalShopTrade.Result result = purchase(inventory, product);

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(40, result.getInventory()[1].stackSize);
        assertEquals(39, inventory[1].stackSize);
        assertEquals(1, product.stackSize);
        assertNotSame(inventory[1], result.getInventory()[1]);
        assertNotSame(inventory[1].getTagCompound(), result.getInventory()[1].getTagCompound());
    }

    @Test
    public void productCannotExceedItsFortyItemLimit() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, 9), new ItemStack(card, 40) };

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(card));

        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(40, inventory[1].stackSize);
        assertEquals(9, inventory[0].stackSize);
    }

    @Test
    public void inventoryStackLimitStillAppliesToItemsAllowingMoreThan64() {
        Item largeStackItem = new Item().setMaxStackSize(128);
        ItemStack[] inventory = { new ItemStack(thirtyFive, 9), new ItemStack(largeStackItem, 64) };

        CrystalShopTrade.Result result = purchase(inventory, new ItemStack(largeStackItem));

        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(64, inventory[1].stackSize);
        assertEquals(9, inventory[0].stackSize);
    }

    @Test
    public void productMetadataAndTagsMustMatchBeforeMerging() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, 9), tagged(new ItemStack(card, 1, 1), "same"),
            tagged(new ItemStack(card, 1, 0), "different") };

        CrystalShopTrade.Result result = purchase(inventory, tagged(new ItemStack(card), "same"));

        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(1, inventory[1].stackSize);
        assertEquals(1, inventory[2].stackSize);
        assertEquals(9, inventory[0].stackSize);
    }

    @Test
    public void nonCurrencyMetadataAndNbtArePreserved() {
        ItemStack[] inventory = { tagged(new ItemStack(thirtyFive, 9), "money"),
            tagged(new ItemStack(thirtyFive, 12, 1), "variant"), tagged(new ItemStack(other, 8), "unrelated"), null };

        CrystalShopTrade.Result result = purchase(inventory, tagged(new ItemStack(card), "product"));

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(35, count(result.getInventory()));
        assertEquals(1, result.getInventory()[0].stackSize);
        assertEquals(
            "money",
            result.getInventory()[0].getTagCompound()
                .getString("test"));
        assertEquals(12, result.getInventory()[1].stackSize);
        assertEquals(1, result.getInventory()[1].getItemDamage());
        assertEquals(
            "variant",
            result.getInventory()[1].getTagCompound()
                .getString("test"));
        assertSame(other, result.getInventory()[2].getItem());
        assertEquals(
            "unrelated",
            result.getInventory()[2].getTagCompound()
                .getString("test"));
        assertEquals(
            "product",
            result.getInventory()[3].getTagCompound()
                .getString("test"));
        result.getInventory()[0].getTagCompound()
            .setString("test", "changed");
        assertEquals(
            "money",
            inventory[0].getTagCompound()
                .getString("test"));
    }

    @Test
    public void balanceIgnoresEmptyNegativeAndOtherItemStacks() {
        ItemStack[] inventory = { new ItemStack(one, 0), new ItemStack(five, -10), new ItemStack(thirtyFive, 10, 1),
            new ItemStack(other, 64), new ItemStack(one, 4), new ItemStack(five, 3), new ItemStack(thirtyFive, 2),
            null };

        assertEquals(89, count(inventory));
    }

    @Test
    public void hugeBalancesClampInsteadOfOverflowing() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, Integer.MAX_VALUE) };

        assertEquals(Integer.MAX_VALUE, count(inventory));
        CrystalShopTrade.Result result = purchase(new ItemStack[] { inventory[0], null }, new ItemStack(card));
        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertTrue(result.getInventory()[0].stackSize > 0);
        assertEquals(Integer.MAX_VALUE - 8, result.getInventory()[0].stackSize);
        assertEquals(Integer.MAX_VALUE, inventory[0].stackSize);
    }

    private CrystalShopTrade.Result purchase(ItemStack[] inventory, ItemStack product) {
        return CrystalShopTrade.purchase(inventory, product, one, five, thirtyFive);
    }

    private int count(ItemStack[] inventory) {
        return CrystalShopTrade.countCrystals(inventory, one, five, thirtyFive);
    }

    private ItemStack[] fullInventory() {
        ItemStack[] inventory = new ItemStack[36];
        for (int slot = 0; slot < inventory.length; slot++) {
            inventory[slot] = new ItemStack(other, 64);
        }
        return inventory;
    }

    private static ItemStack tagged(ItemStack stack, String value) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("test", value);
        stack.setTagCompound(tag);
        return stack;
    }
}
