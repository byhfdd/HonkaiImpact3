package indi.byhfdd.bh3rd.client.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.lang.ref.WeakReference;
import java.util.Random;

/** Short, client-only impact flashes. Does not change the target or create world entities. */
@SideOnly(Side.CLIENT)
public final class MeleeHitEffects {

    private static final int MAX_HITS_PER_TICK = 8;
    private static final int FULL_BRIGHT = 0xF000F0;
    private static final float SURFACE_OFFSET = 0.06F;
    private static WeakReference<World> budgetWorld = new WeakReference<>(null);
    private static long budgetTick = Long.MIN_VALUE;
    private static int hitsThisTick;

    private MeleeHitEffects() {}

    /** Called on the client main thread after receiving a confirmed damage notification. */
    public static void spawn(World world, int targetId, double x, double y, double z, float attackerYaw, int seed) {
        Minecraft mc = Minecraft.getMinecraft();
        if (world == null || !world.isRemote || world != mc.theWorld || mc.renderViewEntity == null
            || mc.effectRenderer == null || !Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)
            || !Float.isFinite(attackerYaw)) return;

        int quality = MathHelper.clamp_int(mc.gameSettings.particleSetting, 0, 2);
        double range = quality == 2 ? 14.0D : quality == 1 ? 20.0D : 24.0D;
        Entity viewer = mc.renderViewEntity;
        if (viewer.getDistanceSq(x, y, z) > range * range || !visible(world, viewer, x, y, z, 1.0F)) return;

        long tick = world.getTotalWorldTime();
        if (budgetWorld.get() != world || budgetTick != tick) {
            budgetWorld = new WeakReference<>(world);
            budgetTick = tick;
            hitsThisTick = 0;
        }
        if (hitsThisTick >= (MAX_HITS_PER_TICK >> quality)) return;
        hitsThisTick++;

        Entity target = world.getEntityByID(targetId);
        Random random = new Random(seed);
        mc.effectRenderer.addEffect(new HitSlashFX(world, target, x, y, z, attackerYaw, random, quality, range));

