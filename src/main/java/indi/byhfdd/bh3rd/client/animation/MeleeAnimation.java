package indi.byhfdd.bh3rd.client.animation;

/** One shared thrust, horizontal cut and diagonal cut, in first-person camera coordinates. */
public final class MeleeAnimation {

    private static final MeleePose[] READY = {
        pose(-.20f, .20f, .18f, -75, 15, 0),
        pose(-.35f, .40f, -.18f, -95, 65, 0),
        pose(-.35f, .65f, -.18f, -95, -65, 35) };

    private static final MeleePose[] STRIKE = {
        pose(-.20f, .20f, -.48f, -75, 15, 0),
        pose(-.35f, .40f, -.18f, -95, -65, 0),
        pose(-.35f, .40f, -.18f, -95, 65, 35) };

    private static final MeleePose[] FOLLOW_THROUGH = {
        pose(-.20f, .20f, -.35f, -75, 15, 0),
        pose(-.35f, .40f, -.10f, -95, -65, 0),
        pose(-.35f, .40f, -.10f, -95, 65, 35) };

    // Normal repeated clicks restart around .5, inside the impact hold rather than mid-cut.
    static final float READY_END = .16f;
    static final float STRIKE_END = .32f;
    static final float HOLD_END = .64f;

    private MeleeAnimation() {}

    public static MeleePose sample(MeleeStyle style, int combo, float progress) {
        return style == null ? MeleePose.ZERO : sample(combo, progress, MeleePose.ZERO);
    }

    public static MeleePose sample(int combo, float progress, MeleePose start) {
        if (Float.isNaN(progress) || Float.isInfinite(progress)) return MeleePose.ZERO;
        if (progress <= 0.0f) return start;
        int stage = Math.floorMod(combo, 3);
        if (progress < READY_END) {
            return interpolate(start, READY[stage], progress / READY_END);
        }
        if (progress < STRIKE_END) {
            return interpolate(READY[stage], STRIKE[stage], (progress - READY_END) / (STRIKE_END - READY_END));
        }
        if (progress < HOLD_END) return STRIKE[stage];
        if (progress < 1.0f) {
            return interpolate(STRIKE[stage], FOLLOW_THROUGH[stage], (progress - HOLD_END) / (1.0f - HOLD_END));
        }
        // Recovery is a separate timeline. Finishing a swing never teleports to the resting pose.
        return FOLLOW_THROUGH[stage];
    }

    static MeleePose interpolate(MeleePose start, MeleePose end, float progress) {
        float t = Math.max(0.0f, Math.min(1.0f, progress));
        // Quintic easing has zero velocity and acceleration at holds and at the end of recovery.
        float eased = Math.max(0.0f, Math.min(1.0f, t * t * t * (t * (t * 6.0f - 15.0f) + 10.0f)));
        return MeleePose.interpolate(start, end, eased);
    }

    private static MeleePose pose(float x, float y, float z, float pitch, float yaw, float roll) {
        return new MeleePose(x, y, z, pitch, yaw, roll);
    }
}
