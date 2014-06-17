package universalteam.universalcore.utils;

import net.minecraft.launchwrapper.Launch;

import java.io.IOException;

public class ObfuscationUtil
{
	static boolean checked = false;
	static boolean obfuscated;

	public static boolean isObfuscated()
	{
		if (!checked)
			checkEnvironment();
		return obfuscated;
	}

	static void checkEnvironment()
	{
		try
		{
			obfuscated = Launch.classLoader.getClassBytes("net.minecraft.world.World") == null;
		}
		catch (IOException e)
		{
		}
	}
}
