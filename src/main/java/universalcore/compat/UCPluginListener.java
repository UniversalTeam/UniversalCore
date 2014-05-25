package universalcore.compat;

import com.google.common.collect.Lists;
import universalcore.api.compat.IPluginListener;
import universalcore.api.compat.UCPlugin;
import universalcore.libs.ReferenceCore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UCPluginListener implements IPluginListener
{
	public List<Method> handleCommon = Lists.newArrayList();
	public List<Method> handleClient = Lists.newArrayList();

	public static UCPluginListener instance = new UCPluginListener();

	@Override
	public String getModID()
	{
		return ReferenceCore.MODID;
	}

	@Override
	public void handle(Collection<Class<?>> plugins)
	{
		for (Class<?> clazz : plugins)
		{
			List<Method> methods = Arrays.asList(clazz.getMethods());

			for (Method method : methods)
			{
				boolean commonFound = false;
				boolean clientFound = false;

				if (method.isAnnotationPresent(UCPlugin.Handle.class))
				{
					handleCommon.add(method);
					commonFound = true;
				}

				if (method.isAnnotationPresent(UCPlugin.HandleClient.class))
				{
					handleClient.add(method);
					clientFound = true;
				}

				if (clientFound && commonFound)
					break;
			}
		}
	}

	public static void handleCommon()
	{
		try
		{
			for (Method method : instance.handleCommon)
				method.invoke(instance, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void handleClient()
	{
		try
		{
			for (Method method : instance.handleClient)
				method.invoke(instance, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
