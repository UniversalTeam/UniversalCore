package universalcore.preloader;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import universalcore.api.compat.IPluginListener;
import universalcore.api.compat.UCPlugin;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UniversalCorePluginDetector
{
	public static Multimap<String, Class<?>> plugins = ArrayListMultimap.create();
	public static List<Class<?>> pluginListeners = Lists.newArrayList();

	public static void registerPluginListener(Class<?> clazz)
	{
		if (clazz.isAssignableFrom(IPluginListener.class))
			pluginListeners.add(clazz);
	}

	public static void findPlugins(File mod)
	{
		try
		{
			JarFile jar = new JarFile(mod);

			for (JarEntry entry : Collections.list(jar.entries()))
			{
				String fileName = entry.getName();

				if (!fileName.endsWith(".class"))
					continue;

				String className = fileName.replace('/', '.').substring(0, fileName.length() - 6);
				Class<?> clazz = Class.forName(className);

				if (!clazz.isAnnotationPresent(UCPlugin.class))
					continue;

				UCPlugin plugin = clazz.getAnnotation(UCPlugin.class);
				plugins.put(plugin.targetID(), clazz);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void handlePlugins()
	{
		for (Class<?> clazz : pluginListeners)
		{
			try
			{
				IPluginListener listener = (IPluginListener) clazz.newInstance();

				if (plugins.containsKey(listener.getModID()))
					listener.handle(plugins.get(listener.getModID()));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
