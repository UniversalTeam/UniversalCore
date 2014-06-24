package universalteam.universalcore.tile.implement;

import net.minecraft.entity.player.EntityPlayer;

public interface IActivateAware
{
	public boolean onActivate(EntityPlayer player, int side, float hitX, float hitY, float hitZ);
}
