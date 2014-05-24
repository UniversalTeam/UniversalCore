package universalcore.preloader;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import universalcore.api.compat.IPluginListener;
import universalcore.api.compat.UCPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UniversalCorePluginDetector
{
	public static Multimap<String, Class<?>> plugins = ArrayListMultimap.create();
	public static List<Class> pluginListeners = Lists.newArrayList();

	public static void registerPluginListener(Class<?> clazz)
	{
		if (clazz.isAssignableFrom(IPluginListener.class))
			pluginListeners.add(clazz);
	}

	public static void findPlugins(File mod)
	{
		
	}
}
