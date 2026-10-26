package indi.byhfdd.bh3rd.client.animation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

/** Replaces vanilla's swing while retaining equipment lowering and the existing model alignment. */
@SideOnly(Side.CLIENT)
public final class FirstPersonMeleeTransform {

    private static final double GRIP_Z_ROTATION = Math.toRadians(335.0D);
    private static final double GRIP_Y_ROTATION = Math.toRadians(50.0D);
    private static final double VIEW_Y_ROTATION = Math.toRadians(45.0D);
    private static final double GRIP_X = 0.9F - 0.9375F;
    private static final double GRIP_Y = 0.2F - 0.0625F;
    private static final double ROTATED_X = GRIP_X * Math.cos(GRIP_Z_ROTATION) - GRIP_Y * Math.sin(GRIP_Z_ROTATION);
    private static final double ROTATED_Y = GRIP_X * Math.sin(GRIP_Z_ROTATION) + GRIP_Y * Math.cos(GRIP_Z_ROTATION);
    private static final double FORGE_X = 1.5D * ROTATED_X * Math.cos(GRIP_Y_ROTATION);
    private static final double FORGE_Z = -1.5D * ROTATED_X * Math.sin(GRIP_Y_ROTATION);
    private static final float PIVOT_X = (float) (0.4D
        * (FORGE_X * Math.cos(VIEW_Y_ROTATION) + FORGE_Z * Math.sin(VIEW_Y_ROTATION)));
    private static final float PIVOT_Y = (float) (0.4D * (1.5D * ROTATED_Y - 0.3D));
    private static final float PIVOT_Z = (float) (0.4D
        * (-FORGE_X * Math.sin(VIEW_Y_ROTATION) + FORGE_Z * Math.cos(VIEW_Y_ROTATION)));

    interface TransformOps {

        void translate(float x, float y, float z);

        void rotate(float angle, float x, float y, float z);

        void scale(float size);
    }

    static final TransformOps OPEN_GL = new TransformOps() {

        @Override
        public void translate(float x, float y, float z) {
            GL11.glTranslatef(x, y, z);
        }

        @Override
        public void rotate(float angle, float x, float y, float z) {
            GL11.glRotatef(angle, x, y, z);
        }

        @Override
        public void scale(float size) {
            GL11.glScalef(size, size, size);
        }
    };

    private FirstPersonMeleeTransform() {}

    public static void apply(float swingProgress, boolean rotateAround, MeleePose pose) {
        apply(OPEN_GL, swingProgress, rotateAround, pose);
    }

    static void apply(TransformOps transform, float swingProgress, boolean rotateAround, MeleePose pose) {
        float rootProgress = MathHelper.sqrt_float(swingProgress);
        float swing = MathHelper.sin(swingProgress * (float) Math.PI);
        float rootSwing = MathHelper.sin(rootProgress * (float) Math.PI);
        float squaredSwing = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);

        // Undo ForgeHooksClient's non-block equipped-item helper in reverse order.
        transform.translate(0.9375F, 0.0625F, 0.0F);
        transform.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
        transform.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
        transform.scale(1.0F / 1.5F);
        transform.translate(0.0F, 0.3F, 0.0F);
        if (rotateAround) {
            transform.rotate(-180.0F, 0.0F, 1.0F, 0.0F);
        }
        transform.scale(2.5F);
        transform.rotate(rootSwing * 80.0F, 1.0F, 0.0F, 0.0F);
        transform.rotate(rootSwing * 20.0F, 0.0F, 0.0F, 1.0F);
        transform.rotate(squaredSwing * 20.0F, 0.0F, 1.0F, 0.0F);
        transform.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
        // The vanilla swing and equipment translations commute; equipment lowering survives.
        transform
            .translate(rootSwing * 0.4F, -MathHelper.sin(rootProgress * (float) Math.PI * 2.0F) * 0.2F, swing * 0.2F);

        // The old model's local origin sits at its grip after the renderer's (.9, .2, 0) offset.
        float pivotX = rotateAround ? -PIVOT_X : PIVOT_X;
        float pivotZ = rotateAround ? -PIVOT_Z : PIVOT_Z;
        transform.translate(pose.x, pose.y, pose.z);
        transform.translate(pivotX, PIVOT_Y, pivotZ);
        transform.rotate(pose.roll, 0.0F, 0.0F, 1.0F);
        transform.rotate(pose.yaw, 0.0F, 1.0F, 0.0F);
        transform.rotate(pose.pitch, 1.0F, 0.0F, 0.0F);
        transform.translate(-pivotX, -PIVOT_Y, -pivotZ);

        // Restore the resting first-person and Forge transforms for the existing OBJ renderer.
        transform.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        transform.scale(0.4F);
        if (rotateAround) {
            transform.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }
        transform.translate(0.0F, -0.3F, 0.0F);
        transform.scale(1.5F);
        transform.rotate(50.0F, 0.0F, 1.0F, 0.0F);
        transform.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        transform.translate(-0.9375F, -0.0625F, 0.0F);
    }
}
