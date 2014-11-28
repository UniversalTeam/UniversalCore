package universalteam.universalcore.configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraftforge.common.config.Configuration;
import universalteam.universalcore.libs.ReferenceCore;

import java.io.File;

public class Config
{
	public static final File mcDir = (File) FMLInjectionData.data()[6];
	public static final File configLocation = new File(mcDir, "config" + File.separator + ReferenceCore.MODID);

	public static Configuration config;

	public static void initConfig()
	{
		config = new Configuration(new File(configLocation, "Config.cfg"));
	}

	public static void updateConfig()
	{


		if (config.hasChanged())
			config.save();
	}

	@SubscribeEvent
	public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (ReferenceCore.MODID.equals(event.modID))
			updateConfig();
	}
}
