package indi.byhfdd.bh3rd.client.effect;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import indi.byhfdd.bh3rd.client.animation.MeleePose;
import org.junit.Test;

public class TrailGeometryTest {

    @Test
    public void stationaryAndInvalidSamplesNeverProduceLightGeometry() {
        assertTrue(TrailGeometry.build(null, 1, true, false).isEmpty());
        assertTrue(TrailGeometry.build(new MeleePose[] { MeleePose.ZERO }, 1, true, false).isEmpty());
        assertTrue(TrailGeometry.build(new MeleePose[] { MeleePose.ZERO, MeleePose.ZERO }, 1, true, false).isEmpty());
        MeleePose invalid = new MeleePose(Float.NaN, 0, 0, 0, 0, 0);
        assertTrue(TrailGeometry.build(new MeleePose[] { MeleePose.ZERO, invalid }, 1, true, false).isEmpty());
        assertTrue(TrailGeometry.build(new MeleePose[] { MeleePose.ZERO, MeleePose.ZERO }, Float.NaN, true, false).isEmpty());
    }

    @Test
    public void keepsTheNewestTwelveSamplesInChronologicalOrder() {
        MeleePose[] samples = new MeleePose[30];
        for (int index = 0; index < samples.length; index++) samples[index] = new MeleePose(index * .01f, 0, 0, 0, 0, 0);
        TrailGeometry.Mesh mesh = TrailGeometry.build(samples, 1.3f, true, false);
        assertEquals(12, mesh.getSampleCount());
        float[] expected = new float[3];
        TrailGeometry.transformPoint(samples[18], 0, 0, 0, true, false, expected, 0);
        assertEquals(expected[0], mesh.coordinate(0, 0, 0), 0);
        TrailGeometry.transformPoint(samples[29], 0, 1.3f, 0, true, false, expected, 0);
        assertEquals(expected[2], mesh.coordinate(11, 1, 2), 0);
    }

    @Test
    public void staticModelSpaceTransformMatchesFirstPersonAndThirdPersonGlChains() {
        MeleePose pose = new MeleePose(-.35f, .65f, -.18f, -95, 47, 35);
        for (boolean firstPerson : new boolean[] { false, true }) {
            for (boolean rotate : new boolean[] { false, true }) {
                Matrix b = new Matrix();
                b.rotate(firstPerson && rotate ? 275 : 95, 1);
                b.rotate(5, 2);
                b.rotate(90, 1);
                Matrix expected = new Matrix();
                expected.rotate(-90, 1);
                expected.rotate(-5, 2);
                expected.rotate(firstPerson && rotate ? -275 : -95, 1);
                float translation = firstPerson ? 1 / 1.2f : .06f;
                float rotation = firstPerson ? 1 : .6f;
                expected.translate(pose.x * translation, pose.y * translation, pose.z * translation);
                expected.rotate(pose.roll * rotation, 2);
                expected.rotate(pose.yaw * rotation, 1);
                expected.rotate(pose.pitch * rotation, 0);
                expected.multiply(b.values);
                float[] actual = new float[3];
                TrailGeometry.transformPoint(pose, .07f, 1.3f, -.04f, firstPerson, rotate, actual, 0);
                assertArrayEquals(expected.point(.07f, 1.3f, -.04f), actual, .000001f);
            }
        }
    }

    @Test
    public void ribbonTracksBladeLengthAndTranslationOnlyThrustStillHasArea() {
        MeleePose[] samples = { new MeleePose(-.2f, .2f, .18f, -75, 15, 0),
            new MeleePose(-.2f, .2f, -.48f, -75, 15, 0) };
        TrailGeometry.Mesh mesh = TrailGeometry.build(samples, 1.2f, true, false);
        assertEquals(2, mesh.getSampleCount());
        double squaredLength = 0;
        double squaredMovement = 0;
        for (int axis = 0; axis < 3; axis++) {
            double delta = mesh.coordinate(1, 1, axis) - mesh.coordinate(1, 0, axis);
            squaredLength += delta * delta;
            double motion = mesh.coordinate(1, 1, axis) - mesh.coordinate(0, 1, axis);
            squaredMovement += motion * motion;
        }
        assertEquals(1.2, Math.sqrt(squaredLength), .000001);
        assertTrue(squaredMovement > .1);
    }

    @Test
    public void firstPersonRibbonPointsCoincideWithTheActualAnimatedObjChain() {
        MeleePose pose = new MeleePose(-.35f, .65f, -.18f, -95, -37, 35);
        for (boolean rotate : new boolean[] { false, true }) {
            // Original ItemRenderer, ForgeHooksClient and the mod's existing resting OBJ alignment.
            Matrix rest = new Matrix();
            rest.rotate(45, 1);
            rest.scale(.4f);
            if (rotate) rest.rotate(180, 1);
            rest.translate(0, -.3f, 0);
            rest.scale(1.5f);
            rest.rotate(50, 1);
            rest.rotate(335, 2);
            rest.translate(-.9375f, -.0625f, 0);
            rest.translate(.9f, .2f, 0);
            rest.scale(2);
            rest.rotate(30, 2);
            rest.rotate(90, 1);
            float[] grip = rest.point(0, 0, 0);
            Matrix animated = new Matrix();
            animated.translate(pose.x, pose.y, pose.z);
            animated.translate(grip[0], grip[1], grip[2]);
            animated.rotate(pose.roll, 2);
            animated.rotate(pose.yaw, 1);
            animated.rotate(pose.pitch, 0);
            animated.translate(-grip[0], -grip[1], -grip[2]);
            animated.multiply(rest.values);
            float[] trailPoint = new float[3];
            TrailGeometry.transformPoint(pose, .07f, 1.3f, -.04f, true, rotate, trailPoint, 0);
            assertArrayEquals(animated.point(.07f, 1.3f, -.04f),
                rest.point(trailPoint[0], trailPoint[1], trailPoint[2]), .000001f);
        }
    }

    private static final class Matrix {

        private double[] values = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };

        private void rotate(float degrees, int axis) {
            double radians = Math.toRadians(degrees), c = Math.cos(radians), s = Math.sin(radians);
            double[] rotation = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };
            int a = (axis + 1) % 3, b = (axis + 2) % 3;
            rotation[a * 4 + a] = c;
            rotation[b * 4 + b] = c;
            rotation[a * 4 + b] = -s;
            rotation[b * 4 + a] = s;
            multiply(rotation);
        }

        private void translate(float x, float y, float z) {
            multiply(new double[] { 1, 0, 0, x, 0, 1, 0, y, 0, 0, 1, z, 0, 0, 0, 1 });
        }

        private void scale(float size) {
            multiply(new double[] { size, 0, 0, 0, 0, size, 0, 0, 0, 0, size, 0, 0, 0, 0, 1 });
        }

        private void multiply(double[] right) {
            double[] product = new double[16];
            for (int row = 0; row < 4; row++) {
                for (int column = 0; column < 4; column++) {
                    for (int index = 0; index < 4; index++) product[row * 4 + column] += values[row * 4 + index] * right[index * 4 + column];
                }
            }
            values = product;
        }

        private float[] point(float x, float y, float z) {
            return new float[] { (float) (values[0] * x + values[1] * y + values[2] * z + values[3]),
                (float) (values[4] * x + values[5] * y + values[6] * z + values[7]),
                (float) (values[8] * x + values[9] * y + values[10] * z + values[11]) };
        }
    }
}
