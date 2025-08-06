package indi.byhfdd.bh3rd.ore;

import cpw.mods.fml.common.IWorldGenerator;
import indi.byhfdd.bh3rd.loader.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import project.studio.manametalmod.MMM;
import project.studio.manametalmod.config.M3Config;
import project.studio.manametalmod.world.WorldAncient;
import project.studio.manametalmod.world.ore.OreSpawnMain;

import java.util.Random;

public class AncientGenerate implements IWorldGenerator {
    public int EventSize = M3Config.OreSpawnSize;
    public int EventSpawn = M3Config.OreSpawnCount;

    public void generate(Random random, int x, int z, World w, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (MMM.getDimensionID(w) == M3Config.WorldAncientID) {
            generateSurface(random, x * 16, z * 16, w);
        }
    }

    public void generateSurface(Random random, int x, int z, World world) {
        addOreSpawn(BlockLoader.EtherPhlogistonore03, world, random, x, z, 16, 16, 10 + random.nextInt(6 + this.EventSize), 10 + this.EventSpawn, 1, 70, WorldAncient.blockAncientStone);
    }

    public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY, Block replaceblock) {
        int diffBtwnMinMaxY = maxY - minY;
        for (int x = 0; x < chancesToSpawn; x++) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(diffBtwnMinMaxY);
            int posZ = blockZPos + random.nextInt(maxZ);
            OreSpawnMain.generate(world, random, posX, posY, posZ, maxVeinSize, block, 0, replaceblock);
        }
    }

}
