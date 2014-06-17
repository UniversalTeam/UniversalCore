package universalteam.universalcore.libs.environment;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import universalteam.universalcore.UniversalCore;
import universalteam.universalcore.utils.UCLogger;

public class EnvironmentChecks
{
	public static boolean hasOptifine;
	protected static UCLogger logger = UniversalCore.logger.setSubName("EnvironmentChecks");

	public static void checkEnvironement()
	{
		if (FMLCommonHandler.instance().getSidedDelegate().getSide() == Side.CLIENT && ((FMLClientHandler.instance().hasOptifine()) || Loader.isModLoaded("optifine")))
		{
			hasOptifine = true;
			logger.warning("Optifine has been detected on your Minecraft installation, this can cause (rendering) issues");
		}
	}
}
