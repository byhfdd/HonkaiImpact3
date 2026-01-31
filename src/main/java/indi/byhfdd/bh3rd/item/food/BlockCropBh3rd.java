package indi.byhfdd.bh3rd.item.food;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import project.studio.manametalmod.produce.farming.BlockCropSpecial;
import project.studio.manametalmod.produce.farming.CropDifficult;

public class BlockCropBh3rd extends BlockCropSpecial {
    private final String cropName;
    @SideOnly(Side.CLIENT)
    private IIcon[] icons; // 存储8个阶段的图标

    public BlockCropBh3rd(CropDifficult type, String name, int LV) {
        super(type, name, LV);
        this.cropName = name;
        // 关键：不要让父类设置 blockIcon
        this.blockIcon = null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.icons = new IIcon[2];
        this.icons[0] = reg.registerIcon("bh3rd:crop/" + "Aeasea" + "_0"); // 未成熟
        this.icons[1] = reg.registerIcon("bh3rd:crop/" + this.cropName + "_1"); // 未成熟
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0) meta = 0;
        if (meta > 7) meta = 7;

        // ✅ 关键：meta 0~6 → 未成熟（index=0），meta 7 → 成熟（index=1）
        int index = (meta >= 7) ? 1 : 0;
        return this.icons[index];
    }
}
