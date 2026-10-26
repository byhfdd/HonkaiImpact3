package indi.byhfdd.bh3rd.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Constructor;

import net.minecraft.network.INetHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class CombatEffectsNetworkTest {

    private final CombatEffectsNetwork.HitHandler handler = new CombatEffectsNetwork.HitHandler();
    private MessageContext clientContext;
    private MessageContext serverContext;

    @Before
    public void setUp() throws ReflectiveOperationException {
        CombatEffectsNetwork.clearClientHits();
        clientContext = context(Side.CLIENT);
        serverContext = context(Side.SERVER);
    }

    @After
    public void clearQueue() {
        CombatEffectsNetwork.clearClientHits();
    }

    @Test
    public void handsAValidClientMessageToTheTickQueue() {
        HitEffectMessage message = message(1);
        assertNull(handler.onMessage(message, clientContext));
        assertSame(message, CombatEffectsNetwork.pollClientHit());
        assertNull(CombatEffectsNetwork.pollClientHit());
    }

    @Test
    public void discardsPacketsDeliveredToTheServer() {
        assertNull(handler.onMessage(message(1), serverContext));
        assertNull(CombatEffectsNetwork.pollClientHit());
    }

    @Test
    public void discardsMalformedVisualDataBeforeItReachesTheQueue() {
        handler.onMessage(new HitEffectMessage(0, 1, Double.NaN, 0, 0, 0, 0), clientContext);
        handler.onMessage(new HitEffectMessage(0, 1, 0, Double.POSITIVE_INFINITY, 0, 0, 0), clientContext);
        handler.onMessage(new HitEffectMessage(0, 1, 0, 0, Double.NEGATIVE_INFINITY, 0, 0), clientContext);
        handler.onMessage(new HitEffectMessage(0, 1, 0, 0, 0, Float.NaN, 0), clientContext);
        handler.onMessage(message(-1), clientContext);
        assertNull(CombatEffectsNetwork.pollClientHit());
    }

    @Test
    public void keepsTheFirst256HitsAndDropsOverflow() {
        for (int id = 0; id < 300; id++) {
            handler.onMessage(message(id), clientContext);
        }
        for (int id = 0; id < 256; id++) {
            assertEquals(id, CombatEffectsNetwork.pollClientHit().targetEntityId);
        }
        assertNull(CombatEffectsNetwork.pollClientHit());
    }

    @Test
    public void clearingAWorldDropsOldHitsAndAllowsNewOnes() {
        handler.onMessage(message(1), clientContext);
        CombatEffectsNetwork.clearClientHits();
        assertNull(CombatEffectsNetwork.pollClientHit());

        HitEffectMessage nextWorldHit = message(2);
        handler.onMessage(nextWorldHit, clientContext);
        assertSame(nextWorldHit, CombatEffectsNetwork.pollClientHit());
    }

    private static HitEffectMessage message(int targetEntityId) {
        return new HitEffectMessage(0, targetEntityId, 0, 64, 0, 90, 1234);
    }

    private static MessageContext context(Side side) throws ReflectiveOperationException {
        // Forge 1.7.10 exposes the receiving side publicly, but its context constructor is package-private.
        // The handler only reads that side; no live connection or Minecraft world is needed for these tests.
        Constructor<MessageContext> constructor = MessageContext.class
            .getDeclaredConstructor(INetHandler.class, Side.class);
        constructor.setAccessible(true);
        return constructor.newInstance(null, side);
    }
}
