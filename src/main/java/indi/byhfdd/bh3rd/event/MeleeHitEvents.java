package indi.byhfdd.bh3rd.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import indi.byhfdd.bh3rd.network.CombatEffectsNetwork;
import indi.byhfdd.bh3rd.network.HitEffectMessage;
import indi.byhfdd.bh3rd.weapon.DaggerCore;
import indi.byhfdd.bh3rd.weapon.HammerCore;
import indi.byhfdd.bh3rd.weapon.KatanaCore;
import indi.byhfdd.bh3rd.weapon.SwordCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Observes combat without modifying damage, movement, equipment or persistent data. */
public final class MeleeHitEvents {

    private final Map<EntityPlayer, AttackContext> attacks = new IdentityHashMap<>();
    private final List<PendingHit> pendingHits = new ArrayList<>();

    public static boolean isSupportedWeapon(Item item) {
        return item instanceof SwordCore || item instanceof KatanaCore || item instanceof DaggerCore
            || item instanceof HammerCore;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public void onAttackEntity(AttackEntityEvent event) {
        EntityPlayer attacker = event.entityPlayer;
        if (attacker.worldObj.isRemote) return;
        attacks.remove(attacker);
        if (event.isCanceled() || !(attacker instanceof EntityPlayerMP) || attacker instanceof FakePlayer
            || !(event.target instanceof EntityLivingBase)) return;
        ItemStack held = attacker.getHeldItem();
        if (held == null || !isSupportedWeapon(held.getItem())) return;
        attacks.put(attacker, new AttackContext(event, held));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase target = event.entityLiving;
        DamageSource source = event.source;
        if (target.worldObj.isRemote || event.isCanceled() || !Float.isFinite(event.ammount)
            || event.ammount <= 0.0F || source.isProjectile()
            || !(source.getEntity() instanceof EntityPlayerMP)) return;
        EntityPlayer attacker = (EntityPlayer) source.getEntity();
        if (source.getSourceOfDamage() != attacker) return;
        AttackContext attack = attacks.get(attacker);
        if (attack == null || attack.event.isCanceled() || attack.event.target != target
            || attacker.worldObj != target.worldObj || attacker.getHeldItem() != attack.weapon) return;

        // Forge 1.7.10 fires LivingHurtEvent before armor, absorption and final health changes.
        // Verify the result at tick end instead of displaying impacts for canceled/fully blocked damage.
        pendingHits.add(new PendingHit(event, attack, target));
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Set<EntityLivingBase> displayed = Collections.newSetFromMap(new IdentityHashMap<EntityLivingBase, Boolean>());
        try {
            for (PendingHit hit : pendingHits) {
                if (displayed.contains(hit.target) || !hit.didDamage()) continue;
                CombatEffectsNetwork.sendHit(hit.message);
                displayed.add(hit.target);
            }
        } finally {
            pendingHits.clear();
            attacks.clear();
        }
    }

    private static float remainingHealth(EntityLivingBase target) {
        return target.getHealth() + Math.max(0.0F, target.getAbsorptionAmount());
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static final class AttackContext {

        private final AttackEntityEvent event;
        private final ItemStack weapon;

        private AttackContext(AttackEntityEvent event, ItemStack weapon) {
            this.event = event;
            this.weapon = weapon;
        }
    }

    private static final class PendingHit {

        private final LivingHurtEvent event;
        private final AttackContext attack;
        private final EntityLivingBase target;
        private final World world;
        private final float healthBefore;
        private final HitEffectMessage message;

        private PendingHit(LivingHurtEvent event, AttackContext attack, EntityLivingBase target) {
            this.event = event;
            this.attack = attack;
            this.target = target;
            world = target.worldObj;
            healthBefore = remainingHealth(target);
            EntityPlayer attacker = attack.event.entityPlayer;
            AxisAlignedBB bounds = target.boundingBox;
            double x = clamp(attacker.posX, bounds.minX, bounds.maxX);
            double y = clamp(
                attacker.posY + attacker.getEyeHeight() - 0.2D,
                bounds.minY + target.height * 0.2D,
                bounds.maxY - target.height * 0.2D);
            double z = clamp(attacker.posZ, bounds.minZ, bounds.maxZ);
            // Derive visual randomness without advancing the world's gameplay RNG.
            long visualHash = world.getTotalWorldTime() * 341873128712L + target.getEntityId() * 132897987541L
                + attacker.getEntityId();
            message = new HitEffectMessage(
                world.provider.dimensionId,
                target.getEntityId(),
                x,
                y,
                z,
                attacker.rotationYaw,
                (int) (visualHash ^ (visualHash >>> 32)));
        }

        private boolean didDamage() {
            return !event.isCanceled() && !attack.event.isCanceled() && Float.isFinite(event.ammount)
                && event.ammount > 0.0F && target.worldObj == world && Float.isFinite(healthBefore)
                && remainingHealth(target) < healthBefore;
        }
    }
}
