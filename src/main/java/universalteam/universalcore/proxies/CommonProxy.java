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

	}

	public void init()
	{
		PacketCustom.assignHandler(UCSPH.CHANNEL, new UCSPH());

		UCPluginListener.handleCommon();
	}

	public void postInit()
	{

	}

	public void serverStarting()
	{
		initCommands();
	}

	protected void initCommands()
	{
		ServerUtil.registerCommand(new UCCommand());
	}
}