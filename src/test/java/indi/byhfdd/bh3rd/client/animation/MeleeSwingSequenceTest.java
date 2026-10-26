package indi.byhfdd.bh3rd.client.animation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MeleeSwingSequenceTest {

    @Test
    public void idleSamplesAndRepeatedRendersDoNotStartAttacks() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        assertFalse(sequence.update(false, 0, 10));
        assertTrue(sequence.update(true, -1, 11));
        for (int render = 0; render < 10; render++) {
            assertFalse(sequence.update(true, -1, 11));
        }
        assertFalse(sequence.update(true, 0, 11));
        assertFalse(sequence.update(true, 0, 12));
        assertFalse(sequence.update(true, 1, 13));
        assertEquals(0, sequence.getCombo());
    }

    @Test
    public void activeSwingRestartsCycleThroughThreeStages() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        for (int attack = 0; attack < 7; attack++) {
            int age = attack * 4;
            assertTrue(sequence.update(true, -1, age));
            assertEquals(attack % 3, sequence.getCombo());
            assertFalse(sequence.update(true, 0, age));
            assertFalse(sequence.update(true, 3, age + 3));
        }
    }

    @Test
    public void lowFrameRateCanObserveRestartsWithoutAnIdleSample() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        assertTrue(sequence.update(true, 2, 100));
        assertEquals(0, sequence.getCombo());
        assertFalse(sequence.update(true, 5, 103));
        assertTrue(sequence.update(true, 1, 106));
        assertEquals(1, sequence.getCombo());
        assertFalse(sequence.update(true, 4, 109));
        assertTrue(sequence.update(true, 0, 111));
        assertEquals(2, sequence.getCombo());
    }

    @Test
    public void interruptionKeepsTheComboUntilTheStartIntervalExceedsSixteenTicks() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        assertTrue(sequence.update(true, 0, 0));
        assertFalse(sequence.update(false, 0, 2));
        assertTrue(sequence.update(true, 0, 9));
        assertEquals(1, sequence.getCombo());
        assertFalse(sequence.update(false, 0, 10));
        assertTrue(sequence.update(true, 0, 25));
        assertEquals(2, sequence.getCombo());
        assertFalse(sequence.update(false, 0, 26));
        assertTrue(sequence.update(true, 0, 42));
        assertEquals(0, sequence.getCombo());
    }

    @Test
    public void timeoutDoesNotChangeThePoseOfAnAttackAlreadyInProgress() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        assertTrue(sequence.update(true, 0, 0));
        assertFalse(sequence.update(true, 4, 4));
        assertTrue(sequence.update(true, 0, 5));
        assertEquals(1, sequence.getCombo());
        assertFalse(sequence.update(true, 4, 100));
        assertEquals(1, sequence.getCombo());
        assertFalse(sequence.update(false, 0, 101));
        assertTrue(sequence.update(true, 0, 102));
        assertEquals(0, sequence.getCombo());
    }

    @Test
    public void oneAgeCannotStartTheSameAttackTwice() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        assertTrue(sequence.update(true, -1, 10));
        assertFalse(sequence.update(true, 0, 10));
        assertFalse(sequence.update(true, -1, 10));
        assertFalse(sequence.update(false, 0, 10));
        assertFalse(sequence.update(true, -1, 10));
        assertEquals(0, sequence.getCombo());
        assertFalse(sequence.update(true, 0, 11));
        assertFalse(sequence.update(true, 4, 14));
        assertTrue(sequence.update(true, -1, 15));
        assertEquals(1, sequence.getCombo());
    }

    @Test
    public void aRewoundClockStartsANewSequence() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        assertTrue(sequence.update(true, 0, 50));
        assertFalse(sequence.update(true, 4, 54));
        assertTrue(sequence.update(true, 0, 55));
        assertEquals(1, sequence.getCombo());
        assertTrue(sequence.update(true, 1, 1));
        assertEquals(0, sequence.getCombo());
        assertFalse(sequence.update(false, 0, 0));
        assertTrue(sequence.update(true, 0, 1));
        assertEquals(0, sequence.getCombo());
    }

    @Test
    public void explicitResetForgetsTheActiveSwingAndCombo() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        assertTrue(sequence.update(true, 0, 5));
        assertFalse(sequence.update(true, 4, 9));
        assertTrue(sequence.update(true, 0, 10));
        assertEquals(1, sequence.getCombo());
        sequence.reset();
        assertEquals(0, sequence.getCombo());
        assertTrue(sequence.update(true, 3, 13));
        assertEquals(0, sequence.getCombo());
        assertFalse(sequence.update(true, 3, 13));
    }

    @Test
    public void largeClockIntervalsCannotOverflowTheTimeout() {
        MeleeSwingSequence sequence = new MeleeSwingSequence();
        assertTrue(sequence.update(true, 0, Integer.MIN_VALUE));
        assertFalse(sequence.update(false, 0, Integer.MIN_VALUE + 1));
        assertTrue(sequence.update(true, 0, Integer.MIN_VALUE + 8));
        assertEquals(1, sequence.getCombo());
        assertFalse(sequence.update(false, 0, Integer.MIN_VALUE + 9));
        assertTrue(sequence.update(true, 0, Integer.MAX_VALUE));
        assertEquals(0, sequence.getCombo());
        assertTrue(sequence.update(true, 1, Integer.MIN_VALUE));
        assertEquals(0, sequence.getCombo());
    }
}
