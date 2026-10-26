package indi.byhfdd.bh3rd.client.effect;

import indi.byhfdd.bh3rd.client.animation.MeleePose;

/** Pure model-space blade segments, expressed relative to the unanimated resting OBJ matrix. */
public final class TrailGeometry {

    public static final int MAX_SAMPLES = 12;
    private static final double COS_FIVE = Math.cos(Math.toRadians(5));
    private static final double SIN_FIVE = Math.sin(Math.toRadians(5));

    private TrailGeometry() {}

    public static Mesh build(MeleePose[] poses, float bladeLength, boolean firstPerson, boolean rotateAround) {
        if (poses == null || poses.length < 2 || !Float.isFinite(bladeLength) || bladeLength <= 0) {
            return Mesh.EMPTY;
        }
        int start = Math.max(0, poses.length - MAX_SAMPLES);
        int count = poses.length - start;
        float[] segments = new float[count * 6];
        boolean moving = false;
        double threshold = bladeLength * bladeLength * 0.00000025D;
        for (int index = 0; index < count; index++) {
            MeleePose pose = poses[start + index];
            if (!finite(pose)) return Mesh.EMPTY;
            int offset = index * 6;
            transformPoint(pose, 0, 0, 0, firstPerson, rotateAround, segments, offset);
            transformPoint(pose, 0, bladeLength, 0, firstPerson, rotateAround, segments, offset + 3);
            if (index > 0) {
                moving |= distanceSquared(segments, offset, offset - 6) > threshold
                    || distanceSquared(segments, offset + 3, offset - 3) > threshold;
            }
        }
        return moving ? new Mesh(segments, count, bladeLength) : Mesh.EMPTY;
    }

    private static boolean finite(MeleePose pose) {
        return pose != null && Float.isFinite(pose.x) && Float.isFinite(pose.y) && Float.isFinite(pose.z)
            && Float.isFinite(pose.pitch) && Float.isFinite(pose.yaw) && Float.isFinite(pose.roll);
    }

    private static double distanceSquared(float[] points, int a, int b) {
        double x = points[a] - points[b];
        double y = points[a + 1] - points[b + 1];
        double z = points[a + 2] - points[b + 2];
        return x * x + y * y + z * z;
    }

    static void transformPoint(MeleePose pose, float x, float y, float z, boolean firstPerson,
        boolean rotateAround, float[] target, int offset) {
        double orientation = firstPerson && rotateAround ? 275 : 95;
        double c = Math.cos(Math.toRadians(orientation));
        double s = Math.sin(Math.toRadians(orientation));
        // B = Ry(95 or 275) Rz(5) Ry(90).
        double bx = c * (COS_FIVE * z - SIN_FIVE * y) - s * x;
        double by = SIN_FIVE * z + COS_FIVE * y;
        double bz = -s * (COS_FIVE * z - SIN_FIVE * y) - c * x;
        double amount = firstPerson ? 1 : .6;
        double pitch = Math.toRadians(pose.pitch * amount);
        double yaw = Math.toRadians(pose.yaw * amount);
        double roll = Math.toRadians(pose.roll * amount);
        double py = Math.cos(pitch) * by - Math.sin(pitch) * bz;
        double pz = Math.sin(pitch) * by + Math.cos(pitch) * bz;
        double yx = Math.cos(yaw) * bx + Math.sin(yaw) * pz;
        double yz = -Math.sin(yaw) * bx + Math.cos(yaw) * pz;
        double translation = firstPerson ? 1.0 / 1.2 : .06;
        double rx = Math.cos(roll) * yx - Math.sin(roll) * py + pose.x * translation;
        double ry = Math.sin(roll) * yx + Math.cos(roll) * py + pose.y * translation;
        double rz = yz + pose.z * translation;
        // B^-1 = Ry(-90) Rz(-5) Ry(-orientation).
        double ix = c * rx - s * rz;
        target[offset] = (float) (-s * rx - c * rz);
        target[offset + 1] = (float) (-SIN_FIVE * ix + COS_FIVE * ry);
        target[offset + 2] = (float) (COS_FIVE * ix + SIN_FIVE * ry);
    }

    static void cameraVector(float x, float y, float z, boolean firstPerson, boolean rotateAround,
        float[] target, int offset) {
        double angle = Math.toRadians(firstPerson && rotateAround ? 275 : 95);
        double c = Math.cos(angle), s = Math.sin(angle);
        double ix = c * x - s * z;
        target[offset] = (float) (-s * x - c * z);
        target[offset + 1] = (float) (-SIN_FIVE * ix + COS_FIVE * y);
        target[offset + 2] = (float) (COS_FIVE * ix + SIN_FIVE * y);
    }

    public static final class Mesh {

        private static final Mesh EMPTY = new Mesh(new float[0], 0, 0);
        final float[] segments;
        final int count;
        final float bladeLength;

        private Mesh(float[] segments, int count, float bladeLength) {
            this.segments = segments;
            this.count = count;
            this.bladeLength = bladeLength;
        }

        public int getSampleCount() {
            return count;
        }

        public boolean isEmpty() {
            return count == 0;
        }

        float coordinate(int sample, float fraction, int axis) {
            int offset = sample * 6 + axis;
            return segments[offset] + (segments[offset + 3] - segments[offset]) * fraction;
        }
    }
}
