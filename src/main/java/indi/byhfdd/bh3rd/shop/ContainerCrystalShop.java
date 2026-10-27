package indi.byhfdd.bh3rd.shop;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.currency.CrystalWallet;
import indi.byhfdd.bh3rd.loader.BlockLoader;
import indi.byhfdd.bh3rd.loader.ItemLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerCrystalShop extends Container {

    public static final int GUI_ID = 0;
    public static final int PRICE = CrystalShopTrade.PRICE;
    public static final int READY = 0;
    public static final int SUCCESS = CrystalShopTrade.SUCCESS;
    public static final int INSUFFICIENT_CRYSTALS = CrystalShopTrade.INSUFFICIENT_CRYSTALS;
    public static final int INVENTORY_FULL = CrystalShopTrade.INVENTORY_FULL;
    public static final int OFFER_CHANGED = 4;
    public static final int NO_CRYSTALS = CrystalShopTrade.NO_CRYSTALS;
    public static final int BALANCE_FULL = CrystalShopTrade.BALANCE_FULL;
    public static final int DEPOSIT_SUCCESS = 7;
    public static final int WITHDRAW_SUCCESS = 8;
    public static final int DEPOSIT_REQUEST = 7;
    public static final int WITHDRAW_REQUEST = 8;

    private final EntityPlayer owner;
    private final World world;
    private final int x;
    private final int y;
    private final int z;
    private int offerIndex = -1;
    private long crystalBalance;
    private long pendingCrystalBalance;
    private int pendingBalanceParts;
    private int status = READY;
    private int lastOfferIndex = -2;
    private long lastCrystalBalance = -1;
    private int lastStatus = -1;

    public ContainerCrystalShop(EntityPlayer player, World world, int x, int y, int z) {
        this.owner = player;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlotToContainer(new Slot(player.inventory, column + row * 9 + 9, 44 + column * 18, 145 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlotToContainer(new Slot(player.inventory, column, 44 + column * 18, 203));
        }
        if (!world.isRemote) {
            refreshShop();
        }
    }

    public int getOfferIndex() {
        return offerIndex;
    }

    public ItemStack getOfferStack() {
        return DailySupply.getOfferStack(offerIndex);
    }

    public long getCrystalBalance() {
        return crystalBalance;
    }

    public int getStatus() {
        return status;
    }

    private void refreshShop() {
        int currentOffer = DailySupply.getCurrentOfferIndex();
        if (currentOffer != offerIndex) {
            offerIndex = currentOffer;
            status = READY;
        }
        crystalBalance = CrystalWallet.getBalance(owner);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter) {
        super.addCraftingToCrafters(crafter);
        sendShopState(crafter);
    }

    private void sendShopState(ICrafting crafter) {
        crafter.sendProgressBarUpdate(this, 0, offerIndex);
        // Window property packets carry signed shorts; publish all four parts of the long together.
        for (int part = 0; part < 4; part++) {
            crafter.sendProgressBarUpdate(this, part + 1, (int) (crystalBalance >>> (part * 16)) & 0xFFFF);
        }
        crafter.sendProgressBarUpdate(this, 5, status);
    }

    @Override
    public void detectAndSendChanges() {
        if (!world.isRemote) {
            refreshShop();
        }
        super.detectAndSendChanges();
        if (!world.isRemote
            && (offerIndex != lastOfferIndex || crystalBalance != lastCrystalBalance || status != lastStatus)) {
            for (ICrafting crafter : crafters) {
                sendShopState(crafter);
            }
            lastOfferIndex = offerIndex;
            lastCrystalBalance = crystalBalance;
            lastStatus = status;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if (id >= 1 && id <= 4) {
            int part = id - 1;
            if (part == 0) {
                pendingCrystalBalance = 0;
                pendingBalanceParts = 0;
            }
            pendingCrystalBalance |= (long) (value & 0xFFFF) << (part * 16);
            pendingBalanceParts |= 1 << part;
            if (part == 3 && pendingBalanceParts == 15 && pendingCrystalBalance >= 0) {
                crystalBalance = pendingCrystalBalance;
            }
            return;
        }
        switch (id) {
            case 0:
                offerIndex = value >= 0 && value < 7 ? value : -1;
                break;
            case 5:
                status = value;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int request) {
        if (world.isRemote || player.openContainer != this || !canInteractWith(player)) {
            return false;
        }
        if (request < 0 || request > WITHDRAW_REQUEST) {
            return false;
        }
        refreshShop();
        if (request < DEPOSIT_REQUEST && request != offerIndex) {
            status = OFFER_CHANGED;
            detectAndSendChanges();
            return false;
        }
        long previousBalance = crystalBalance;
        CrystalShopTrade.Result result;
        if (request == DEPOSIT_REQUEST) {
            result = CrystalShopTrade.deposit(
                player.inventory.mainInventory,
                previousBalance,
                ItemLoader.crystal1,
                ItemLoader.crystal5,
                ItemLoader.crystal35);
        } else if (request == WITHDRAW_REQUEST) {
            result = CrystalShopTrade.withdraw(player.inventory.mainInventory, previousBalance, ItemLoader.crystal35);
        } else {
            result = CrystalShopTrade.purchase(player.inventory.mainInventory, getOfferStack(), previousBalance);
        }
        status = result.getStatus();
        if (status == SUCCESS) {
            if (CrystalWallet.getBalance(player) != previousBalance) {
                status = READY;
                detectAndSendChanges();
                return false;
            }
            long nextBalance = result.getBalance();
            boolean committed = nextBalance > previousBalance
                ? CrystalWallet.tryCredit(player, nextBalance - previousBalance)
                : CrystalWallet.trySpend(player, previousBalance - nextBalance);
            if (!committed) {
                status = nextBalance > previousBalance ? BALANCE_FULL : INSUFFICIENT_CRYSTALS;
                detectAndSendChanges();
                return false;
            }
            System.arraycopy(
                result.getInventory(),
                0,
                player.inventory.mainInventory,
                0,
                player.inventory.mainInventory.length);
            player.inventory.markDirty();
            status = request == DEPOSIT_REQUEST ? DEPOSIT_SUCCESS
                : request == WITHDRAW_REQUEST ? WITHDRAW_SUCCESS : SUCCESS;
        }
        detectAndSendChanges();
        return result.getStatus() == SUCCESS;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return player == owner && player.worldObj == world
            && !player.isDead
            && world.getBlock(x, y, z) == BlockLoader.CrystalShop
            && player.getDistanceSq(x + 0.5D, y + 0.5D, z + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        if (index < 0 || index >= inventorySlots.size()) {
            return null;
        }
        Slot slot = inventorySlots.get(index);
        if (!slot.getHasStack()) {
            return null;
        }
        ItemStack stack = slot.getStack();
        ItemStack original = stack.copy();
        if (index < 27 ? !mergeItemStack(stack, 27, 36, false) : !mergeItemStack(stack, 0, 27, false)) {
            return null;
        }
        if (stack.stackSize == 0) {
            slot.putStack(null);
        } else {
            slot.onSlotChanged();
        }
        if (stack.stackSize == original.stackSize) {
            return null;
        }
        slot.onPickupFromSlot(player, stack);
        return original;
    }
}
