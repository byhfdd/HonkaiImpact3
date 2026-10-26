package indi.byhfdd.bh3rd.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import indi.byhfdd.bh3rd.item.food.FoodLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;

public class EventLeafDrop {

    @SubscribeEvent
    public void onHarvestLeaves(BlockEvent.HarvestDropsEvent event) {
        if (event.block == null || event.harvester == null
            || event.harvester instanceof FakePlayer
            || event.isSilkTouching) {
            return;
        }

        Block block = event.block;

        boolean isLeaves = block instanceof BlockLeavesBase || block == Blocks.leaves || block == Blocks.leaves2;

        if (isLeaves) {
            World world = event.world;
            if (world.rand.nextInt(20) == 0) { // 20%
                int amount = world.rand.nextInt(2) + 1;
                event.drops.add(new ItemStack(FoodLoader.FoodMaterials14, amount));
            }
        }
    }
}
