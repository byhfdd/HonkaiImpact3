package indi.byhfdd.bh3rd.cuisine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.junit.Test;

public class FoodCoreTest {

    @Test
    public void matchingPartlyEatenServingsRemainSeparateWhenAddedToInventory() {
        FoodCore food = new FoodCore(20, 2, "FoodCoreTest");
        InventoryPlayer inventory = new InventoryPlayer(null);
        ItemStack first = serving(food);
        ItemStack second = serving(food);

        assertTrue(inventory.addItemStackToInventory(first));
        assertTrue(inventory.addItemStackToInventory(second));

        assertEquals(1, inventory.mainInventory[0].stackSize);
        assertEquals(1, inventory.mainInventory[1].stackSize);
        assertNotSame(inventory.mainInventory[0].getTagCompound(), inventory.mainInventory[1].getTagCompound());
        assertEquals(3, inventory.mainInventory[0].getTagCompound().getInteger("foodCount"));
        assertEquals(3, inventory.mainInventory[1].getTagCompound().getInteger("foodCount"));

        inventory.mainInventory[0].getTagCompound().setInteger("AlreadyEaten", 2);
        assertEquals(1, inventory.mainInventory[1].getTagCompound().getInteger("AlreadyEaten"));
    }

    private static ItemStack serving(FoodCore food) {
        ItemStack stack = new ItemStack(food, 1, 0);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("foodCount", 3);
        tag.setInteger("AlreadyEaten", 1);
        stack.setTagCompound(tag);
        return stack;
    }
}
