package indi.byhfdd.bh3rd.client.animation;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.weapon.DaggerCore;
import indi.byhfdd.bh3rd.weapon.HammerCore;
import indi.byhfdd.bh3rd.weapon.KatanaCore;
import indi.byhfdd.bh3rd.weapon.SwordCore;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/** Client-only poses driven by the entity's existing, synchronized arm swing. */
@SideOnly(Side.CLIENT)
public final class MeleeAnimations {

    public static final MeleeAnimations INSTANCE = new MeleeAnimations();

    private final Map<EntityLivingBase, SwingState> states = new WeakHashMap<>();
    private float partialTicks;

    private MeleeAnimations() {}

    public static MeleeStyle getStyle(Item item) {
        return item instanceof KatanaCore || item instanceof SwordCore || item instanceof DaggerCore
            || item instanceof HammerCore ? MeleeStyle.STANDARD : null;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public static boolean isUsingItem(EntityLivingBase entity) {
        return entity instanceof EntityPlayer && ((EntityPlayer) entity).getItemInUseCount() > 0;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            partialTicks = Math.max(0.0F, Math.min(1.0F, event.renderTickTime));
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Iterator<Map.Entry<EntityLivingBase, SwingState>> iterator = states.entrySet()
            .iterator();
        while (iterator.hasNext()) {
            Map.Entry<EntityLivingBase, SwingState> entry = iterator.next();
            EntityLivingBase entity = entry.getKey();
            SwingState state = entry.getValue();
            if (!isCurrent(entity, state.item, state.slot) || isUsingItem(entity)) {
                iterator.remove();
            } else {
                state.observe(entity, entity.ticksExisted);
            }
        }
    }

    public MeleePose sample(EntityLivingBase entity, ItemStack renderedStack, MeleeStyle style) {
        int slot = getSlot(entity);
        if (style == null || !isCurrent(entity, renderedStack.getItem(), slot) || isUsingItem(entity)) {
            states.remove(entity);
            return MeleePose.ZERO;
        }
        SwingState state = states.get(entity);
        if (state == null || state.item != renderedStack.getItem() || state.slot != slot) {
            state = new SwingState(renderedStack.getItem(), slot);
            states.put(entity, state);
        }
        double now = (double) entity.ticksExisted + partialTicks;
        state.observe(entity, now);
        return state.animation.sample(now);
    }

    private static boolean isCurrent(EntityLivingBase entity, Item item, int slot) {
        if (entity == null || entity.isDead || entity.worldObj != Minecraft.getMinecraft().theWorld) return false;
        ItemStack held = entity.getHeldItem();
        return held != null && held.getItem() == item && getSlot(entity) == slot;
    }

    private static int getSlot(EntityLivingBase entity) {
        return entity instanceof EntityPlayer ? ((EntityPlayer) entity).inventory.currentItem : -1;
    }

    private static int getSwingDuration(EntityLivingBase entity) {
        // Matches EntityLivingBase's private arm-swing duration; only the visual timeline reads it.
        if (entity.isPotionActive(Potion.digSpeed)) {
            return Math.max(1, 6 - (1 + entity.getActivePotionEffect(Potion.digSpeed).getAmplifier()));
        }
        if (entity.isPotionActive(Potion.digSlowdown)) {
            return 6 + (1 + entity.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2;
        }
        return 6;
    }

    private static final class SwingState {

        private final Item item;
        private final int slot;
        private final MeleeComboAnimation animation = new MeleeComboAnimation();

        private SwingState(Item item, int slot) {
            this.item = item;
            this.slot = slot;
        }

        private void observe(EntityLivingBase entity, double now) {
            animation.observe(
                entity.isSwingInProgress,
                entity.swingProgressInt,
                entity.ticksExisted,
                now,
                getSwingDuration(entity));
        }
    }
}
