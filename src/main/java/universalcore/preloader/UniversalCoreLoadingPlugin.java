package universalcore.preloader;

import codechicken.core.launch.DepLoader;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class UniversalCoreLoadingPlugin implements IFMLLoadingPlugin, IFMLCallHook
{
	public File mcDir;
	public String mcVersion;

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
		return null;
	}

	private void scanModsForUCAccessTransformer()
	{
		File modsDir = new File(mcDir, "mods");
		if (modsDir.exists())
			for (File mod : modsDir.listFiles())
				scanMod(mod);

		File versionModsDir = new File(modsDir, mcVersion);

		if (versionModsDir.exists())
			for (File mod : versionModsDir.listFiles())
				scanMod(mod);
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

				String mapFile = attr.getValue("UCAccessTransformer");
				if (mapFile != null)
					UniversalCoreAccessTransformer.addTransformerMap(mapFile);
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
