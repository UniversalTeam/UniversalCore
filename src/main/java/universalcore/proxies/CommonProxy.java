package universalcore.proxies;

import universalcore.compat.UCPluginListener;

public class CommonProxy
{
	public void preInit()
	{

	}

	public void init()
	{
		UCPluginListener.handleCommon();
	}

	public void postInit()
	{

	}
}