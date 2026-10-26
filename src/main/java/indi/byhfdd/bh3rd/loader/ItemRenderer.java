package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.client.animation.FirstPersonMeleeTransform;
import indi.byhfdd.bh3rd.client.animation.MeleeAnimations;
import indi.byhfdd.bh3rd.client.animation.MeleePose;
import indi.byhfdd.bh3rd.client.animation.MeleeStyle;
import indi.byhfdd.bh3rd.client.animation.ThirdPersonMeleeTransform;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ItemRenderer implements IItemRenderer {

    public final String name;
    public final Minecraft mc = Minecraft.getMinecraft();
    public WavefrontObject modelobj;
    public ResourceLocation tex;
    private static final Map<String, WavefrontObject> modelCache = new HashMap<>();
    private static final Map<String, ResourceLocation> texCache = new HashMap<>();
    private final MeleeStyle meleeStyle;

    public ItemRenderer(String name) {
        this(name, null);
    }

    public ItemRenderer(String name, MeleeStyle meleeStyle) {
        this.name = name;
        this.meleeStyle = meleeStyle;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (!handleRenderType(item, type) || !loadModel()) return;
        EntityLivingBase owner = data.length > 1 && data[1] instanceof EntityLivingBase ? (EntityLivingBase) data[1]
            : null;
        MeleePose pose = MeleePose.ZERO;
        boolean animate = meleeStyle != null && owner != null && !MeleeAnimations.isUsingItem(owner);
        if (meleeStyle != null && owner != null) {
            pose = MeleeAnimations.INSTANCE.sample(owner, item, meleeStyle);
        }

        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_CURRENT_BIT | GL11.GL_LIGHTING_BIT | GL11.GL_TEXTURE_BIT);
        GL11.glPushMatrix();
        try {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            if (type == ItemRenderType.EQUIPPED_FIRST_PERSON && animate && owner == mc.thePlayer) {
                FirstPersonMeleeTransform.apply(
                    owner.getSwingProgress(MeleeAnimations.INSTANCE.getPartialTicks()),
                    item.getItem()
                        .shouldRotateAroundWhenRendering(),
                    pose);
            }
            applyRestingPose(type);
            if (type == ItemRenderType.EQUIPPED && animate) {
                ThirdPersonMeleeTransform.apply(pose);
            }
            this.mc.renderEngine.bindTexture(this.tex);
            this.modelobj.renderAll();
        } finally {
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }

    private static void applyRestingPose(ItemRenderType type) {
        if (type == ItemRenderType.EQUIPPED) {
            GL11.glTranslatef(0.83F, 0.15F, -0.05F);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
        } else {
            GL11.glTranslatef(0.9F, 0.2F, 0.0F);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            GL11.glRotatef(30.0F, 0.0F, 0.0F, 1.0F);
        }
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
    }

    private boolean loadModel() {
        if (modelobj != null) return true;
        try {
            if (!modelCache.containsKey(name)) {
                modelCache.put(
                    name,
                    (WavefrontObject) AdvancedModelLoader
                        .loadModel(new ResourceLocation("bh3rd:model/" + name + ".obj")));
            }
            if (!texCache.containsKey(name)) {
                texCache.put(name, new ResourceLocation("bh3rd:textures/weapon/" + name + ".png"));
            }
            this.modelobj = modelCache.get(name);
            this.tex = texCache.get(name);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to load model or texture for: " + name);
            e.printStackTrace();
            return false;
        }
    }
}
