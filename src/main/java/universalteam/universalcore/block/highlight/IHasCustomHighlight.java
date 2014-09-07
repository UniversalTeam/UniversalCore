package universalteam.universalcore.block.highlight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public interface IHasCustomHighlight
{
	AxisAlignedBB[] getBoxes(World world, int x, int y, int z, EntityPlayer player);

	double getExpansion();
}
