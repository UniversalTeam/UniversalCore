package universalteam.universalcore.tile.implement;

import net.minecraft.entity.player.EntityPlayer;

public interface IHasGUI
{
	public boolean canPlayerOpenGUI(EntityPlayer player, int side, float hitX, float hitY, float hitZ);
}
