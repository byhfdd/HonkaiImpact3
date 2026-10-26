package indi.byhfdd.bh3rd.client.animation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** Expresses the shared camera-axis pose around the third-person model's grip. */
@SideOnly(Side.CLIENT)
public final class ThirdPersonMeleeTransform {

    private ThirdPersonMeleeTransform() {}

    public static void apply(MeleePose pose) {
        apply(FirstPersonMeleeTransform.OPEN_GL, pose);
    }

    static void apply(FirstPersonMeleeTransform.TransformOps transform, MeleePose pose) {
        // B = Ry(95) Rz(5) Ry(90) is the resting first-person OBJ orientation.
        // B^-1 T R B keeps a yaw sweep from becoming a spin around the model's long +Y axis.
        transform.rotate(-90.0f, 0, 1, 0);
        transform.rotate(-5.0f, 0, 0, 1);
        transform.rotate(-95.0f, 0, 1, 0);
        transform.translate(pose.x * .06f, pose.y * .06f, pose.z * .06f);
        transform.rotate(pose.roll * .6f, 0, 0, 1);
        transform.rotate(pose.yaw * .6f, 0, 1, 0);
        transform.rotate(pose.pitch * .6f, 1, 0, 0);
        transform.rotate(95.0f, 0, 1, 0);
        transform.rotate(5.0f, 0, 0, 1);
        transform.rotate(90.0f, 0, 1, 0);
    }
}
