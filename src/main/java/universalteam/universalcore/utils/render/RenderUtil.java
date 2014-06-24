package universalteam.universalcore.utils.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.common.util.ForgeDirection;
import universalteam.universalcore.block.BlockAdvanced;

public class RenderUtil
{
	public static void rotateIconsForRender(BlockAdvanced block, ForgeDirection dir, RenderBlocks renderer)
	{
		switch (dir)
		{
			case DOWN:
				renderer.uvRotateSouth = 3;
				renderer.uvRotateNorth = 3;
				renderer.uvRotateEast = 3;
				renderer.uvRotateWest = 3;
				break;
			case NORTH:
				renderer.uvRotateSouth = 1;
				renderer.uvRotateNorth = 2;
				break;
			case SOUTH:
				renderer.uvRotateSouth = 2;
				renderer.uvRotateNorth = 1;
				renderer.uvRotateTop = 3;
				renderer.uvRotateBottom = 3;
				break;
			case EAST:
				renderer.uvRotateEast = 1;
				renderer.uvRotateWest = 2;
				renderer.uvRotateTop = 2;
				renderer.uvRotateBottom = 1;
				break;
			case WEST:
				renderer.uvRotateEast = 2;
				renderer.uvRotateWest = 1;
				renderer.uvRotateTop = 1;
				renderer.uvRotateBottom = 2;
				break;
		}
	}

	public static void resetFaces(RenderBlocks renderer)
	{
		renderer.uvRotateBottom = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateWest = 0;
	}
}
