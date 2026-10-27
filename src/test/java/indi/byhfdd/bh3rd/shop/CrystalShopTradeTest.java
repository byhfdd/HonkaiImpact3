package indi.byhfdd.bh3rd.shop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

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
    public void insufficientWalletDoesNotUseInventoryCrystalsOrChangeInputs() {
        ItemStack crystals = tagged(new ItemStack(thirtyFive, 64), "currency");
        ItemStack product = tagged(new ItemStack(card), "product");
        ItemStack[] inventory = { crystals, null };

        CrystalShopTrade.Result result = CrystalShopTrade.purchase(inventory, product, 279);

        assertEquals(CrystalShopTrade.INSUFFICIENT_CRYSTALS, result.getStatus());
        assertEquals(279, result.getBalance());
        assertNull(result.getInventory());
        assertSame(crystals, inventory[0]);
        assertEquals(64, crystals.stackSize);
        assertEquals("currency", crystals.getTagCompound().getString("test"));
        assertEquals("product", product.getTagCompound().getString("test"));
        assertNull(inventory[1]);
    }

    @Test
    public void walletPaysExactly280AndInventoryCrystalsRemainAvailableForCrafting() {
        ItemStack crystals = new ItemStack(thirtyFive, 8);
        ItemStack product = new ItemStack(card);
        ItemStack[] inventory = { crystals, null };

        CrystalShopTrade.Result result = CrystalShopTrade.purchase(inventory, product, 280);

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(0, result.getBalance());
        assertEquals(8, result.getInventory()[0].stackSize);
        assertSame(card, result.getInventory()[1].getItem());
        assertNotSame(product, result.getInventory()[1]);
        assertNotSame(crystals, result.getInventory()[0]);
        assertSame(crystals, inventory[0]);
        assertNull(inventory[1]);
    }

    @Test
    public void fullInventoryRejectsPurchaseWithoutChargingOrConsumingPhysicalCrystals() {
        ItemStack crystals = tagged(new ItemStack(thirtyFive, 8), "retained");
        ItemStack[] inventory = { crystals, new ItemStack(other, 64) };

        CrystalShopTrade.Result result = CrystalShopTrade.purchase(inventory, new ItemStack(card), 900);

        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(900, result.getBalance());
        assertNull(result.getInventory());
        assertSame(crystals, inventory[0]);
        assertEquals(8, crystals.stackSize);
        assertEquals("retained", crystals.getTagCompound().getString("test"));
    }

    @Test
    public void matchingProductCanMergeWithoutChangingInputStacksOrTags() {
        ItemStack[] inventory = { tagged(new ItemStack(card, 39, 2), "same") };
        ItemStack product = tagged(new ItemStack(card, 1, 2), "same");

        CrystalShopTrade.Result result = CrystalShopTrade.purchase(inventory, product, 300);

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(20, result.getBalance());
        assertEquals(40, result.getInventory()[0].stackSize);
        assertEquals(39, inventory[0].stackSize);
        assertEquals(1, product.stackSize);
        assertNotSame(inventory[0].getTagCompound(), result.getInventory()[0].getTagCompound());
        result.getInventory()[0].getTagCompound().setString("test", "changed");
        assertEquals("same", inventory[0].getTagCompound().getString("test"));
    }

    @Test
    public void productCannotExceedItsFortyItemLimit() {
        ItemStack[] inventory = { new ItemStack(card, 40) };
        CrystalShopTrade.Result result = CrystalShopTrade.purchase(inventory, new ItemStack(card), 280);
        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(280, result.getBalance());
        assertEquals(40, inventory[0].stackSize);
    }

    @Test
    public void vanillaInventoryLimitStillAppliesToItemsAllowingMoreThan64() {
        Item largeStackItem = new Item().setMaxStackSize(128);
        ItemStack[] inventory = { new ItemStack(largeStackItem, 64) };
        CrystalShopTrade.Result result = CrystalShopTrade.purchase(inventory, new ItemStack(largeStackItem), 280);
        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(280, result.getBalance());
        assertEquals(64, inventory[0].stackSize);
    }

    @Test
    public void productMetadataAndTagsMustMatchBeforeMerging() {
        ItemStack[] inventory = { tagged(new ItemStack(card, 1, 1), "same"),
            tagged(new ItemStack(card, 1, 0), "different") };
        CrystalShopTrade.Result result = CrystalShopTrade.purchase(inventory, tagged(new ItemStack(card), "same"), 280);
        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertNull(result.getInventory());
        assertEquals(1, inventory[0].stackSize);
        assertEquals(1, inventory[1].stackSize);
    }

    @Test
    public void failedMultiStackDeliveryDoesNotPartiallyFillOriginalStack() {
        ItemStack[] inventory = { new ItemStack(card, 39), new ItemStack(other, 64) };
        CrystalShopTrade.Result result = CrystalShopTrade.purchase(inventory, new ItemStack(card, 2), 280);
        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(39, inventory[0].stackSize);
        assertEquals(280, result.getBalance());
    }

    @Test
    public void purchaseHandlesLongBalancesWithoutIntegerTruncation() {
        CrystalShopTrade.Result result = CrystalShopTrade.purchase(new ItemStack[1], new ItemStack(card), Long.MAX_VALUE);
        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(Long.MAX_VALUE - 280, result.getBalance());
    }

    @Test
    public void depositCountsEveryDenominationAndLeavesOtherItemsAndMetadataIntact() {
        ItemStack[] inventory = { tagged(new ItemStack(one, 4), "currency"), new ItemStack(five, 3),
            new ItemStack(thirtyFive, 2), tagged(new ItemStack(thirtyFive, 7, 1), "variant"),
            tagged(new ItemStack(other, 6), "unrelated"), null };

        CrystalShopTrade.Result result = deposit(inventory, 100);

        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(189, result.getBalance());
        assertNull(result.getInventory()[0]);
        assertNull(result.getInventory()[1]);
        assertNull(result.getInventory()[2]);
        assertEquals(7, result.getInventory()[3].stackSize);
        assertEquals(1, result.getInventory()[3].getItemDamage());
        assertEquals("variant", result.getInventory()[3].getTagCompound().getString("test"));
        assertSame(other, result.getInventory()[4].getItem());
        assertEquals("unrelated", result.getInventory()[4].getTagCompound().getString("test"));
        assertNotSame(inventory[4].getTagCompound(), result.getInventory()[4].getTagCompound());
        assertEquals(4, inventory[0].stackSize);
        assertEquals("currency", inventory[0].getTagCompound().getString("test"));
    }

    @Test
    public void depositDoesNotCountEmptyNegativeOrVariantStacks() {
        ItemStack[] inventory = { new ItemStack(one, 0), new ItemStack(five, -10), new ItemStack(thirtyFive, 10, 1),
            new ItemStack(other, 64), null };
        CrystalShopTrade.Result result = deposit(inventory, 99);
        assertEquals(CrystalShopTrade.NO_CRYSTALS, result.getStatus());
        assertEquals(99, result.getBalance());
        assertNull(result.getInventory());
        assertEquals(-10, inventory[1].stackSize);
    }

    @Test
    public void depositedSnapshotCannotCreditTheSameCrystalsTwice() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, 8), null };
        CrystalShopTrade.Result first = deposit(inventory, 0);
        CrystalShopTrade.Result repeated = deposit(first.getInventory(), first.getBalance());
        assertEquals(CrystalShopTrade.NO_CRYSTALS, repeated.getStatus());
        assertEquals(280, repeated.getBalance());
        assertNull(repeated.getInventory());
    }

    @Test
    public void depositCanExactlyReachTheMaximumBalance() {
        ItemStack[] inventory = { new ItemStack(one), new ItemStack(thirtyFive) };
        CrystalShopTrade.Result result = deposit(inventory, Long.MAX_VALUE - 36);
        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(Long.MAX_VALUE, result.getBalance());
        assertNull(result.getInventory()[0]);
        assertNull(result.getInventory()[1]);
        assertEquals(1, inventory[0].stackSize);
    }

    @Test
    public void depositOverflowRejectsAllStacksWithoutPartialCreditOrConsumption() {
        ItemStack first = tagged(new ItemStack(one), "retained");
        ItemStack[] inventory = { first, new ItemStack(thirtyFive) };
        CrystalShopTrade.Result result = deposit(inventory, Long.MAX_VALUE - 35);
        assertEquals(CrystalShopTrade.BALANCE_FULL, result.getStatus());
        assertEquals(Long.MAX_VALUE - 35, result.getBalance());
        assertNull(result.getInventory());
        assertSame(first, inventory[0]);
        assertEquals(1, inventory[1].stackSize);
        assertEquals("retained", first.getTagCompound().getString("test"));
    }

    @Test
    public void depositUsesLongArithmeticForOversizedStacks() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, Integer.MAX_VALUE), new ItemStack(five, Integer.MAX_VALUE) };
        CrystalShopTrade.Result result = deposit(inventory, (long) Integer.MAX_VALUE + 1);
        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals((long) Integer.MAX_VALUE * 41 + 1, result.getBalance());
        assertEquals(Integer.MAX_VALUE, inventory[0].stackSize);
        assertNull(result.getInventory()[0]);
        assertNull(result.getInventory()[1]);
    }

    @Test
    public void withdrawalCreatesOneCraftingCrystalAndChargesExactly35() {
        ItemStack[] inventory = { tagged(new ItemStack(other), "retained"), null };
        CrystalShopTrade.Result result = CrystalShopTrade.withdraw(inventory, 35, thirtyFive);
        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(0, result.getBalance());
        assertSame(thirtyFive, result.getInventory()[1].getItem());
        assertEquals(1, result.getInventory()[1].stackSize);
        assertEquals(0, result.getInventory()[1].getItemDamage());
        assertNull(result.getInventory()[1].getTagCompound());
        assertNull(inventory[1]);
        assertEquals("retained", result.getInventory()[0].getTagCompound().getString("test"));
    }

    @Test
    public void withdrawalMergesIntoAnExistingOrdinaryCrystalStack() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, 63) };
        CrystalShopTrade.Result result = CrystalShopTrade.withdraw(inventory, 100, thirtyFive);
        assertEquals(CrystalShopTrade.SUCCESS, result.getStatus());
        assertEquals(65, result.getBalance());
        assertEquals(64, result.getInventory()[0].stackSize);
        assertEquals(63, inventory[0].stackSize);
    }

    @Test
    public void withdrawalWithNoDeliverySpaceDoesNotCharge() {
        ItemStack[] inventory = { new ItemStack(thirtyFive, 64), new ItemStack(other, 64) };
        CrystalShopTrade.Result result = CrystalShopTrade.withdraw(inventory, Long.MAX_VALUE, thirtyFive);
        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(Long.MAX_VALUE, result.getBalance());
        assertNull(result.getInventory());
        assertEquals(64, inventory[0].stackSize);
    }

    @Test
    public void withdrawalDoesNotMergeIntoTaggedOrVariantCrystals() {
        ItemStack[] inventory = { tagged(new ItemStack(thirtyFive), "named"), new ItemStack(thirtyFive, 1, 1) };
        CrystalShopTrade.Result result = CrystalShopTrade.withdraw(inventory, 35, thirtyFive);
        assertEquals(CrystalShopTrade.INVENTORY_FULL, result.getStatus());
        assertEquals(35, result.getBalance());
        assertEquals("named", inventory[0].getTagCompound().getString("test"));
    }

    @Test
    public void insufficientWithdrawalLeavesInventoryUntouched() {
        ItemStack[] inventory = { null };
        CrystalShopTrade.Result result = CrystalShopTrade.withdraw(inventory, 34, thirtyFive);
        assertEquals(CrystalShopTrade.INSUFFICIENT_CRYSTALS, result.getStatus());
        assertEquals(34, result.getBalance());
        assertNull(result.getInventory());
        assertNull(inventory[0]);
    }

    private CrystalShopTrade.Result deposit(ItemStack[] inventory, long balance) {
        return CrystalShopTrade.deposit(inventory, balance, one, five, thirtyFive);
    }

    private static ItemStack tagged(ItemStack stack, String value) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("test", value);
        stack.setTagCompound(tag);
        return stack;
    }
}
