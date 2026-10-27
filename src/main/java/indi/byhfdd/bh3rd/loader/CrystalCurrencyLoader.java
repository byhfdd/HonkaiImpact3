package indi.byhfdd.bh3rd.loader;

import indi.byhfdd.bh3rd.event.CrystalPickupEvents;
import net.minecraftforge.common.MinecraftForge;

public final class CrystalCurrencyLoader {

    private static boolean initialized;

    private CrystalCurrencyLoader() {}

    public static void init() {
        if (initialized) return;
        initialized = true;
        MinecraftForge.EVENT_BUS.register(new CrystalPickupEvents());
    }
}
