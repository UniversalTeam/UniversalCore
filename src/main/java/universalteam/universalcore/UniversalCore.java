package universalteam.universalcore;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import universalteam.universalcore.libs.ReferenceCore;
import universalteam.universalcore.proxies.CommonProxy;

@Mod(modid = ReferenceCore.MODID, name = ReferenceCore.MODNAME, version = ReferenceCore.VERSION, dependencies = "required-after:Forge@[10.12.1.1082,)")
public class UniversalCore
{
	@SidedProxy(clientSide = "universalteam.universalcore.proxies.ClientProxy", serverSide = "universalteam.universalcore.proxies.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}
}