package universalteam.universalcore.configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import universalteam.universalcore.libs.ReferenceCore;

import java.io.File;

public class Config
{
	public static final File mcDir = (File) FMLInjectionData.data()[6];
	public static final File configLocation = new File(mcDir, "config" + File.separator + ReferenceCore.MODID);

	public static Configuration config;

	public static Property spreadMoss;

	public static void initConfig()
	{
		config = new Configuration(new File(configLocation, "Config.cfg"));
		updateConfig();
	}

	public static void updateConfig()
	{
		config.addCustomCategoryComment("Tweaks", "Some random tweaks added by UC.");

		spreadMoss = config.get("Tweaks", "Spreading Moss", true);
		spreadMoss.comment = "enables spreading of moss on cobblestone and bricks. this also allows you to crack stone bricks";

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
