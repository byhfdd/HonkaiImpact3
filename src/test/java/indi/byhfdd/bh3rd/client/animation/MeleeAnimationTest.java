package indi.byhfdd.bh3rd.client.animation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MeleeAnimationTest {

    @Test
    public void anAttackStartsAtItsCapturedPoseAndFinishesInFollowThrough() {
        MeleePose start = new MeleePose(.1f, -.1f, .1f, 10, -10, 5);
        for (int combo = 0; combo < 3; combo++) {
            assertPoseEquals(start, MeleeAnimation.sample(combo, 0, start), 0);
            assertPoseEquals(start, MeleeAnimation.sample(combo, -1, start), 0);
            MeleePose end = MeleeAnimation.sample(combo, 1, start);
            assertTrue(distance(MeleePose.ZERO, end) > 1);
            assertPoseEquals(end, MeleeAnimation.sample(combo, 10, start), 0);
            assertPoseEquals(end, MeleeAnimation.sample(combo, .9999f, start), .0001f);
        }
    }

    @Test
    public void invalidInputCannotProduceNonFiniteTransforms() {
        float[] invalid = { Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY };
        for (float progress : invalid) {
            assertPoseEquals(MeleePose.ZERO, MeleeAnimation.sample(MeleeStyle.STANDARD, 0, progress), 0);
        }
        assertPoseEquals(MeleePose.ZERO, MeleeAnimation.sample(null, 0, .5f), 0);
        int[] combos = { Integer.MIN_VALUE, -1, 0, 1, 2, Integer.MAX_VALUE };
        float[] limits = { .4f, .45f, .5f, 100, 70, 40 };
        for (int combo : combos) {
            for (int frame = 0; frame <= 1000; frame++) {
                float[] pose = components(MeleeAnimation.sample(MeleeStyle.STANDARD, combo, frame / 1000f));
                for (int component = 0; component < pose.length; component++) {
                    assertTrue(Float.isFinite(pose[component]));
                    assertTrue(Math.abs(pose[component]) <= limits[component]);
                }
            }
        }
    }

    @Test
    public void stagesRepeatInThreeIncludingNegativeIndices() {
        for (int combo = -7; combo <= 7; combo++) {
            assertPoseEquals(
                MeleeAnimation.sample(MeleeStyle.STANDARD, combo, .5f),
                MeleeAnimation.sample(MeleeStyle.STANDARD, combo + 3, .5f),
                0);
        }
        for (int first = 0; first < 3; first++) {
            for (int second = first + 1; second < 3; second++) {
                assertTrue(
                    distance(
                        MeleeAnimation.sample(MeleeStyle.STANDARD, first, .5f),
                        MeleeAnimation.sample(MeleeStyle.STANDARD, second, .5f)) > 10);
            }
        }
    }

    @Test
    public void thrustMovesForwardAndTheTwoCutsHaveDifferentPlanes() {
        MeleePose thrustReady = MeleeAnimation.sample(MeleeStyle.STANDARD, 0, MeleeAnimation.READY_END);
        MeleePose thrustImpact = MeleeAnimation.sample(MeleeStyle.STANDARD, 0, .5f);
        assertTrue(thrustImpact.z < thrustReady.z - .5f);
        assertEquals(thrustReady.pitch, thrustImpact.pitch, 0);
        assertEquals(thrustReady.yaw, thrustImpact.yaw, 0);
        MeleePose horizontalReady = MeleeAnimation.sample(MeleeStyle.STANDARD, 1, MeleeAnimation.READY_END);
        MeleePose horizontalImpact = MeleeAnimation.sample(MeleeStyle.STANDARD, 1, .5f);
        MeleePose diagonalReady = MeleeAnimation.sample(MeleeStyle.STANDARD, 2, MeleeAnimation.READY_END);
        MeleePose diagonalImpact = MeleeAnimation.sample(MeleeStyle.STANDARD, 2, .5f);
        assertTrue(horizontalReady.yaw * horizontalImpact.yaw < 0);
        assertTrue(diagonalReady.yaw * diagonalImpact.yaw < 0);
        assertTrue(horizontalImpact.yaw * diagonalImpact.yaw < 0);
        assertTrue(Math.abs(diagonalImpact.roll - horizontalImpact.roll) > 20);
    }

    @Test
    public void contactPausesAndAllPhaseBoundariesAreContinuous() {
        float[] boundaries = { MeleeAnimation.READY_END, MeleeAnimation.STRIKE_END, MeleeAnimation.HOLD_END, 1 };
        for (int combo = 0; combo < 3; combo++) {
            MeleePose hold = MeleeAnimation.sample(MeleeStyle.STANDARD, combo, .4f);
            for (float progress = .4f; progress <= .6f; progress += .02f) {
                assertPoseEquals(hold, MeleeAnimation.sample(MeleeStyle.STANDARD, combo, progress), 0);
            }
            for (float boundary : boundaries) {
                MeleePose center = MeleeAnimation.sample(MeleeStyle.STANDARD, combo, boundary);
                assertPoseEquals(center, MeleeAnimation.sample(MeleeStyle.STANDARD, combo, boundary - .0001f), .001f);
                assertPoseEquals(center, MeleeAnimation.sample(MeleeStyle.STANDARD, combo, boundary + .0001f), .001f);
            }
            MeleePose previous = MeleePose.ZERO;
            for (int frame = 1; frame <= 1000; frame++) {
                MeleePose current = MeleeAnimation.sample(MeleeStyle.STANDARD, combo, frame / 1000f);
                assertTrue(distance(previous, current) < 3);
                previous = current;
            }
        }
    }

    @Test
    public void preparationBlendsFromAnArbitraryPreviousPose() {
        MeleePose start = new MeleePose(.2f, -.1f, -.2f, 25, -15, 10);
        for (int combo = 0; combo < 3; combo++) {
            MeleePose ready = MeleeAnimation.sample(combo, MeleeAnimation.READY_END, MeleePose.ZERO);
            MeleePose halfway = MeleeAnimation.sample(combo, MeleeAnimation.READY_END / 2, start);
            assertPoseEquals(MeleePose.interpolate(start, ready, .5f), halfway, .00001f);
            assertTrue(distance(start, MeleeAnimation.sample(combo, .0001f, start)) < .001f);
        }
    }

    @Test
    public void poseScalingAndInterpolationPreserveTheirInputs() {
        MeleePose original = new MeleePose(1, 2, 3, 4, 5, 6);
        assertPoseEquals(new MeleePose(.5f, 1, 1.5f, 2, 2.5f, 3), original.multiply(.5f), 0);
        assertPoseEquals(original.multiply(.25f), MeleePose.interpolate(MeleePose.ZERO, original, .25f), 0);
        assertPoseEquals(new MeleePose(1, 2, 3, 4, 5, 6), original, 0);
    }

    private static float distance(MeleePose first, MeleePose second) {
        float[] a = components(first);
        float[] b = components(second);
        float distance = 0;
        for (int index = 0; index < a.length; index++) distance += Math.abs(a[index] - b[index]);
        return distance;
    }

    private static void assertPoseEquals(MeleePose expected, MeleePose actual, float delta) {
        float[] a = components(expected);
        float[] b = components(actual);
        for (int index = 0; index < a.length; index++) assertEquals(a[index], b[index], delta);
    }

    private static float[] components(MeleePose pose) {
        return new float[] { pose.x, pose.y, pose.z, pose.pitch, pose.yaw, pose.roll };
    }
}
