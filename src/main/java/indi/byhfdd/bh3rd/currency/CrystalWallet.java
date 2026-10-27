package indi.byhfdd.bh3rd.currency;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/** Server-owned crystal balance, saved with the player and retained by Forge after respawning. */
public final class CrystalWallet {

    public static final long MAX_BALANCE = Long.MAX_VALUE;

    private static final String WALLET_TAG = "bh3rd:crystalWallet";
    private static final String BALANCE_TAG = "balance";

    private CrystalWallet() {}

    /** Clients obtain the displayed balance from the shop container, not from their local player NBT. */
    public static long getBalance(EntityPlayer player) {
        return player == null ? 0L : getBalance(player.getEntityData());
    }

    public static boolean tryCredit(EntityPlayer player, long amount) {
        return isServerPlayer(player) && tryCredit(player.getEntityData(), amount);
    }

    public static boolean trySpend(EntityPlayer player, long amount) {
        return isServerPlayer(player) && trySpend(player.getEntityData(), amount);
    }

    private static boolean isServerPlayer(EntityPlayer player) {
        return player != null && player.worldObj != null && !player.worldObj.isRemote;
    }

    static long getBalance(NBTTagCompound entityData) {
        NBTTagCompound persisted = entityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        return Math.max(0L, persisted.getCompoundTag(WALLET_TAG).getLong(BALANCE_TAG));
    }

    static boolean tryCredit(NBTTagCompound entityData, long amount) {
        long balance = getBalance(entityData);
        if (amount <= 0L || amount > MAX_BALANCE - balance) return false;
        setBalance(entityData, balance + amount);
        return true;
    }

    static boolean trySpend(NBTTagCompound entityData, long amount) {
        long balance = getBalance(entityData);
        if (amount <= 0L || balance < amount) return false;
        setBalance(entityData, balance - amount);
        return true;
    }

    private static void setBalance(NBTTagCompound entityData, long balance) {
        NBTTagCompound persisted = entityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        NBTTagCompound wallet = persisted.getCompoundTag(WALLET_TAG);
        wallet.setLong(BALANCE_TAG, balance);
        persisted.setTag(WALLET_TAG, wallet);
        entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
    }
}
