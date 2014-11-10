package universalteam.universalcore.proxies;

import codechicken.lib.packet.PacketCustom;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import universalteam.universalcore.block.highlight.CustomBlockHighLighHandler;
import universalteam.universalcore.client.DevRenderEventHandler;
import universalteam.universalcore.client.render.block.BlockAdvancedRenderingHandler;
import universalteam.universalcore.compat.UCPluginListener;
import universalteam.universalcore.libs.ReferenceCore;
import universalteam.universalcore.network.PacketConstants;
import universalteam.universalcore.network.UCCPH;
import universalteam.universalcore.utils.EventUtil;
import universalteam.universalcore.version.UCVersion;
import universalteam.universalcore.version.UCVersionChecker;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();

		UCVersionChecker.registerModVersion(new UCVersion(ReferenceCore.VERSION, "https://raw.githubusercontent.com/UniversalTeam/UCModVersions/master/UniversalCore/version.json"));

		initClientEventHandlers();

		initISBRHs();
	}

	@Override
	public void init()
	{
		super.init();

		PacketCustom.assignHandler(PacketConstants.CHANNEL, new UCCPH());

		UCPluginListener.handleClient();

		UCVersionChecker.execute();
	}

	@Override
	public void postInit()
	{
		super.postInit();
	}

	@Override
	public void serverStarting()
	{
		super.serverStarting();
	}

	@Override
	public void serverStopping()
	{
		super.serverStopping();
	}

	public void initClientEventHandlers()
	{
		//EventUtil.register(new DevRenderEventHandler());
		EventUtil.register(new UCVersionChecker());
		EventUtil.register(new CustomBlockHighLighHandler());
	}

	public void initISBRHs()
	{
		RenderingRegistry.registerBlockHandler(BlockAdvancedRenderingHandler.RENDER_ID, new BlockAdvancedRenderingHandler());
	}
}
