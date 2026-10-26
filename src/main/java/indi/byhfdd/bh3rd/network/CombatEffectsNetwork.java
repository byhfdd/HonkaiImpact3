package indi.byhfdd.bh3rd.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/** No client classes are referenced here, including by the S2C network handler. */
public final class CombatEffectsNetwork {

    private static final int MAX_PENDING_HITS = 256;
    private static final double VIEW_DISTANCE = 48.0D;
    private static final Queue<HitEffectMessage> CLIENT_HITS = new ArrayBlockingQueue<>(MAX_PENDING_HITS);
    private static SimpleNetworkWrapper channel;

    private CombatEffectsNetwork() {}

    public static void init() {
        if (channel != null) return;
        channel = NetworkRegistry.INSTANCE.newSimpleChannel("bh3rd_fx");
        channel.registerMessage(HitHandler.class, HitEffectMessage.class, 0, Side.CLIENT);
    }

    public static void sendHit(HitEffectMessage message) {
        if (channel == null || !message.isValid()) return;
        channel.sendToAllAround(
            message,
            new NetworkRegistry.TargetPoint(
                message.dimensionId,
                message.hitX,
                message.hitY,
                message.hitZ,
                VIEW_DISTANCE));
    }

    /** Call from the client tick, and discard messages for any other dimension. */
    public static HitEffectMessage pollClientHit() {
        return CLIENT_HITS.poll();
    }

    /** Call when the client world changes or disconnects, so queued visuals cannot cross worlds. */
    public static void clearClientHits() {
        CLIENT_HITS.clear();
    }

    public static final class HitHandler implements IMessageHandler<HitEffectMessage, IMessage> {

        @Override
        public IMessage onMessage(HitEffectMessage message, MessageContext context) {
            if (context.side != Side.CLIENT || !message.isValid()) return null;
            CLIENT_HITS.offer(message);
            return null;
        }
    }
}
