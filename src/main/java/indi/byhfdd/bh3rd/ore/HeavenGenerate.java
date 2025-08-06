package indi.byhfdd.bh3rd.ore;

import cpw.mods.fml.common.IWorldGenerator;
import indi.byhfdd.bh3rd.loader.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import project.studio.manametalmod.MMM;
import project.studio.manametalmod.config.M3Config;
import project.studio.manametalmod.itemAndBlockCraft.ItemCraft10;
import project.studio.manametalmod.world.ore.OreSpawnMain;

import java.util.Random;

public class HeavenGenerate implements IWorldGenerator {
    public int EventSize = M3Config.OreSpawnSize;
    public int EventSpawn = M3Config.OreSpawnCount;

    public void generate(Random random, int x, int z, World w, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (MMM.getDimensionID(w) == M3Config.ManaWorldSkyID) {
            generateSurface(random, x * 16, z * 16, w);
        }
    }
    public void generateSurface(Random random, int x, int z, World world) {
        addOreSpawn(BlockLoader.EtherPhlogistonore01, world, random, x, z, 16, 16, 10 + random.nextInt(6 + this.EventSize), 10 + this.EventSpawn, 1, 128, Blocks.stone);
        addOreSpawn(BlockLoader.EtherPhlogistonore02, world, random, x, z, 16, 16, 10 + random.nextInt(6 + this.EventSize), 10 + this.EventSpawn, 1, 128, ItemCraft10.SkyStone);
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
//    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
//        if (world.provider.dimensionId == M3Config.ManaWorldSkyID) {
//            spawnOre(random, chunkX * 16, chunkZ * 16, world);
//            generateSurface(random, chunkX * 16, chunkZ * 16, world);
//        }
//    }
//
//    public void spawnOre(Random random, int x, int z, World world) {
//        if (!ManaMetalAPI.orlist_sky_main.isEmpty()) {
//            for (int s = 0; s < 10; s++) {
//                addOreSpawn(BlockLoader.EtherPhlogistonore01, world, random, x, z, 16, 16, 8 + random.nextInt(6), 6, 1, 128);
//            }
//        }
//    }
//
//    public void addOreSpawn(Block block, World world, Random random, int x, int z, int maxX, int maxZ, int Size, int Spawn, int minY, int maxY) {
//        int diffBtwnMinMaxY = maxY - minY;
//        for (int i = 0; i < Spawn; i++) {
//            int Xpos = x + random.nextInt(maxX);
//            int Ypos = minY + random.nextInt(diffBtwnMinMaxY);
//            int Zpos = z + random.nextInt(minY);
//            OreSpawnMain.generate(world, random, Xpos, Ypos, Zpos, Size, block, 0, Blocks.stone);
//        }
//    }
//
//    public void generateSurface(Random random, int x, int z, World world) {
//        if (!ManaMetalAPI.orlist_sky_special.isEmpty()) {
//            for (int s = 0; s < 10; ++s) {
//                this.addOreSpawn(BlockLoader.EtherPhlogistonore02, world, random, x, z, 16, 16, 5 + random.nextInt(6), 6, 1, 128, ItemCraft10.SkyStone);
//            }
//        }
//    }
//
//    public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY, Block replaceblock) {
//        int diffBtwnMinMaxY = maxY - minY;
//
//        for (int x = 0; x < chancesToSpawn; ++x) {
//            int posX = blockXPos + random.nextInt(maxX);
//            int posY = minY + random.nextInt(diffBtwnMinMaxY);
//            int posZ = blockZPos + random.nextInt(maxZ);
//            OreSpawnMain.generate(world, random, posX, posY, posZ, maxVeinSize, block, 0, replaceblock);
//        }
//    }
}
