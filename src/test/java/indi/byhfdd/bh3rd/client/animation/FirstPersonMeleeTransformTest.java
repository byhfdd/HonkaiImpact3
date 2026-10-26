package indi.byhfdd.bh3rd.client.animation;

import static org.junit.Assert.assertArrayEquals;

import net.minecraft.util.MathHelper;

import org.junit.Test;

public class FirstPersonMeleeTransformTest {

    private static final MeleePose ZERO = new MeleePose(0, 0, 0, 0, 0, 0);

    @Test
    public void neutralPoseRemovesSwingAndPreservesEquipmentLowering() {
        for (boolean rotate : new boolean[] { false, true }) {
            for (float swing : new float[] { 0, 0.01F, 0.1F, 0.3F, 0.5F, 0.8F, 0.99F, 1 }) {
                for (float equipY : new float[] { -0.52F, -0.75F, -1.12F }) {
                    Matrix actual = camera();
                    vanilla(actual, swing, equipY, rotate);
                    FirstPersonMeleeTransform.apply(actual, swing, rotate, ZERO);
                    Matrix expected = camera();
                    vanilla(expected, 0, equipY, rotate);
                    assertArrayEquals(expected.values, actual.values, 0.000002D);
                }
            }
        }
    }

    @Test
    public void rotationsKeepTheOriginalModelGripAnchored() {
        for (boolean rotate : new boolean[] { false, true }) {
            Matrix actual = camera();
            vanilla(actual, 0.37F, -0.83F, rotate);
            FirstPersonMeleeTransform.apply(actual, 0.37F, rotate, new MeleePose(0, 0, 0, 67, -31, 44));
            Matrix expected = camera();
            vanilla(expected, 0, -0.83F, rotate);
            assertArrayEquals(expected.point(0.9F, 0.2F, 0), actual.point(0.9F, 0.2F, 0), 0.000002D);
        }
    }

    @Test
    public void translationUsesCameraAxesForBothItemOrientations() {
        for (boolean rotate : new boolean[] { false, true }) {
            Matrix actual = new Matrix();
            vanilla(actual, 0.61F, -0.52F, rotate);
            FirstPersonMeleeTransform.apply(actual, 0.61F, rotate, new MeleePose(0.23F, -0.14F, 0.07F, 0, 0, 0));
            Matrix expected = new Matrix();
            vanilla(expected, 0, -0.52F, rotate);
            double[] grip = expected.point(0.9F, 0.2F, 0);
            grip[0] += 0.23F;
            grip[1] -= 0.14F;
            grip[2] += 0.07F;
            assertArrayEquals(grip, actual.point(0.9F, 0.2F, 0), 0.000002D);
        }
    }

    private static Matrix camera() {
        Matrix result = new Matrix();
        result.translate(0.17F, -0.23F, 0.31F);
        result.rotate(13, 0, 0, 1);
        result.rotate(-9, 1, 0, 0);
        return result;
    }

    @Test
    public void thirdPersonRotationKeepsTheGripAndTheSharedSweepAxes() {
        for (int combo = 0; combo < 3; combo++) {
            for (float progress : new float[] { 0, .08f, .16f, .24f, .32f, .5f, 1 }) {
                MeleePose sampled = MeleeAnimation.sample(combo, progress, MeleePose.ZERO);
                MeleePose pose = new MeleePose(0, 0, 0, sampled.pitch, sampled.yaw, sampled.roll);
                Matrix grip = new Matrix();
                ThirdPersonMeleeTransform.apply(grip, pose);
                assertArrayEquals(new double[] { 0, 0, 0 }, grip.point(0, 0, 0), 0.000002D);

                Matrix actual = new Matrix();
                restingOrientation(actual);
                ThirdPersonMeleeTransform.apply(actual, pose);
                Matrix expected = new Matrix();
                expected.rotate(pose.roll * .6f, 0, 0, 1);
                expected.rotate(pose.yaw * .6f, 0, 1, 0);
                expected.rotate(pose.pitch * .6f, 1, 0, 0);
                restingOrientation(expected);
                assertArrayEquals(expected.values, actual.values, 0.000002D);
            }
        }
    }

