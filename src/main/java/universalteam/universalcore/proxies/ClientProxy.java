package universalteam.universalcore.proxies;

import codechicken.lib.packet.PacketCustom;
import net.minecraftforge.common.MinecraftForge;
import universalteam.universalcore.client.CapeEventHandler;
import universalteam.universalcore.client.DevRenderEventHandler;
import universalteam.universalcore.compat.UCPluginListener;
import universalteam.universalcore.network.UCCPH;
import universalteam.universalcore.network.UCSPH;

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

		PacketCustom.assignHandler(UCCPH.CHANNEL, new UCCPH());

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
		MinecraftForge.EVENT_BUS.register(new DevRenderEventHandler());
	}
}
