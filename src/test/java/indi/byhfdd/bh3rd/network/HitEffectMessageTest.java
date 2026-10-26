package indi.byhfdd.bh3rd.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class HitEffectMessageTest {

    @Test
    public void preservesEveryFieldAcrossThePacketCodec() {
        HitEffectMessage original = new HitEffectMessage(
            -7,
            42,
            12.25D,
            81.75D,
            -300.5D,
            -125.25F,
            Integer.MIN_VALUE);
        ByteBuf buffer = Unpooled.buffer();
        try {
            original.toBytes(buffer);
            HitEffectMessage decoded = new HitEffectMessage();
            decoded.fromBytes(buffer);

            assertEquals(original.dimensionId, decoded.dimensionId);
            assertEquals(original.targetEntityId, decoded.targetEntityId);
            assertEquals(original.hitX, decoded.hitX, 0.0D);
            assertEquals(original.hitY, decoded.hitY, 0.0D);
            assertEquals(original.hitZ, decoded.hitZ, 0.0D);
            assertEquals(original.attackerYaw, decoded.attackerYaw, 0.0F);
            assertEquals(original.seed, decoded.seed);
            assertEquals(0, buffer.readableBytes());
            assertTrue(decoded.isValid());
        } finally {
            buffer.release();
        }
    }

    @Test
    public void rejectsNonFiniteCoordinatesAndYaw() {
        for (double invalid : new double[] { Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY }) {
            assertFalse(new HitEffectMessage(0, 1, invalid, 0, 0, 0, 0).isValid());
            assertFalse(new HitEffectMessage(0, 1, 0, invalid, 0, 0, 0).isValid());
            assertFalse(new HitEffectMessage(0, 1, 0, 0, invalid, 0, 0).isValid());
        }
        for (float invalid : new float[] { Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY }) {
            assertFalse(new HitEffectMessage(0, 1, 0, 0, 0, invalid, 0).isValid());
        }
    }

    @Test
    public void rejectsNegativeEntityIdsWithoutRejectingNegativeDimensionsOrPositions() {
        assertFalse(new HitEffectMessage(0, -1, 0, 0, 0, 0, 0).isValid());
        assertTrue(new HitEffectMessage(-1, 0, -12, -8, -45, -180, -1).isValid());
    }
}