    private static void restingOrientation(Matrix matrix) {
        matrix.rotate(45, 0, 1, 0);
        matrix.rotate(50, 0, 1, 0);
        matrix.rotate(335, 0, 0, 1);
        matrix.rotate(30, 0, 0, 1);
        matrix.rotate(90, 0, 1, 0);
    }

    @Test
    public void thirdPersonThrustTranslationDoesNotRotateWithTheSweep() {
        for (float yaw : new float[] { -65, 0, 65 }) {
            Matrix actual = new Matrix();
            restingOrientation(actual);
            ThirdPersonMeleeTransform.apply(actual, new MeleePose(0, 0, -.48f, -95, yaw, 35));
            assertArrayEquals(new double[] { 0, 0, -.48f * .06f }, actual.point(0, 0, 0), 0.000002D);
        }
    }

    /** Independently follows Minecraft ItemRenderer and ForgeHooksClient's forward GL calls. */
    private static void vanilla(Matrix matrix, float progress, float equipY, boolean rotateAround) {
        float root = MathHelper.sqrt_float(progress);
        matrix.translate(
            -MathHelper.sin(root * (float) Math.PI) * 0.4F,
            MathHelper.sin(root * (float) Math.PI * 2.0F) * 0.2F,
            -MathHelper.sin(progress * (float) Math.PI) * 0.2F);
        matrix.translate(0.7F * 0.8F, equipY, -0.9F * 0.8F);
        matrix.rotate(45, 0, 1, 0);
        matrix.rotate(-MathHelper.sin(progress * progress * (float) Math.PI) * 20, 0, 1, 0);
        matrix.rotate(-MathHelper.sin(root * (float) Math.PI) * 20, 0, 0, 1);
        matrix.rotate(-MathHelper.sin(root * (float) Math.PI) * 80, 1, 0, 0);
        matrix.scale(0.4F);
        if (rotateAround) matrix.rotate(180, 0, 1, 0);
        matrix.translate(0, -0.3F, 0);
        matrix.scale(1.5F);
        matrix.rotate(50, 0, 1, 0);
        matrix.rotate(335, 0, 0, 1);
        matrix.translate(-0.9375F, -0.0625F, 0);
    }

    private static final class Matrix implements FirstPersonMeleeTransform.TransformOps {

        private double[] values = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };

        @Override
        public void translate(float x, float y, float z) {
            multiply(new double[] { 1, 0, 0, x, 0, 1, 0, y, 0, 0, 1, z, 0, 0, 0, 1 });
        }

        @Override
        public void rotate(float angle, float x, float y, float z) {
            double radians = Math.toRadians(angle);
            double c = Math.cos(radians), s = Math.sin(radians), t = 1 - c;
            multiply(
                new double[] { t * x * x + c, t * x * y - s * z, t * x * z + s * y, 0, t * x * y + s * z, t * y * y + c,
                    t * y * z - s * x, 0, t * x * z - s * y, t * y * z + s * x, t * z * z + c, 0, 0, 0, 0, 1 });
        }

        @Override
        public void scale(float size) {
            multiply(new double[] { size, 0, 0, 0, 0, size, 0, 0, 0, 0, size, 0, 0, 0, 0, 1 });
        }

        private void multiply(double[] right) {
            double[] product = new double[16];
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    for (int k = 0; k < 4; k++) product[row * 4 + col] += values[row * 4 + k] * right[k * 4 + col];
                }
            }
            values = product;
        }

        private double[] point(float x, float y, float z) {
            return new double[] { values[0] * x + values[1] * y + values[2] * z + values[3],
                values[4] * x + values[5] * y + values[6] * z + values[7],
                values[8] * x + values[9] * y + values[10] * z + values[11] };
        }
    }
}
