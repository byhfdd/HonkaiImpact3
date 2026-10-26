package indi.byhfdd.bh3rd.client.animation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MeleeComboAnimationTest {

    @Test
    public void earlyRestartCapturesTheOldPoseAtTheSameInstant() {
        MeleeComboAnimation animation = started(6);
        animation.observe(true, 3, 3, 3, 6);
        MeleePose oldPose = animation.sample(3.25);
        animation.observe(true, -1, 3, 3.25, 6);
        assertEquals(1, animation.getCombo());
        assertPoseEquals(oldPose, animation.sample(3.25), 0);
        assertPoseEquals(oldPose, animation.sample(3.25001), .0001f);
        assertTrue(distance(MeleePose.ZERO, animation.sample(3.25)) > 1);
        animation.observe(true, 0, 3, 3.25, 6);
        assertEquals(1, animation.getCombo());
    }

    @Test
    public void restartBetweenFramesCapturesTheEventPoseRatherThanTheLastRenderedPose() {
        MeleeComboAnimation animation = started(6);
        animation.observe(true, 5, 5, 5, 6);
        MeleePose atEvent = animation.sample(6);
        // The next frame first sees counter 1: its restart happened at age 6, not at 7.5.
        animation.observe(true, 1, 7, 7.5, 6);
        assertEquals(1, animation.getCombo());
        assertPoseEquals(atEvent, animation.sample(6), 0);
        assertPoseEquals(MeleeAnimation.sample(1, .25f, atEvent), animation.sample(7.5), .00001f);
    }

    @Test
    public void naturalCompletionRecoversForFourTicksWithoutAZeroTeleport() {
        MeleeComboAnimation animation = started(6);
        MeleePose end = animation.sample(6);
        animation.observe(false, 0, 6, 6, 6);
        assertTrue(distance(MeleePose.ZERO, end) > 1);
        assertPoseEquals(end, animation.sample(6), 0);
        assertPoseEquals(end, animation.sample(6.0001), .0001f);
        assertPoseEquals(end.multiply(.5f), animation.sample(8), .00001f);
        animation.observe(false, 0, 8, 8, 6);
        assertPoseEquals(end.multiply(.5f), animation.sample(8), .00001f);
        assertPoseEquals(MeleePose.ZERO, animation.sample(10), 0);
        assertPoseEquals(MeleePose.ZERO, animation.sample(100), 0);
    }

    @Test
    public void aNewAttackDuringRecoveryStartsAtThePartiallyRecoveredPose() {
        MeleeComboAnimation animation = started(6);
        animation.observe(false, 0, 6, 6, 6);
        MeleePose recovering = animation.sample(8);
        animation.observe(true, -1, 8, 8, 6);
        assertEquals(1, animation.getCombo());
        assertTrue(distance(MeleePose.ZERO, recovering) > 1);
        assertPoseEquals(recovering, animation.sample(8), 0);
        assertPoseEquals(MeleeAnimation.sample(1, .09f, recovering), animation.sample(8.54), .0001f);
    }

    @Test
    public void firstObservationCanJoinAnAttackAlreadyInProgress() {
        MeleeComboAnimation animation = new MeleeComboAnimation();
        animation.observe(true, 3, 100, 100.25, 6);
        assertEquals(0, animation.getCombo());
        assertPoseEquals(
            MeleeAnimation.sample(0, 3.25f / 6, MeleePose.ZERO),
            animation.sample(100.25),
            .00001f);
        assertTrue(distance(MeleePose.ZERO, animation.sample(100.25)) > 1);
    }

    @Test
    public void denseAndSparseRenderingGiveTheSamePoseAtTheSameTime() {
        MeleeComboAnimation sparse = started(6);
        MeleeComboAnimation dense = started(6);
        for (int tick = 1; tick <= 10; tick++) {
            for (int frame = 0; frame < 12; frame++) dense.sample(tick - 1 + frame / 12.0);
            if (tick == 3) {
                sparse.observe(true, 3, 3, 3, 6);
                dense.observe(true, 3, 3, 3, 6);
                sparse.observe(true, -1, 3, 3.25, 6);
                dense.observe(true, -1, 3, 3.25, 6);
            }
            if (tick == 9) {
                sparse.observe(false, 0, 9, 9, 6);
                dense.observe(false, 0, 9, 9, 6);
            }
            assertPoseEquals(sparse.sample(tick + .37), dense.sample(tick + .37), 0);
        }
        MeleePose before = dense.sample(10);
        dense.sample(1000);
        dense.sample(0);
        assertPoseEquals(before, dense.sample(10), 0);
        assertEquals(sparse.getCombo(), dense.getCombo());
    }

    @Test
    public void nativeDurationsPreserveTheShapeAndRecoverWithoutJumping() {
        int[] durations = { 3, 4, 5, 6, 8 };
        for (int duration : durations) {
            MeleeComboAnimation animation = started(duration);
            for (int step = 0; step <= 20; step++) {
                float progress = step / 20f;
                assertPoseEquals(
                    MeleeAnimation.sample(0, progress, MeleePose.ZERO),
                    animation.sample(progress * (double) duration),
                    .0001f);
            }
            MeleePose end = animation.sample(duration);
            animation.observe(false, 0, duration, duration, duration);
            assertPoseEquals(end, animation.sample(duration), 0);
            assertPoseEquals(MeleePose.ZERO, animation.sample(duration + 4), 0);
        }
    }

    @Test
    public void theEarliestNativeRestartIsContinuousForEveryDuration() {
        int[] durations = { 3, 4, 5, 6, 8 };
        for (int duration : durations) {
            MeleeComboAnimation animation = started(duration);
            int restartTick = duration / 2;
            animation.observe(true, restartTick, restartTick, restartTick, duration);
            MeleePose before = animation.sample(restartTick + .1);
            assertPoseEquals(MeleeAnimation.sample(0, .5f, MeleePose.ZERO), before, .00001f);
            animation.observe(true, -1, restartTick, restartTick + .1, duration);
            assertEquals(1, animation.getCombo());
            assertPoseEquals(before, animation.sample(restartTick + .1), 0);
        }
    }

    @Test
    public void changingTheNativeDurationMidAttackPreservesTheCurrentPhase() {
        MeleeComboAnimation animation = started(6);
        MeleePose before = animation.sample(2.25);
        animation.observe(true, 2, 2, 2.25, 8);
        assertPoseEquals(before, animation.sample(2.25), .0001f);
        assertPoseEquals(MeleeAnimation.sample(0, .625f, MeleePose.ZERO), animation.sample(4.25), .0001f);
        before = animation.sample(4.25);
        animation.observe(true, 3, 4, 4.25, 3);
        assertPoseEquals(before, animation.sample(4.25), .0001f);
        assertPoseEquals(MeleeAnimation.sample(0, .875f, MeleePose.ZERO), animation.sample(5), .0001f);
        assertEquals(0, animation.getCombo());
    }

    @Test
    public void longLivedEntitiesRetainFractionalTickPrecision() {
        int base = 1 << 25;
        MeleeComboAnimation longLived = new MeleeComboAnimation();
        longLived.observe(true, 0, base, base, 6);
        MeleeComboAnimation fresh = started(6);
        for (int sample = 1; sample < 40; sample++) {
            double offset = sample / 10.0;
            assertPoseEquals(fresh.sample(offset), longLived.sample(base + offset), .0001f);
        }
        assertTrue(distance(longLived.sample(base + .1), longLived.sample(base + .2)) > .01f);
    }

    @Test
    public void resetAndClockRollbackClearThePreviousAttackAndCombo() {
        MeleeComboAnimation animation = started(6);
        animation.observe(true, 3, 3, 3, 6);
        animation.observe(true, -1, 4, 4, 6);
        assertEquals(1, animation.getCombo());
        animation.reset();
        assertEquals(0, animation.getCombo());
        assertPoseEquals(MeleePose.ZERO, animation.sample(4), 0);
        animation.observe(true, 0, 20, 20, 6);
        animation.observe(true, 3, 23, 23, 6);
        animation.observe(true, -1, 24, 24, 6);
        assertEquals(1, animation.getCombo());
        animation.observe(true, 0, 1, 1, 6);
        assertEquals(0, animation.getCombo());
        assertPoseEquals(MeleePose.ZERO, animation.sample(1), 0);
        animation.observe(false, 0, 0, 0, 6);
        assertPoseEquals(MeleePose.ZERO, animation.sample(100), 0);
    }

    @Test
    public void idleTimeoutStartsTheFirstStageAndInvalidTimesDoNotMutateState() {
        MeleeComboAnimation animation = started(6);
        animation.observe(false, 0, 6, 6, 6);
        animation.observe(true, -1, 20, 20, 6);
        assertEquals(0, animation.getCombo());
        MeleePose before = animation.sample(21);
        animation.observe(false, 0, 21, Double.NaN, 6);
        animation.observe(false, 0, 21, Double.POSITIVE_INFINITY, 6);
        assertPoseEquals(before, animation.sample(21), 0);
        assertPoseEquals(MeleePose.ZERO, animation.sample(Double.NaN), 0);
        assertPoseEquals(MeleePose.ZERO, animation.sample(Double.NEGATIVE_INFINITY), 0);
        MeleeComboAnimation invalidDuration = started(0);
        assertPoseEquals(MeleeAnimation.sample(0, .5f, MeleePose.ZERO), invalidDuration.sample(.5), 0);
    }

    private static MeleeComboAnimation started(int duration) {
        MeleeComboAnimation animation = new MeleeComboAnimation();
        animation.observe(true, 0, 0, 0, duration);
        return animation;
    }

    private static float distance(MeleePose first, MeleePose second) {
        float[] a = components(first);
        float[] b = components(second);
        float result = 0;
        for (int index = 0; index < a.length; index++) result += Math.abs(a[index] - b[index]);
        return result;
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
