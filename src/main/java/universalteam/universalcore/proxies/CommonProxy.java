package universalteam.universalcore.proxies;

import codechicken.lib.packet.PacketCustom;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import universalteam.universalcore.command.UCCommand;
import universalteam.universalcore.compat.UCPluginListener;
import universalteam.universalcore.network.PacketConstants;
import universalteam.universalcore.network.UCSPH;
import universalteam.universalcore.utils.EventUtil;
import universalteam.universalcore.utils.ServerUtil;
import universalteam.universalcore.world.retrogen.RetroactiveWorldGenerator;

public class CommonProxy
{
	public void preInit()
	{
		initEventHandlers();
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

	protected void initCommands()
	{
		ServerUtil.registerCommand(new UCCommand());
	}

	protected void initEventHandlers()
	{
		EventUtil.register(new RetroactiveWorldGenerator());
	}
}
