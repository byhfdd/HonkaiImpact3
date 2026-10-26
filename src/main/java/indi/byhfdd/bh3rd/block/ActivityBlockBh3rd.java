package indi.byhfdd.bh3rd.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ActivityBlockBh3rd extends Block {

    // 基础纹理：逻辑方向（与世界无关）
    private IIcon topIcon;
    private IIcon bottomIcon;
    private IIcon[] frontIcons = new IIcon[4]; // 逻辑前面4种状态
    private IIcon backIcon; // 逻辑后面
    private IIcon leftIcon; // 逻辑左面
    private IIcon rightIcon; // 逻辑右面
    String name;

    public ActivityBlockBh3rd(String name) {
        super(Material.rock);
        this.name = name;
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
        this.setBlockName(name);
    }

    // ========== 放置：设置 facing（0~3）==========
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int yaw = MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int facing;
        switch (yaw) {
            case 0:
                facing = 0;
                break; // NORTH
            case 1:
                facing = 3;
                break; // EAST
            case 2:
                facing = 1;
                break; // SOUTH
            case 3:
                facing = 2;
                break; // WEST
            default:
                facing = 0;
        }
        world.setBlockMetadataWithNotify(x, y, z, facing << 2, 2); // 初始 state=0
    }

    // ========== 右键：切换前面状态 ==========
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (world.isRemote) {
            // ========== 客户端：播放预测的下一个音效 ==========
            int currentMeta = world.getBlockMetadata(x, y, z);
            int currentState = currentMeta & 3;
            int nextState = (currentState + 1) % 4;

            // 使用固定声音事件名 "bh3rd:name" 实现中断效果
            // 实际音频由 sounds.json 中的随机池决定？不！我们用自定义包更可靠
            // 但 1.7.10 不支持动态选音频 → 所以我们用 4 个独立声音名，并接受轻微重叠
            String soundName = "bh3rd:cube." + name + "." + nextState;

            world.playSound(
                (double) x + 0.5D,
                (double) y + 0.5D,
                (double) z + 0.5D,
                soundName,
                1.0F, // volume
                1.0F, // pitch
                false // no distance delay
            );
            return true;
        } else {
            // ========== 服务端：更新状态 ==========
            int meta = world.getBlockMetadata(x, y, z);
            int facing = meta & 0xC; // 高2位
            int state = meta & 3; // 低2位
            state = (state + 1) % 4;
            world.setBlockMetadataWithNotify(x, y, z, facing | state, 3);
            return true;
        }
    }

    // ========== 核心：根据朝向返回世界坐标的正确纹理 ==========
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int worldSide, int meta) {
        int facing = (meta >> 2) & 3;
        int state = meta & 3;

        switch (facing) {
            case 0:
                return getForNorth(worldSide, state);
            case 1:
                return getForSouth(worldSide, state);
            case 2:
                return getForWest(worldSide, state);
            case 3:
                return getForEast(worldSide, state);
            default:
                return getForNorth(worldSide, state);
        }
    }

    // 面朝 NORTH 时的世界面映射
    private IIcon getForNorth(int side, int state) {
        switch (side) {
            case 0:
                return bottomIcon;
            case 1:
                return topIcon;
            case 2:
                return frontIcons[state];
            case 3:
                return backIcon;
            case 4:
                return leftIcon;
            case 5:
                return rightIcon;
            default:
                return topIcon;
        }
    }

    // 面朝 SOUTH 时：前后左右互换
    private IIcon getForSouth(int side, int state) {
        switch (side) {
            case 0:
                return bottomIcon;
            case 1:
                return topIcon;
            case 2:
                return backIcon;
            case 3:
                return frontIcons[state];
            case 4:
                return rightIcon;
            case 5:
                return leftIcon;
            default:
                return topIcon;
        }
    }

    // 面朝 WEST 时
    private IIcon getForWest(int side, int state) {
        switch (side) {
            case 0:
                return bottomIcon;
            case 1:
                return topIcon;
            case 2:
                return rightIcon;
            case 3:
                return leftIcon;
            case 4:
                return frontIcons[state];
            case 5:
                return backIcon;
            default:
                return topIcon;
        }
    }

    // 面朝 EAST 时
    private IIcon getForEast(int side, int state) {
        switch (side) {
            case 0:
                return bottomIcon;
            case 1:
                return topIcon;
            case 2:
                return leftIcon;
            case 3:
                return rightIcon;
            case 4:
                return backIcon;
            case 5:
                return frontIcons[state];
            default:
                return topIcon;
        }
    }

    // ========== 注册6个逻辑面 + 4个前面状态 ==========
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {

        this.topIcon = reg.registerIcon("bh3rd:cube/" + name + "_top");
        this.bottomIcon = reg.registerIcon("bh3rd:cube/" + name + "_bottom");
        this.backIcon = reg.registerIcon("bh3rd:cube/" + name + "_back");
        this.leftIcon = reg.registerIcon("bh3rd:cube/" + name + "_left");
        this.rightIcon = reg.registerIcon("bh3rd:cube/" + name + "_right");

        this.frontIcons[0] = reg.registerIcon("bh3rd:cube/" + name + "_front_0");
        this.frontIcons[1] = reg.registerIcon("bh3rd:cube/" + name + "_front_1");
        this.frontIcons[2] = reg.registerIcon("bh3rd:cube/" + name + "_front_2");
        this.frontIcons[3] = reg.registerIcon("bh3rd:cube/" + name + "_front_3");
    }
}