        int sparks = quality == 0 ? 8 : quality == 1 ? 4 : 0;
        double yaw = Math.toRadians(attackerYaw);
        double normalX = Math.sin(yaw);
        double normalZ = -Math.cos(yaw);
        for (int i = 0; i < sparks; i++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double spread = 0.035D + random.nextDouble() * 0.10D;
            double sx = x + normalX * SURFACE_OFFSET + (random.nextDouble() - 0.5D) * 0.15D;
            double sy = y + (random.nextDouble() - 0.5D) * 0.18D;
            double sz = z + normalZ * SURFACE_OFFSET + (random.nextDouble() - 0.5D) * 0.15D;
            mc.effectRenderer.addEffect(
                new HitSparkFX(
                    world,
                    sx,
                    sy,
                    sz,
                    Math.cos(angle) * spread + normalX * 0.035D,
                    0.035D + random.nextDouble() * 0.075D,
                    Math.sin(angle) * spread + normalZ * 0.035D,
                    4 + random.nextInt(3)));
        }
    }

    private static boolean visible(World world, Entity viewer, double x, double y, double z, float partialTicks) {
        double eyeX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
        double eyeY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks + viewer.getEyeHeight();
        double eyeZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;
        return world.rayTraceBlocks(Vec3.createVectorHelper(eyeX, eyeY, eyeZ), Vec3.createVectorHelper(x, y, z)) == null;
    }

    private static final class HitSlashFX extends EntityFX {

        private final Entity target;
        private final double offsetX;
        private final double offsetY;
        private final double offsetZ;
        private final double rangeSquared;
        private final float halfLength;
        private final float angle;
        private final boolean minimal;

        private HitSlashFX(World world, Entity target, double x, double y, double z, float yaw, Random random,
            int quality, double range) {
            super(world, x, y, z);
            this.target = target;
            offsetX = target == null ? 0.0D : x - target.posX;
            offsetY = target == null ? 0.0D : y - target.posY;
            offsetZ = target == null ? 0.0D : z - target.posZ;
            rangeSquared = range * range;
            halfLength = target == null ? 0.55F : MathHelper.clamp_float(target.height * 0.32F, 0.3F, 1.05F);
            angle = (float) Math.toRadians(35.0F + random.nextFloat() * 12.0F + MathHelper.wrapAngleTo180_float(yaw) * 0.025F);
            minimal = quality == 2;
            particleMaxAge = minimal ? 4 : 6;
            noClip = true;
        }

        @Override
        public void onUpdate() {
            prevPosX = posX;
            prevPosY = posY;
            prevPosZ = posZ;
            if (++particleAge >= particleMaxAge) {
                setDead();
                return;
            }
            if (target != null && target.worldObj == worldObj && !target.isDead
                && worldObj.getEntityByID(target.getEntityId()) == target) {
                setPosition(target.posX + offsetX, target.posY + offsetY, target.posZ + offsetZ);
            }
        }

        @Override
        public int getFXLayer() {
            // Layer 3 is not inside EffectRenderer's shared particle tessellation batch.
            return 3;
        }

        @Override
        public int getBrightnessForRender(float partialTicks) {
            return FULL_BRIGHT;
        }

        @Override
        public void renderParticle(Tessellator tessellator, float partialTicks, float rightX, float upY, float rightZ,
            float upX, float upZ) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.theWorld != worldObj || mc.renderViewEntity == null || isDead) return;
            double worldX = prevPosX + (posX - prevPosX) * partialTicks;
            double worldY = prevPosY + (posY - prevPosY) * partialTicks;
            double worldZ = prevPosZ + (posZ - prevPosZ) * partialTicks;
            double x = worldX - interpPosX;
            double y = worldY - interpPosY;
            double z = worldZ - interpPosZ;
            double distanceSquared = x * x + y * y + z * z;
            if (distanceSquared > rangeSquared || !visible(worldObj, mc.renderViewEntity, worldX, worldY, worldZ, partialTicks)) {
                return;
            }

            // The packet already contains a surface hit point; this tiny bias avoids z-fighting.
            // Check that unshifted point first so a hidden hit is never pulled through a wall.
            double toEyeY = mc.renderViewEntity.getEyeHeight() - y;
            double eyeDistance = Math.sqrt(x * x + toEyeY * toEyeY + z * z);
            if (eyeDistance > 0.01D) {
                double shift = Math.min(SURFACE_OFFSET, eyeDistance * 0.25D) / eyeDistance;
                x -= x * shift;
                y += toEyeY * shift;
                z -= z * shift;
            }
            float remaining = 1.0F - MathHelper.clamp_float((particleAge + partialTicks) / particleMaxAge, 0.0F, 1.0F);
            float alpha = remaining * remaining;
            float length = halfLength * (1.03F - remaining * 0.18F);

            GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT
                | GL11.GL_LIGHTING_BIT | GL11.GL_CURRENT_BIT | GL11.GL_TEXTURE_BIT);
            try {
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDepthMask(false);
                GL11.glEnable(GL11.GL_BLEND);
                GL14.glBlendEquation(GL14.GL_FUNC_ADD);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glShadeModel(GL11.GL_SMOOTH);

                tessellator.startDrawingQuads();
                tessellator.setBrightness(FULL_BRIGHT);
                if (!minimal) {
                    stroke(tessellator, x, y, z, rightX, upY, rightZ, upX, upZ, angle, length, length * 0.075F,
                        0.30F, 0.88F, 1.0F, alpha * 0.45F);
                    stroke(tessellator, x, y, z, rightX, upY, rightZ, upX, upZ, angle + 1.57F, length * 0.8F,
                        length * 0.075F, 0.30F, 0.88F, 1.0F, alpha * 0.38F);
                }
                stroke(tessellator, x, y, z, rightX, upY, rightZ, upX, upZ, angle, length, length * 0.018F,
                    0.84F, 0.98F, 1.0F, alpha);
                stroke(tessellator, x, y, z, rightX, upY, rightZ, upX, upZ, angle + 1.57F, length * 0.8F,
                    length * 0.018F, 0.84F, 0.98F, 1.0F, alpha * 0.9F);
                tessellator.draw();
            } finally {
                GL11.glPopAttrib();
            }
        }

        private static void stroke(Tessellator tessellator, double x, double y, double z, float rightX, float upY,
            float rightZ, float upX, float upZ, float angle, float length, float width, float red, float green,
            float blue, float alpha) {
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            for (int segment = 0; segment < 3; segment++) {
                float from = segment == 0 ? -1.0F : segment == 1 ? -0.18F : 0.18F;
                float to = segment == 0 ? -0.18F : segment == 1 ? 0.18F : 1.0F;
                float fromWidth = segment == 0 ? 0.0F : width;
                float toWidth = segment == 2 ? 0.0F : width;
                vertex(tessellator, x, y, z, rightX, upY, rightZ, upX, upZ, cos, sin, from * length, -fromWidth,
                    red, green, blue, segment == 0 ? 0.0F : alpha);
                vertex(tessellator, x, y, z, rightX, upY, rightZ, upX, upZ, cos, sin, to * length, -toWidth,
                    red, green, blue, segment == 2 ? 0.0F : alpha);
                vertex(tessellator, x, y, z, rightX, upY, rightZ, upX, upZ, cos, sin, to * length, toWidth,
                    red, green, blue, segment == 2 ? 0.0F : alpha);
                vertex(tessellator, x, y, z, rightX, upY, rightZ, upX, upZ, cos, sin, from * length, fromWidth,
                    red, green, blue, segment == 0 ? 0.0F : alpha);
            }
        }

        private static void vertex(Tessellator tessellator, double x, double y, double z, float rightX, float upY,
            float rightZ, float upX, float upZ, float cos, float sin, float along, float across, float red,
            float green, float blue, float alpha) {
            double screenX = cos * along - sin * across;
            double screenY = sin * along + cos * across;
            tessellator.setColorRGBA_F(red, green, blue, alpha);
            tessellator.addVertex(x + rightX * screenX + upX * screenY, y + upY * screenY,
                z + rightZ * screenX + upZ * screenY);
        }
    }

    private static final class HitSparkFX extends EntityCritFX {

        private HitSparkFX(World world, double x, double y, double z, double vx, double vy, double vz, int lifetime) {
            super(world, x, y, z, vx, vy, vz, 0.6F);
            particleAge = 0;
            particleMaxAge = lifetime;
            setRBGColorF(0.42F, 0.88F, 1.0F);
            nextTextureIndexX();
        }

        @Override
        public int getBrightnessForRender(float partialTicks) {
            return FULL_BRIGHT;
        }

        @Override
        public void renderParticle(Tessellator tessellator, float partialTicks, float rightX, float upY, float rightZ,
            float upX, float upZ) {
            float remaining = 1.0F - MathHelper.clamp_float((particleAge + partialTicks) / particleMaxAge, 0.0F, 1.0F);
            setAlphaF(remaining * remaining);
            super.renderParticle(tessellator, partialTicks, rightX, upY, rightZ, upX, upZ);
        }
    }
}
