package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.Main;
import indi.byhfdd.bh3rd.client.GuiCrystalShop;
import indi.byhfdd.bh3rd.shop.ContainerCrystalShop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ClientGuiLoader extends GuiLoader {

    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new ClientGuiLoader());
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == ContainerCrystalShop.GUI_ID && world.getBlock(x, y, z) == BlockLoader.CrystalShop) {
            return new GuiCrystalShop(new ContainerCrystalShop(player, world, x, y, z));
        }
        return null;
    }
}
