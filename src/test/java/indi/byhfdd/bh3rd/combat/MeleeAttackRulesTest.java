package indi.byhfdd.bh3rd.combat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MeleeAttackRulesTest {

    @Test
    public void onlyACompletedCooldownPermitsANewSwing() {
        assertTrue(MeleeAttackRules.cooldownReady(0.0D));
        // The base mod subtracts .1 and can leave a small negative remainder.
        assertTrue(MeleeAttackRules.cooldownReady(-0.05D));
        assertFalse(MeleeAttackRules.cooldownReady(0.75D));
        assertFalse(MeleeAttackRules.cooldownReady(0.1D));
        assertFalse(MeleeAttackRules.cooldownReady(1.0e-12D));
    }

    @Test
    public void nonFinitePlayerStateDoesNotAdvertiseAnAttackAsReady() {
        assertFalse(MeleeAttackRules.cooldownReady(Double.NaN));
        assertFalse(MeleeAttackRules.cooldownReady(Double.POSITIVE_INFINITY));
        assertFalse(MeleeAttackRules.cooldownReady(Double.NEGATIVE_INFINITY));
    }
}
