package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.FMLCommonHandler;
import indi.byhfdd.bh3rd.event.MeleeHitEvents;
import indi.byhfdd.bh3rd.network.CombatEffectsNetwork;
import net.minecraftforge.common.MinecraftForge;

public final class CombatEffectLoader {

    private static boolean initialized;

    private CombatEffectLoader() {}

    public static void init() {
        if (initialized) return;
        initialized = true;
        CombatEffectsNetwork.init();
        MeleeHitEvents events = new MeleeHitEvents();
        MinecraftForge.EVENT_BUS.register(events);
        FMLCommonHandler.instance().bus().register(events);
    }
}
