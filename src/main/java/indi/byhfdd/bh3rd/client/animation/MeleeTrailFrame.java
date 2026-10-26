package indi.byhfdd.bh3rd.client.animation;

/** A bounded, chronological sample of the current cut, independent of render frame rate. */
public final class MeleeTrailFrame {

    public static final MeleeTrailFrame EMPTY = new MeleeTrailFrame(new MeleePose[0], 0.0f, 0);

    public final MeleePose[] poses;
    public final float strength;
    public final int combo;

    MeleeTrailFrame(MeleePose[] poses, float strength, int combo) {
        this.poses = poses;
        this.strength = strength;
        this.combo = combo;
    }
}
