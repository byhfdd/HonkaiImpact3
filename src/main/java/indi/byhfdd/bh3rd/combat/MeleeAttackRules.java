package indi.byhfdd.bh3rd.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import project.studio.manametalmod.MMM;
import project.studio.manametalmod.config.M3Config;
import project.studio.manametalmod.entity.nbt.ManaMetalModRoot;
import project.studio.manametalmod.manor.ManorPermission;
import project.studio.manametalmod.manor.ManorPermissionType;
import project.studio.manametalmod.potion.PotionEffectM3;
import project.studio.manametalmod.potion.PotionM3;

import java.lang.ref.WeakReference;

/** Read the same prerequisites as ManaMetal's EventFx.onPlayerAttackEvent; never consume its cooldown. */
public final class MeleeAttackRules {

    private static volatile WeakReference<EntityPlayer> localPlayer = new WeakReference<>(null);

    private MeleeAttackRules() {}

    public static void setLocalPlayer(EntityPlayer player) {
        if (localPlayer.get() != player) localPlayer = new WeakReference<>(player);
    }

    public static boolean canSwing(EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer) || entity instanceof FakePlayer) return true;
        EntityPlayer player = (EntityPlayer) entity;
        // Remote players are driven by server-approved animation packets; their private career
        // cooldown is not necessarily synchronized to this client. Only predict our own input.
        if (player.worldObj.isRemote && player != localPlayer.get()) return true;
        if (!player.isEntityAlive()
            || PotionEffectM3.isPotion(player, PotionM3.potionPlayerDef)
            || !ManorPermission.playerHasManorPermission(player, ManorPermissionType.DAMAGE_ENTITY)) return false;
        if (!M3Config.UseAttackSpeed) return true;
        ManaMetalModRoot data = MMM.getEntityNBT(player);
        // An unavailable player state must not advertise an attack as ready.
        return data != null && data.carrer != null && cooldownReady(data.carrer.getAttackSpeed());
    }

    static boolean cooldownReady(double remaining) {
        return Double.isFinite(remaining) && remaining <= 0.0D;
    }
}
