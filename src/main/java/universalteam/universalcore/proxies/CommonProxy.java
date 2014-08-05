package universalteam.universalcore.proxies;

import codechicken.lib.packet.PacketCustom;
import universalteam.universalcore.command.UCCommand;
import universalteam.universalcore.compat.UCPluginListener;
import universalteam.universalcore.libs.ReferenceCore;
import universalteam.universalcore.network.PacketConstants;
import universalteam.universalcore.network.UCSPH;
import universalteam.universalcore.utils.ServerUtil;
import universalteam.universalcore.version.UCVersion;
import universalteam.universalcore.version.UCVersionChecker;

public class CommonProxy
{
	public void preInit()
	{
		UCVersionChecker.registerModVersion(new UCVersion(ReferenceCore.VERSION, "https://raw.githubusercontent.com/UniversalTeam/UCModVersions/master/UniversalCore/version.json"));
	}

	public void init()
	{
		PacketCustom.assignHandler(PacketConstants.CHANNEL, new UCSPH());

		UCPluginListener.handleCommon();

		UCVersionChecker.execute();
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
