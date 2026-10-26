package indi.byhfdd.bh3rd.client.animation;

/** Tracks sampled vanilla swing ticks without advancing the combo on repeated render calls. */
public final class MeleeSwingSequence {

    private static final int COMBO_TIMEOUT = 16;

    private boolean hasUpdate;
    private boolean hasStarted;
    private boolean wasSwinging;
    private int lastSwingTick;
    private int lastAge;
    private int lastStartAge;
    private int combo;

    /** Returns true once per observed swing start; an expired combo restarts on the next swing. */
    public boolean update(boolean swinging, int swingTick, int age) {
        if (hasUpdate && age < lastAge) {
            reset();
        }
        boolean restarted = wasSwinging && swingTick < lastSwingTick;
        boolean started = swinging && (!wasSwinging || restarted) && (!hasStarted || age != lastStartAge);
        if (started) {
            if (!hasStarted || (long) age - lastStartAge > COMBO_TIMEOUT) {
                combo = 0;
            } else {
                combo = (combo + 1) % 3;
            }
            lastStartAge = age;
            hasStarted = true;
        }
        hasUpdate = true;
        wasSwinging = swinging;
        lastSwingTick = swingTick;
        lastAge = age;
        return started;
    }

    public int getCombo() {
        return combo;
    }

    public void reset() {
        hasUpdate = false;
        hasStarted = false;
        wasSwinging = false;
        lastSwingTick = 0;
        lastAge = 0;
        lastStartAge = 0;
        combo = 0;
    }
}
