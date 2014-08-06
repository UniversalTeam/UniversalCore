package universalteam.universalcore.world.retrogen;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

public interface IRetroGenerator
{
    public String getUniqueGenerationID();

    public boolean canGenerateIn(World world, Chunk chunk);

    public void generate(Random rand, World world, int chunkX, int chunkZ);
}
