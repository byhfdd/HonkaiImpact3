package indi.byhfdd.bh3rd.client.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.client.animation.MeleePose;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/** Untextured luminous blade sweep; never re-renders the weapon OBJ. */
@SideOnly(Side.CLIENT)
public final class MeleeTrailRenderer {

    private static final float[] GLOW_FRACTIONS = { .18f, .38f, .88f, 1.05f };
    private static final float[] GLOW_ALPHA = { 0, .28f, .55f, 0 };
    private static final float[] CORE_FRACTIONS = { .68f, .90f, 1.04f };
    private static final float[] CORE_ALPHA = { 0, .65f, 0 };

    private MeleeTrailRenderer() {}

    /**
     * Draw under the unanimated resting-model matrix. FP callers first cancel vanilla swing with a ZERO pose,
     * then apply their old resting OBJ transform; TP callers apply only the old resting OBJ transform.
     * Samples run oldest to newest, belong to one strike, and are capped at twelve. Strength must be zero
     * outside the active strike/fade window; no entity history or clocks are retained here.
     */
    public static void render(MeleePose[] poses, float strength, float bladeLength, boolean firstPerson,
        boolean rotateAround, int combo) {
        if (!Float.isFinite(strength) || strength <= 0) return;
        TrailGeometry.Mesh mesh = TrailGeometry.build(poses, bladeLength, firstPerson, rotateAround);
        if (mesh.isEmpty()) return;
        float opacity = Math.min(1, strength);
        float oldLightX = OpenGlHelper.lastBrightnessX;
        float oldLightY = OpenGlHelper.lastBrightnessY;
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_CURRENT_BIT | GL11.GL_COLOR_BUFFER_BIT
            | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_LIGHTING_BIT | GL11.GL_TEXTURE_BIT);
        GL11.glPushMatrix();
        try {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL14.glBlendEquation(GL14.GL_FUNC_ADD);
            // Keep the caller's depth test, but luminous geometry must not occlude later draws.
            GL11.glDepthMask(false);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
            ribbon(mesh, GLOW_FRACTIONS, GLOW_ALPHA, .28f, .65f, 1, opacity);
            ribbon(mesh, CORE_FRACTIONS, CORE_ALPHA, .80f, .96f, 1, opacity);
            if (Math.floorMod(combo, 3) == 0) {
                thrustSpark(mesh, firstPerson, rotateAround, opacity);
            }
        } finally {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, oldLightX, oldLightY);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }

    private static void ribbon(TrailGeometry.Mesh mesh, float[] fractions, float[] alphas,
        float red, float green, float blue, float strength) {
        for (int lane = 0; lane < fractions.length - 1; lane++) {
            GL11.glBegin(GL11.GL_QUAD_STRIP);
            try {
                for (int sample = 0; sample < mesh.count; sample++) {
                    float age = sample / (float) (mesh.count - 1);
                    float fade = age * age * strength;
                    for (int edge = lane; edge <= lane + 1; edge++) {
                        GL11.glColor4f(red, green, blue, alphas[edge] * fade);
                        GL11.glVertex3f(mesh.coordinate(sample, fractions[edge], 0),
                            mesh.coordinate(sample, fractions[edge], 1), mesh.coordinate(sample, fractions[edge], 2));
                    }
                }
            } finally {
                GL11.glEnd();
            }
        }
    }

    private static void thrustSpark(TrailGeometry.Mesh mesh, boolean firstPerson, boolean rotateAround, float strength) {
        int sample = mesh.count - 1;
        float[] center = new float[3];
        float[] axis = new float[3];
        float[] side = new float[3];
        for (int component = 0; component < 3; component++) {
            center[component] = mesh.coordinate(sample, 1.015f, component);
            axis[component] = (mesh.coordinate(sample, 1, component) - mesh.coordinate(sample, 0, component)) * .10f;
        }
        float width = Math.max(.008f, Math.min(.035f, mesh.bladeLength * .025f));
        // Two crossing, tapered diamonds remain visible even when the thrust faces the camera.
        for (int plane = 0; plane < 2; plane++) {
            TrailGeometry.cameraVector(plane == 0 ? width : 0, plane == 1 ? width : 0, 0,
                firstPerson, rotateAround, side, 0);
            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            try {
                GL11.glColor4f(.88f, .98f, 1, .65f * strength);
                vertex(center, axis, side, 0, 0);
                GL11.glColor4f(.38f, .75f, 1, 0);
                vertex(center, axis, side, 1, 0);
                vertex(center, axis, side, 0, 1);
                vertex(center, axis, side, -.7f, 0);
                vertex(center, axis, side, 0, -1);
                vertex(center, axis, side, 1, 0);
            } finally {
                GL11.glEnd();
            }
        }
    }

    private static void vertex(float[] center, float[] axis, float[] side, float along, float across) {
        GL11.glVertex3f(center[0] + axis[0] * along + side[0] * across,
            center[1] + axis[1] * along + side[1] * across, center[2] + axis[2] * along + side[2] * across);
    }
}
