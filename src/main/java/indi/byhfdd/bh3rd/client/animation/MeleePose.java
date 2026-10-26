package indi.byhfdd.bh3rd.client.animation;

/** Camera-space translation and rotation in degrees, relative to the weapon's resting pose. */
public final class MeleePose {

    public static final MeleePose ZERO = new MeleePose(0, 0, 0, 0, 0, 0);

    public final float x;
    public final float y;
    public final float z;
    public final float pitch;
    public final float yaw;
    public final float roll;

    public MeleePose(float x, float y, float z, float pitch, float yaw, float roll) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public MeleePose multiply(float factor) {
        return new MeleePose(x * factor, y * factor, z * factor, pitch * factor, yaw * factor, roll * factor);
    }

    public static MeleePose interpolate(MeleePose start, MeleePose end, float amount) {
        return new MeleePose(
            start.x + (end.x - start.x) * amount,
            start.y + (end.y - start.y) * amount,
            start.z + (end.z - start.z) * amount,
            start.pitch + (end.pitch - start.pitch) * amount,
            start.yaw + (end.yaw - start.yaw) * amount,
            start.roll + (end.roll - start.roll) * amount);
    }
}
