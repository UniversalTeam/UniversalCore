package universalteam.universalcore.proxies;

import codechicken.lib.packet.PacketCustom;
import universalteam.universalcore.command.UCCommand;
import universalteam.universalcore.compat.UCPluginListener;
import universalteam.universalcore.network.UCSPH;
import universalteam.universalcore.utils.ServerUtil;

public class CommonProxy
{
	public void preInit()
	{
		initCommands();
	}

	public void init()
	{
		PacketCustom.assignHandler(UCSPH.CHANNEL, new UCSPH());

		UCPluginListener.handleCommon();
	}

	public void postInit()
	{

	}

	protected void initCommands()
	{
		ServerUtil.registerCommand(new UCCommand());
	}
}