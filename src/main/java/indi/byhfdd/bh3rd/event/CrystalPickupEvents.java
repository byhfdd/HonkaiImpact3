package indi.byhfdd.bh3rd.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import indi.byhfdd.bh3rd.currency.CrystalCurrency;
import indi.byhfdd.bh3rd.currency.CrystalWallet;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public final class CrystalPickupEvents {

    // ManaMetal checks team ownership and manor pickup permission at HIGH priority first.
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.entityPlayer;
        EntityItem dropped = event.item;
        if (event.isCanceled() || event.getResult() == Event.Result.DENY || player == null
            || player.worldObj.isRemote || player.isDead || player instanceof FakePlayer
            || dropped == null || dropped.isDead || dropped.worldObj != player.worldObj
            || dropped.delayBeforeCanPickup > 0) return;

        // Forge fires this event before vanilla checks the dropped item's owner.
        String owner = dropped.func_145798_i();
        if (owner != null && (long) dropped.lifespan - dropped.age > 200L
            && !owner.equals(player.getCommandSenderName())) return;

        ItemStack stack = dropped.getEntityItem();
        long value = CrystalCurrency.value(stack);
        if (value <= 0L) return;

        // If the wallet is full, keep the complete drop and do not fall back to inventory pickup.
        event.setCanceled(true);
        if (!CrystalWallet.tryCredit(player, value)) return;

        int count = stack.stackSize;
        player.onItemPickup(dropped, count);
        stack.stackSize = 0;
        dropped.setDead();
        player.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, 1.5F);
    }
}
