package universalcore.proxies;

import universalcore.compat.UCPluginListener;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();
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
}
