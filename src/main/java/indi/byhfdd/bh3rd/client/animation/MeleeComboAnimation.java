package indi.byhfdd.bh3rd.client.animation;

/** Tick-based playback; sampling never mutates the pose or advances the combo. */
public final class MeleeComboAnimation {

    private static final float RECOVERY_TICKS = 4.0f;

    private final MeleeSwingSequence sequence = new MeleeSwingSequence();
    private MeleePose attackStart = MeleePose.ZERO;
    private MeleePose recoveryStart = MeleePose.ZERO;
    private double attackTime;
    private double recoveryTime;
    private int duration;
    private int combo;
    private int lastAge;
    private boolean hasObservation;
    private boolean hasAttack;
    private boolean wasSwinging;
    private boolean recovering;

    public void observe(boolean swinging, int swingTick, int age, double now, int swingDuration) {
        if (!Double.isFinite(now)) return;
        if (hasObservation && age < lastAge) reset();
        int nextDuration = Math.max(1, swingDuration);
        boolean started = sequence.update(swinging, swingTick, age);
        if (started) {
            // A newly visible entity can already be partway through an attack.
            double eventTime = swingTick > 0 ? (double) age - swingTick : now;
            MeleePose current = sample(eventTime);
            combo = sequence.getCombo();
            attackStart = current;
            attackTime = eventTime;
            duration = nextDuration;
            hasAttack = true;
            recovering = false;
        } else if (hasAttack && wasSwinging && !swinging && !recovering) {
            recoveryStart = sample(now);
            recoveryTime = now;
            recovering = true;
        } else if (hasAttack && swinging && !recovering && duration != nextDuration) {
            double progress = Math.max(0.0, Math.min(1.0, (now - attackTime) / duration));
            attackTime = now - progress * nextDuration;
            duration = nextDuration;
        }
        lastAge = age;
        hasObservation = true;
        wasSwinging = swinging;
    }

    public MeleePose sample(double now) {
        if (!hasAttack || !Double.isFinite(now)) return MeleePose.ZERO;
        if (recovering) {
            return MeleeAnimation.interpolate(
                recoveryStart,
                MeleePose.ZERO,
                (float) ((now - recoveryTime) / RECOVERY_TICKS));
        }
        return MeleeAnimation.sample(combo, (float) ((now - attackTime) / duration), attackStart);
    }

    public int getCombo() {
        return combo;
    }

    public MeleeTrailFrame sampleTrail(double now) {
        if (!hasAttack || recovering || !Double.isFinite(now)) return MeleeTrailFrame.EMPTY;
        float progress = (float) ((now - attackTime) / duration);
        float fadeEnd = MeleeAnimation.STRIKE_END + .22f;
        if (progress <= MeleeAnimation.READY_END || progress >= fadeEnd) return MeleeTrailFrame.EMPTY;
        float end = Math.min(progress, MeleeAnimation.STRIKE_END);
        float start = Math.max(MeleeAnimation.READY_END, end - .14f);
        float fadeIn = Math.min(1.0f, (end - MeleeAnimation.READY_END) / .04f);
        float fadeOut = progress <= MeleeAnimation.STRIKE_END ? 1.0f
            : (fadeEnd - progress) / (fadeEnd - MeleeAnimation.STRIKE_END);
        MeleePose[] poses = new MeleePose[12];
        for (int i = 0; i < poses.length; i++) {
            float time = start + (end - start) * i / (poses.length - 1);
            poses[i] = MeleeAnimation.sample(combo, time, attackStart);
        }
        return new MeleeTrailFrame(poses, fadeIn * fadeOut, combo);
    }

    public void reset() {
        sequence.reset();
        attackStart = MeleePose.ZERO;
        recoveryStart = MeleePose.ZERO;
        attackTime = 0.0;
        recoveryTime = 0.0;
        duration = 1;
        combo = 0;
        lastAge = 0;
        hasObservation = false;
        hasAttack = false;
        wasSwinging = false;
        recovering = false;
    }
}
