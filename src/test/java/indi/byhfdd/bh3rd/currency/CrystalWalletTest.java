package indi.byhfdd.bh3rd.currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import org.junit.Test;

public class CrystalWalletTest {

    @Test
    public void readingANewPlayerDoesNotCreateOrChangeData() {
        NBTTagCompound data = new NBTTagCompound();

        assertEquals(0L, CrystalWallet.getBalance(data));
        assertTrue(data.hasNoTags());
    }

    @Test
    public void balanceSurvivesPlayerDataSerializationAndRespawnCopy() throws Exception {
        NBTTagCompound data = new NBTTagCompound();
        assertTrue(CrystalWallet.tryCredit(data, 35L));
        assertTrue(CrystalWallet.tryCredit(data, 5L));
        assertTrue(CrystalWallet.tryCredit(data, 1L));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        CompressedStreamTools.writeCompressed(data, bytes);
        NBTTagCompound reloaded = CompressedStreamTools.readCompressed(new ByteArrayInputStream(bytes.toByteArray()));
        assertEquals(41L, CrystalWallet.getBalance(reloaded));

        // EntityPlayer.clonePlayer preserves this tag for death and End-portal respawns.
        NBTTagCompound respawnData = new NBTTagCompound();
        respawnData.setTag(EntityPlayer.PERSISTED_NBT_TAG, reloaded.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG));
        assertEquals(41L, CrystalWallet.getBalance(respawnData));
    }

    @Test
    public void creditsAndPaymentsPreserveOtherModsAndOtherPlayerData() {
        NBTTagCompound data = new NBTTagCompound();
        data.setString("otherMod", "unchanged");
        NBTTagCompound persisted = new NBTTagCompound();
        persisted.setLong("otherWallet", 999L);
        data.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);

        assertTrue(CrystalWallet.tryCredit(data, 560L));
        assertTrue(CrystalWallet.trySpend(data, 280L));

        assertEquals(280L, CrystalWallet.getBalance(data));
        assertEquals("unchanged", data.getString("otherMod"));
        assertEquals(999L, data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getLong("otherWallet"));
    }

    @Test
    public void insufficientAndInvalidPaymentsLeaveBalanceUnchanged() {
        NBTTagCompound data = new NBTTagCompound();
        assertTrue(CrystalWallet.tryCredit(data, 279L));
        NBTTagCompound before = (NBTTagCompound) data.copy();

        assertFalse(CrystalWallet.trySpend(data, 280L));
        assertFalse(CrystalWallet.trySpend(data, -1L));
        assertFalse(CrystalWallet.trySpend(data, Long.MIN_VALUE));
        assertFalse(CrystalWallet.trySpend(data, 0L));
        assertEquals(before, data);
    }

    @Test
    public void exactPaymentAndRepeatedPaymentCannotMakeBalanceNegative() {
        NBTTagCompound data = new NBTTagCompound();
        assertTrue(CrystalWallet.tryCredit(data, 280L));

        assertTrue(CrystalWallet.trySpend(data, 280L));
        assertFalse(CrystalWallet.trySpend(data, 280L));
        assertEquals(0L, CrystalWallet.getBalance(data));
    }

    @Test
    public void hugeBalancesUseLongAndRejectOverflowWithoutClampingAwayFunds() {
        NBTTagCompound data = new NBTTagCompound();
        assertTrue(CrystalWallet.tryCredit(data, CrystalWallet.MAX_BALANCE - 35L));
        assertTrue(CrystalWallet.tryCredit(data, 35L));
        assertEquals(Long.MAX_VALUE, CrystalWallet.getBalance(data));
        NBTTagCompound before = (NBTTagCompound) data.copy();

        assertFalse(CrystalWallet.tryCredit(data, 1L));
        assertFalse(CrystalWallet.tryCredit(data, Long.MAX_VALUE));
        assertFalse(CrystalWallet.tryCredit(data, -1L));
        assertFalse(CrystalWallet.tryCredit(data, 0L));
        assertEquals(before, data);
        assertTrue(CrystalWallet.trySpend(data, 280L));
        assertEquals(Long.MAX_VALUE - 280L, CrystalWallet.getBalance(data));
    }

    @Test
    public void negativeSavedBalanceCannotCreateDebtOrPermitAPayment() {
        NBTTagCompound data = new NBTTagCompound();
        NBTTagCompound wallet = new NBTTagCompound();
        wallet.setLong("balance", -100L);
        NBTTagCompound persisted = new NBTTagCompound();
        persisted.setTag("bh3rd:crystalWallet", wallet);
        data.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);

        assertEquals(0L, CrystalWallet.getBalance(data));
        assertFalse(CrystalWallet.trySpend(data, 1L));
        assertTrue(CrystalWallet.tryCredit(data, 35L));
        assertEquals(35L, CrystalWallet.getBalance(data));
    }
}
