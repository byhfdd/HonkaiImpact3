package indi.byhfdd.bh3rd.loader;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import indi.byhfdd.bh3rd.client.effect.MeleeHitEffects;
import indi.byhfdd.bh3rd.combat.MeleeAttackRules;
import indi.byhfdd.bh3rd.network.CombatEffectsNetwork;
import indi.byhfdd.bh3rd.network.HitEffectMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

/** Client main-thread delivery for visual-only network messages. */
@SideOnly(Side.CLIENT)
public final class ClientEffectLoader {

    private static boolean initialized;
    private World currentWorld;

    private ClientEffectLoader() {}

    public static void init() {
        if (initialized) return;
        initialized = true;
        FMLCommonHandler.instance().bus().register(new ClientEffectLoader());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        MeleeAttackRules.setLocalPlayer(Minecraft.getMinecraft().thePlayer);
        if (event.phase != TickEvent.Phase.END) return;
        World world = Minecraft.getMinecraft().theWorld;
        if (world != currentWorld) {
            currentWorld = world;
            CombatEffectsNetwork.clearClientHits();
        }
        if (world == null) {
            CombatEffectsNetwork.clearClientHits();
            return;
        }
        for (int i = 0; i < 64; i++) {
            HitEffectMessage hit = CombatEffectsNetwork.pollClientHit();
            if (hit == null) break;
            if (hit.dimensionId == world.provider.dimensionId) {
                MeleeHitEffects.spawn(world, hit.targetEntityId, hit.hitX, hit.hitY, hit.hitZ, hit.attackerYaw, hit.seed);
            }
        }
    }
}
