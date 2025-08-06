package indi.byhfdd.bh3rd.loader;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;

public class ItemRenderer  implements IItemRenderer {
    public final String name;
    public final Minecraft mc = Minecraft.getMinecraft();
    public WavefrontObject modelobj;
    public ResourceLocation tex;
    public ItemRenderer(String name) {
        this.name = name;
    }
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        // 只在手持时渲染自定义模型
        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {

            if (this.modelobj == null) {
                try {
                    this.modelobj = (WavefrontObject) AdvancedModelLoader.loadModel(
                        new ResourceLocation("bh3rd:model/" + this.name + ".obj")
                    );
                    this.tex = new ResourceLocation("bh3rd:textures/weapon/" + this.name + ".png");
                } catch (Exception e) {
                    System.err.println("Failed to load model or texture for: " + this.name);
                    e.printStackTrace();
                    return; // 加载失败则不渲染
                }
            }

            GL11.glDisable(2896); // 禁用光照
            GL11.glDisable(2884); // 禁用背面剔除

            float size = 2.0F;

            GL11.glPushMatrix();

            if (type == ItemRenderType.EQUIPPED) {
                GL11.glTranslatef(0.83F, 0.15F, -0.05F);
                GL11.glScalef(size, size, size);
                GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            } else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslatef(0.9F, 0.2F, -0.0F);
                GL11.glScalef(size, size, size);
                GL11.glRotatef(30.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            }

            this.mc.renderEngine.bindTexture(this.tex);
            this.modelobj.renderAll();

            GL11.glPopMatrix();

            GL11.glEnable(2896); // 启用光照
            GL11.glEnable(2884); // 启用背面剔除
        }

    }
}
