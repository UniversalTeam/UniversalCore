package universalteam.universalcore.proxies;

import codechicken.lib.packet.PacketCustom;
import universalteam.universalcore.command.CommandNick;
import universalteam.universalcore.command.CommandRealName;
import universalteam.universalcore.compat.UCPluginListener;
import universalteam.universalcore.configuration.Config;
import universalteam.universalcore.network.PacketConstants;
import universalteam.universalcore.network.UCSPH;
import universalteam.universalcore.nick.NicknameData;
import universalteam.universalcore.nick.NicknameHandler;
import universalteam.universalcore.utils.EventUtil;
import universalteam.universalcore.utils.ServerUtil;
import universalteam.universalcore.world.retrogen.RetroactiveWorldGenerator;

public class CommonProxy
{
	public void preInit()
	{
		Config.initConfig();

		initEventHandlers();

		NicknameData.initialize();

		Tweaks.initTweakBlocks();
	}

	public void init()
	{
		PacketCustom.assignHandler(PacketConstants.CHANNEL, new UCSPH());

		UCPluginListener.handleCommon();
	}

	public void postInit()
	{

	}

	public void serverStarting()
	{
		initCommands();
	}

	public void serverStopping()
	{
		NicknameData.deInitialize();
	}

	protected void initCommands()
	{
		ServerUtil.registerCommand(new CommandNick());
		ServerUtil.registerCommand(new CommandRealName());
	}

	protected void initEventHandlers()
	{
		EventUtil.register(new RetroactiveWorldGenerator());
		EventUtil.register(new NicknameHandler());
		EventUtil.register(new Config());
	}

	public static class Tweaks
	{
		public static void initTweakBlocks()
		{
			if (Config.spreadMoss.getBoolean(true))
			{

			}
		}
	}
}
