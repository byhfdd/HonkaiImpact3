package indi.byhfdd.bh3rd.client.animation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MeleeTrailFrameTest {

    @Test
    public void idlePreparationAndRecoveryDoNotEmitTrails() {
        MeleeComboAnimation animation = new MeleeComboAnimation();
        assertEquals(0, animation.sampleTrail(0).poses.length);
        animation.observe(true, 0, 0, 0, 6);
        assertEquals(0, animation.sampleTrail(.5).poses.length);
        assertEquals(0, animation.sampleTrail(4).poses.length);
        animation.observe(false, 0, 6, 6, 6);
        assertEquals(0, animation.sampleTrail(6).poses.length);
        assertEquals(0, animation.sampleTrail(Double.NaN).poses.length);
    }

    @Test
    public void samplesFollowTheSameCutWithoutDependingOnFrameRate() {
        MeleeComboAnimation animation = new MeleeComboAnimation();
        animation.observe(true, 0, 0, 0, 6);
        MeleeTrailFrame first = animation.sampleTrail(1.5);
        assertEquals(12, first.poses.length);
        assertTrue(first.strength > 0 && first.strength <= 1);
        assertPoseEquals(animation.sample(1.5), first.poses[11]);
        for (int i = 0; i < 120; i++) animation.sampleTrail(i / 50.0);
        MeleeTrailFrame repeated = animation.sampleTrail(1.5);
        assertEquals(first.strength, repeated.strength, 0);
        for (int i = 0; i < first.poses.length; i++) assertPoseEquals(first.poses[i], repeated.poses[i]);
    }

    @Test
    public void impactTrailFadesAndANewSwingDoesNotReuseThePreviousCut() {
        MeleeComboAnimation animation = new MeleeComboAnimation();
        animation.observe(true, 0, 0, 0, 6);
        MeleeTrailFrame impact = animation.sampleTrail(2);
        MeleeTrailFrame fading = animation.sampleTrail(2.7);
        assertTrue(fading.strength < impact.strength);
        assertPoseEquals(impact.poses[11], fading.poses[11]);
        animation.observe(true, 3, 3, 3, 6);
        animation.observe(true, -1, 3, 3, 6);
        assertEquals(0, animation.sampleTrail(3).poses.length);
        MeleeTrailFrame second = animation.sampleTrail(4.5);
        assertEquals(1, second.combo);
        assertPoseEquals(animation.sample(4.5), second.poses[11]);
    }

    private static void assertPoseEquals(MeleePose expected, MeleePose actual) {
        assertEquals(expected.x, actual.x, .00001f);
        assertEquals(expected.y, actual.y, .00001f);
        assertEquals(expected.z, actual.z, .00001f);
        assertEquals(expected.pitch, actual.pitch, .00001f);
        assertEquals(expected.yaw, actual.yaw, .00001f);
        assertEquals(expected.roll, actual.roll, .00001f);
    }
}
