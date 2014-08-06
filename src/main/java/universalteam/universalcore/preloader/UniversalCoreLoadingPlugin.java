package universalteam.universalcore.preloader;

import codechicken.core.launch.DepLoader;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import universalteam.universalcore.utils.UCLogger;

import java.io.File;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static universalteam.universalcore.UniversalCore.logger;

public class UniversalCoreLoadingPlugin implements IFMLLoadingPlugin, IFMLCallHook
{
	public File mcDir;
	public String mcVersion;

	public static UCLogger preLogger = logger.setSubName("preloader");

	public UniversalCoreLoadingPlugin()
	{
		if (mcDir != null)
			return;

		mcDir = (File) FMLInjectionData.data()[6];
		mcVersion = (String) FMLInjectionData.data()[4];

		DepLoader.load();
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return null;
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{

	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}

	@Override
	public Void call() throws Exception
	{
		scanMods();

		return null;
	}

	private void scanMods()
	{
		File modsDir = new File(mcDir, "mods");
		if (modsDir.exists())
			for (File mod : modsDir.listFiles())
			{
				scanMod(mod);
				UniversalCorePluginDetector.findPlugins(mod);
			}

		File versionModsDir = new File(modsDir, mcVersion);

		if (versionModsDir.exists())
			for (File mod : versionModsDir.listFiles())
			{
				scanMod(mod);
				UniversalCorePluginDetector.findPlugins(mod);
			}

		UniversalCorePluginDetector.handlePlugins();
	}

	private void scanMod(File mod)
	{
		if (!mod.getName().endsWith(".jar") && !mod.getName().endsWith(".zip"))
			return;

		try
		{
			JarFile jar = new JarFile(mod);
			try
			{
				Manifest manifest = jar.getManifest();
				if (manifest == null)
					return;
				Attributes attr = manifest.getMainAttributes();
				if (attr == null)
					return;

				String pluginListener = attr.getValue("UCPluginListener");
				if (pluginListener != null)
				{
					try
					{
						UniversalCorePluginDetector.registerPluginListener(Class.forName(pluginListener));
						preLogger.info("Found new UCPluginListener: %S", pluginListener);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			finally
			{
				jar.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
