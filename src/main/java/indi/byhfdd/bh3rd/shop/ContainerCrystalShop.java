package indi.byhfdd.bh3rd.shop;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

    private final EntityPlayer owner;
    private final World world;
    private final int x;
    private final int y;
    private final int z;
    private int offerIndex = -1;
    private int crystalBalance;
    private int status = READY;
    private int lastOfferIndex = -2;
    private int lastCrystalBalance = -1;
    private int lastStatus = -1;

    public ContainerCrystalShop(EntityPlayer player, World world, int x, int y, int z) {
        this.owner = player;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlotToContainer(new Slot(player.inventory, column + row * 9 + 9, 44 + column * 18, 124 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlotToContainer(new Slot(player.inventory, column, 44 + column * 18, 182));
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

    public int getCrystalBalance() {
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
        crystalBalance = CrystalShopTrade.countCrystals(
            owner.inventory.mainInventory,
            ItemLoader.crystal1,
            ItemLoader.crystal5,
            ItemLoader.crystal35);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter) {
        super.addCraftingToCrafters(crafter);
        sendShopState(crafter);
    }

    private void sendShopState(ICrafting crafter) {
        crafter.sendProgressBarUpdate(this, 0, offerIndex);
        // Window property packets carry signed shorts, so split balances above 32767.
        crafter.sendProgressBarUpdate(this, 1, crystalBalance & 0xFFFF);
        crafter.sendProgressBarUpdate(this, 2, crystalBalance >>> 16);
        crafter.sendProgressBarUpdate(this, 3, status);
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
        switch (id) {
            case 0:
                offerIndex = value >= 0 && value < 7 ? value : -1;
                break;
            case 1:
                crystalBalance = (crystalBalance & 0xFFFF0000) | (value & 0xFFFF);
                break;
            case 2:
                crystalBalance = (crystalBalance & 0xFFFF) | ((value & 0x7FFF) << 16);
                break;
            case 3:
                status = value;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int requestedOffer) {
        if (world.isRemote || player.openContainer != this || !canInteractWith(player)) {
            return false;
        }
        refreshShop();
        if (requestedOffer != offerIndex) {
            status = OFFER_CHANGED;
            detectAndSendChanges();
            return false;
        }
        CrystalShopTrade.Result result = CrystalShopTrade.purchase(
            player.inventory.mainInventory,
            getOfferStack(),
            ItemLoader.crystal1,
            ItemLoader.crystal5,
            ItemLoader.crystal35);
        status = result.getStatus();
        if (status == SUCCESS) {
            System.arraycopy(
                result.getInventory(),
                0,
                player.inventory.mainInventory,
                0,
                player.inventory.mainInventory.length);
            player.inventory.markDirty();
        }
        detectAndSendChanges();
        return status == SUCCESS;
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
