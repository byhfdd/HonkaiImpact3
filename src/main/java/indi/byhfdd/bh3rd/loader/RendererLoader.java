package indi.byhfdd.bh3rd.loader;

import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class RendererLoader {

    public static void init() {
        for (Field field : WeaponLoader.class.getDeclaredFields()) {
            if (Item.class.isAssignableFrom(field.getType())) {
                try {
                    Item item = (Item) field.get(null);
                    String name = field.getName();
                    MinecraftForgeClient.registerItemRenderer(item, new ItemRenderer(name));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to register renderer: " + field.getName(), e);
                }
            }
        }
    }
}
