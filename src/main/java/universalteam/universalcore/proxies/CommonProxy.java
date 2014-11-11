package universalteam.universalcore.proxies;

import codechicken.lib.packet.PacketCustom;
import universalteam.universalcore.command.CommandNick;
import universalteam.universalcore.command.CommandRealName;
import universalteam.universalcore.command.UCCommand;
import universalteam.universalcore.compat.UCPluginListener;
import universalteam.universalcore.libs.environment.EnvironmentChecks;
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
		initEventHandlers();

		NicknameData.initialize();
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
		ServerUtil.registerCommand(new UCCommand());
		ServerUtil.registerCommand(new CommandNick());
		ServerUtil.registerCommand(new CommandRealName());
	}

	protected void initEventHandlers()
	{
		EventUtil.register(new RetroactiveWorldGenerator());
		EventUtil.register(new NicknameHandler());
	}
}
