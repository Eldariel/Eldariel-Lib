package net.eldariel.lib.world;

import java.util.Random;

import net.eldariel.lib.EldarielLib;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.IWorldGenerator;

public abstract class WorldGeneratorEL implements IWorldGenerator {

	public final boolean allowRetrogen;
	public final String retrogenKey;
	public final int retrogenVersion;
	
	protected boolean printDebugInfo = false;
	
	public WorldGeneratorEL(boolean allowRetrogen, String retrogenKey) {
		
		this(allowRetrogen, retrogenKey, 1);
	}
	
	public WorldGeneratorEL(boolean allowRetrogen, String retrogenKey, int retrogenVersion) {
		
		this.allowRetrogen = allowRetrogen;
		this.retrogenKey = retrogenKey;
		this.retrogenVersion = retrogenVersion > 0 ? retrogenVersion : 1;
		
		if (allowRetrogen)
			MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		final int dim = world.provider.getDimension();
		final int posX = chunkX * 16;
		final int posZ = chunkZ * 16;
		
		if (!generateForDimension(dim, world, random, posX, posZ)) {
			switch (dim) {
			case 0:
				generateSurface(world, random, posX, posZ);
				break;
			case -1:
				generateNether(world, random, posX, posZ);
				break;
			case 1:
				generateEnd(world, random, posX, posZ);
				break;
			default:
				generateSurface(world, random, posX, posZ);
			}
		}
	}
	
	protected void generateSurface(World world, Random random, int posX, int posZ) {
		
	}
	
	protected void generateNether(World world, Random random, int posX, int posZ) {
		
	}
	
	protected void generateEnd(World world, Random random, int posX, int posZ) {
		
	}
	
	protected boolean generateForDimension(final int dim, World world, Random random, int posX, int posZ) {
		
		return false;
	}
	
	protected void debug(Object obj) {
		
		if (printDebugInfo)
			EldarielLib.logHelper.debug(obj);
	}
}
