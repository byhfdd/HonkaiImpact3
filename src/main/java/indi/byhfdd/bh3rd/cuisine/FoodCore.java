package indi.byhfdd.bh3rd.cuisine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import project.studio.manametalmod.ManaMetalMod;
import project.studio.manametalmod.api.IFood;
import project.studio.manametalmod.core.NBTHelp;
import project.studio.manametalmod.produce.cuisine.FoodTaste;
import project.studio.manametalmod.produce.cuisine.ICuisine;
import project.studio.manametalmod.produce.cuisine.ItemCuisine;

import java.util.List;

public class FoodCore extends ItemFood implements IFood, ICuisine {

    int some;
    // 图标数组，用于存储不同子类型食物的图标
    IIcon[] icons;
    String nameLV;
    /**
     * 构造函数，初始化食物的基本属性
     */
    int hunger = 20;

    public FoodCore(int hunger, int some, String nameLV) {
        super(1, 1.0F, false);
        this.hunger = hunger;
        this.some = some;
        this.nameLV = nameLV;
        this.icons = new IIcon[some];
        this.setMaxStackSize(1); // 每份料理的剩余口数和收藏次数保存在独立 NBT 中
        this.setAlwaysEdible(); // 设置总是可以食用（即使饥饿值满也可以食用）
        this.setHasSubtypes(true); // 启用子类型（metadata）
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 设置创造模式物品栏分类
    }

    /**
     * 当食物被食用完时调用此方法
     *
     * @param item   食物物品栈
     * @param world  游戏世界对象
     * @param player 食用食物的玩家
     * @return 处理后的物品栈
     */
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
        // 旧存档可能仍有多份一叠，不能让本体按一份料理修改整叠的 NBT。
        if (rejectStackedCuisine(item, world, player)) {
            return item;
        }
        player.getFoodStats()
            .func_151686_a(this, item); // 增加玩家的饱食度
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F); // 播放打嗝声音
        this.onFoodEaten(item, world, player); // 调用父类的食物食用处理方法
        ItemCuisine.addItemCuisineEffect(item, world, player, false); // 添加料理效果
        return item;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (rejectStackedCuisine(item, world, player)) {
            return item;
        }
        return super.onItemRightClick(item, world, player);
    }

    private boolean rejectStackedCuisine(ItemStack item, World world, EntityPlayer player) {
        if (item.stackSize <= 1) {
            return false;
        }
        if (!world.isRemote) {
            player.addChatMessage(new ChatComponentTranslation("message.bh3rd.cuisine.splitBeforeEating"));
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        // 使用 nameLV 构造 key，并加上 metadata 支持（可选）
        String tipKey = "tips." + this.nameLV + "." + stack.getItemDamage();

        // 先尝试带 meta 的提示（如 tips.cooked_meat.1）
        if (StatCollector.canTranslate(tipKey)) {
            tooltip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal(tipKey));
        } else {
            // 回退到通用提示（如 tips.cooked_meat）
            String fallbackKey = "tips." + this.nameLV;
            if (StatCollector.canTranslate(fallbackKey)) {
                tooltip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal(fallbackKey));
            }
        }

        // 调用父类（通常为空，但保留好习惯）
        super.addInformation(stack, player, tooltip, advanced);
    }

    /**
     * 判断食物是否可以种植（作为植物种子使用）
     *
     * @param food 食物物品栈
     * @return 是否可以种植
     */
    @Override
    public boolean canRoot(ItemStack food) {
        if (food.hasTagCompound()) {
            NBTTagCompound tag = food.getTagCompound();
            if (tag.hasKey("FoodTaste", 3)) {
                int data = NBTHelp.getIntSafe("FoodTaste", tag, -1);
                if (data != -1) {
                    FoodTaste ft = FoodTaste.values()[data];
                    if (ft == FoodTaste.GoldDust) {
                        return false; // 如果是金粉口味，则不能种植
                    }
                }
            }
        }

        return true; // 默认可以种植
    }

    /**
     * 获取食物提供的饥饿值
     *
     * @param item 食物物品栈
     * @return 饥饿值（固定返回20）
     */
    public int func_150905_g(ItemStack item) {
        return hunger;
    }

    /**
     * 获取食物的饱和度
     *
     * @param item 食物物品栈
     * @return 饱和度值（固定返回15.0F）
     */
    public float func_150906_h(ItemStack item) {
        return 15.0F;
    }

    /**
     * 判断物品是否带有特殊效果发光
     *
     * @param par1ItemStack 物品栈
     * @param pass          渲染通道
     * @return 是否有特效
     */
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return super.hasEffect(par1ItemStack, pass);
    }

    /**
     * 根据元数据获取对应的图标
     *
     * @param meta 元数据
     * @return 对应的图标
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return icons[meta >= 0 && meta < icons.length ? meta : 0];
    }

    /**
     * 获取物品的元数据
     *
     * @param meta 输入的元数据
     * @return 返回元数据本身
     */
    public int getMetadata(int meta) {
        return meta;
    }

    /**
     * 获取物品的本地化名称键
     *
     * @param is 物品栈
     * @return 本地化键名
     */
    public String getUnlocalizedName(ItemStack is) {
        return "item." + nameLV + "." + is.getItemDamage();
    }

    /**
     * 注册物品的所有图标
     *
     * @param reg 图标注册器
     */
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        for (int i = 0; i < some; ++i) {
            icons[i] = reg.registerIcon("bh3rd:" + nameLV + "." + i); // 注册10个不同子类型的图标
        }

    }

    /**
     * 添加物品的子类型到创造模式物品栏
     *
     * @param itm 物品实例
     * @param tab 创造模式标签页
     * @param lst 子类型列表
     */
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itm, CreativeTabs tab, List lst) {
        for (int i = 0; i < some; ++i) {
            lst.add(new ItemStack(itm, 1, i)); // 添加10个不同元数据的物品
        }

    }

    /**
     * 获取食物的最大生长周期（用于种植功能）
     *
     * @param food 食物物品栈
     * @return 最大生长时间（tick）
     */
    @Override
    public int maxRoot(ItemStack food) {
        return 2400;
    }

    /**
     * 获取种植时使用的根部物品
     *
     * @param food 食物物品栈
     * @return 用作根部的物品栈
     */
    @Override
    public ItemStack getRootItem(ItemStack food) {
        return new ItemStack(ManaMetalMod.bigbowl); // 使用ManaMetalMod的大碗作为根部物品
    }
}
