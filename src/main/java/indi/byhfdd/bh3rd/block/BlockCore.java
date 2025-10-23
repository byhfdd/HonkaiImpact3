package indi.byhfdd.bh3rd.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;
import indi.byhfdd.bh3rd.loader.CreativeTabsLoader;
import project.studio.manametalmod.sound.SoundTypeCore;

public class BlockCore extends Block {

    public Item dropped;

    public BlockCore(String name, int hardness, int level, Item dropped) {
        super(Material.rock);// 分类
        this.setBlockName(name);// 未本地化名字
        this.setBlockTextureName("bh3rd:" + name);// 材质路径
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);// 物品栏
        this.setHardness(hardness);// 硬度，破坏时间
        this.setStepSound(SoundTypeCore.crystal);// 声音
        this.setHarvestLevel("pickaxe", level);// 挖掘等级
        this.dropped = dropped;// 掉落物
        GameRegistry.registerBlock(this, name);
    }

    public Item getItemDropped(int p1, Random random, int p2) {// 掉落物品
        return dropped;
    }
}
