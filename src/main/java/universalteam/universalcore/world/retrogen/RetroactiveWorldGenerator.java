package universalteam.universalcore.world.retrogen;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import universalteam.universalcore.utils.ChunkCoord;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RetroactiveWorldGenerator
{
	private static ArrayList<IRetroGenerator> generators = Lists.newArrayList();
	private static ConcurrentLinkedQueue<RetroGenEntry> genQueue = new ConcurrentLinkedQueue<RetroGenEntry>();

	public static final String retroGenSaveDataName = "uc_retrogen_data";

	public static void registerRetroGenerator(IRetroGenerator generator)
	{
		generators.add(generator);
	}

	private RetroGenSaveData getRetroGenSaveData(World world)
	{
		RetroGenSaveData data = (RetroGenSaveData) world.perWorldStorage.loadData(RetroGenSaveData.class, retroGenSaveDataName);

		if (data == null)
		{
			data = new RetroGenSaveData(retroGenSaveDataName);
			world.perWorldStorage.setData(retroGenSaveDataName, data);
		}

		return data;
	}

	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load event)
	{
		RetroGenSaveData data = getRetroGenSaveData(event.world);
		ChunkCoord coord = new ChunkCoord(event.getChunk());
		World world = event.world;
		Chunk chunk = event.getChunk();

		for (IRetroGenerator gen : generators)
			if (gen.canGenerateIn(world, chunk) && data.isGenerationNeeded(coord, gen.getUniqueGenerationID()))
			{
				genQueue.add(new RetroGenEntry(world, coord, gen));
				data.markChunkRetroGenerated(coord, gen.getUniqueGenerationID());
			}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if (event.phase != TickEvent.Phase.START)
			return;

		World world = event.world;

		while (!genQueue.isEmpty())
		{
			if (world.getWorldTime() % 10 != 0)
				continue;

			RetroGenEntry entry = genQueue.poll();
			entry.gen.generate(entry.world.rand, entry.world, entry.coord.chunkX, entry.coord.chunkZ);
		}
	}

	public class RetroGenEntry
	{
		World world;
		ChunkCoord coord;
		IRetroGenerator gen;

		public RetroGenEntry(World world, ChunkCoord coord, IRetroGenerator gen)
		{
			this.world = world;
			this.coord = coord;
			this.gen = gen;
		}
	}
}
