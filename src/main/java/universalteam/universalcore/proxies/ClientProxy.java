package universalteam.universalcore.proxies;

import net.minecraftforge.common.MinecraftForge;
import universalteam.universalcore.client.CapeEventHandler;
import universalteam.universalcore.compat.UCPluginListener;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();

		initClientEventHandlers();
	}

	@Override
	public void init()
	{
		super.init();

		UCPluginListener.handleClient();
	}

	@Override
	public void postInit()
	{
		super.postInit();
	}

	public void initClientEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new CapeEventHandler());
	}
}
