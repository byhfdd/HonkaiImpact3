package indi.byhfdd.bh3rd.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/** A visual-only receipt for a server-confirmed melee hit. */
public final class HitEffectMessage implements IMessage {

    public int dimensionId;
    public int targetEntityId;
    public double hitX;
    public double hitY;
    public double hitZ;
    public float attackerYaw;
    public int seed;

    public HitEffectMessage() {}

    public HitEffectMessage(int dimensionId, int targetEntityId, double hitX, double hitY, double hitZ,
        float attackerYaw, int seed) {
        this.dimensionId = dimensionId;
        this.targetEntityId = targetEntityId;
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
        this.attackerYaw = attackerYaw;
        this.seed = seed;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        dimensionId = buffer.readInt();
        targetEntityId = buffer.readInt();
        hitX = buffer.readDouble();
        hitY = buffer.readDouble();
        hitZ = buffer.readDouble();
        attackerYaw = buffer.readFloat();
        seed = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(dimensionId);
        buffer.writeInt(targetEntityId);
        buffer.writeDouble(hitX);
        buffer.writeDouble(hitY);
        buffer.writeDouble(hitZ);
        buffer.writeFloat(attackerYaw);
        buffer.writeInt(seed);
    }

    public boolean isValid() {
        return targetEntityId >= 0 && Double.isFinite(hitX) && Double.isFinite(hitY) && Double.isFinite(hitZ)
            && Float.isFinite(attackerYaw);
    }
}
